package com.yatra.tech.service.impl;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.Iterator;

import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XPathReader {

	private Document xmlDocument;
	private XPath xPath;

	public XPathReader(InputStream in) throws Exception {
		initObjects(in);
	}

	public XPathReader(String xmlFile) throws Exception {
		this(new FileInputStream(xmlFile));
	}

	public XPathReader(InputStream in, Boolean isNameSpaceAware) throws Exception {
		initObjects(in, isNameSpaceAware);
	}

	public XPathReader(String xmlFile, Boolean isNameSpaceAware) throws Exception {
		this(new FileInputStream(xmlFile), isNameSpaceAware);
	}

	private void initObjects(InputStream in) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		xmlDocument = factory.newDocumentBuilder().parse(in);
		xPath = XPathFactory.newInstance().newXPath();
	}

	private void initObjects(InputStream in, Boolean isNameSpaceAware) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setNamespaceAware(isNameSpaceAware);
		xmlDocument = factory.newDocumentBuilder().parse(in);
		xPath = XPathFactory.newInstance().newXPath();
		if (isNameSpaceAware)
			setNameSpaceContext();
	}

	public void setNameSpaceContext() {
		xPath.setNamespaceContext(new NamespaceContext() {
			@Override
			public String getNamespaceURI(String prefix) {
				if (prefix.equals("soap")) {
					return "http://www.w3.org/2003/05/soap-envelope";
				}
				if (prefix.equals("soapenv")) {
					return "http://schemas.xmlsoap.org/soap/envelope/";
				}
				if (prefix.equals("ota") || prefix.equals("ns1")) {
					return "http://www.opentravel.org/OTA/2003/05";
				}
				if (prefix.equals("ns2")) {
					return "http://www.openjawtech.com/2005";
				}
				return XMLConstants.NULL_NS_URI;
			}

			@Override
			public String getPrefix(String namespaceURI) {
				throw new UnsupportedOperationException("Not supported yet.");
			}

			@Override
			public Iterator<?> getPrefixes(String namespaceURI) {
				throw new UnsupportedOperationException("Not supported yet.");
			}
		});

	}

	public Object read(String expression, QName returnType) throws Exception {
		XPathExpression xPathExpression = xPath.compile(expression);
		return xPathExpression.evaluate(xmlDocument, returnType);
	}

	public Object readChildForNode(String expression, Node node, QName returnType) throws Exception {
		return xPath.evaluate(expression, node, returnType);
	}

	public NodeList readNodeList(String expression) throws Exception {
		Object object = read(expression, XPathConstants.NODESET);
		return object == null ? null : (NodeList) object;
	}

	public Node readNode(String expression) throws Exception {
		Object object = read(expression, XPathConstants.NODE);
		return object == null ? null : (Node) object;
	}

	public String readStringFromNodeList(String expression) throws Exception {
		NodeList nodeList = readNodeList(expression);
		if (nodeList != null && nodeList.getLength() > 0) {
			try {
				StringBuilder string = new StringBuilder();
				for (int i = 0; i < nodeList.getLength(); i++) {
					StringWriter sw = new StringWriter();
					Transformer t = TransformerFactory.newInstance().newTransformer();
					t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
					t.transform(new DOMSource(nodeList.item(i)), new StreamResult(sw));
					string.append(sw.toString());
				}
				return string.toString();
			} catch (TransformerException te) {
			}
		}
		return "";
	}

	public String readString(String expression) throws Exception {
		Object object = read(expression, XPathConstants.STRING);
		return object == null ? "" : (String) object;
	}

	public double readDouble(String expression) throws Exception {
		Double object = ((Number) read(expression, XPathConstants.NUMBER)).doubleValue();
		return object.equals(Double.NaN) ? 0.0d : object;
	}

	public int readInt(String expression) throws Exception {
		Integer object = ((Number) read(expression, XPathConstants.NUMBER)).intValue();
		return object;
	}

	public Boolean readBoolean(String expression) throws Exception {
		Object object = read(expression, XPathConstants.BOOLEAN);
		return object == null ? null : (Boolean) object;
	}

	public String getXml() throws TransformerException {
		DOMSource domSource = new DOMSource(this.xmlDocument);
		StringWriter writer = new StringWriter();
		StreamResult result = new StreamResult(writer);
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		transformer.transform(domSource, result);
		return writer.toString();
	}

	public String getNodeListAsString(String expression) throws Exception {
		NodeList nodeList = readNodeList(expression);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < nodeList.getLength(); i++) {
			sb.append(convertNodeToString(nodeList.item(i)));
		}
		return sb.toString();
	}
	
	 private String convertNodeToString(Node node) throws TransformerException {
	        Transformer t = TransformerFactory.newInstance().newTransformer();
	        t.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
	        StringWriter sw = new StringWriter();
	        t.transform(new DOMSource(node), new StreamResult(sw));
	        return sw.toString();
	    }
}