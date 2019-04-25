package com.yatra.tech.utils;

import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * Utility class for working with a VelocityEngine.
 * Provides convenience methods to merge a Velocity template with a model.
 *
 * @author rahul.vardhan
 */
public abstract class VelocityEngineUtils {

	private static final Log logger = LogFactory.getLog(VelocityEngineUtils.class);


	/**
	 * Merge the specified Velocity template with the given model and write
	 * the result to the given Writer.
	 * @param velocityEngine VelocityEngine to work with
	 * @param templateLocation the location of template, relative to Velocity's
	 * resource loader path
	 * @param encoding the encoding of the template file
	 * @param model the Map that contains model names as keys and model objects
	 * as values
	 * @param writer the Writer to write the result to
	 * @throws VelocityException if the template wasn't found or rendering failed
	 */
	public static void mergeTemplate(
			VelocityEngine velocityEngine, String templateLocation, String encoding, Map<String, Object> model, Writer writer)
			throws VelocityException {

		try {
			VelocityContext velocityContext = new VelocityContext(model);
			velocityEngine.mergeTemplate(templateLocation, encoding, velocityContext, writer);
		}
		catch (VelocityException ex) {
			throw ex;
		}
		catch (RuntimeException ex) {
			throw ex;
		}
		catch (Exception ex) {
			logger.error("Why does VelocityEngine throw a generic checked exception, after all?", ex);
			throw new VelocityException(ex.toString());
		}
	}

	/**
	 * Merge the specified Velocity template with the given model into a String.
	 * <p>When using this method to prepare a text for a mail to be sent with Spring's
	 * mail support, consider wrapping VelocityException in MailPreparationException.
	 * @param velocityEngine VelocityEngine to work with
	 * @param templateLocation the location of template, relative to Velocity's
	 * resource loader path
	 * @param encoding the encoding of the template file
	 * @param model the Map that contains model names as keys and model objects
	 * as values
	 * @return the result as String
	 * @throws VelocityException if the template wasn't found or rendering failed
	 * @see org.springframework.mail.MailPreparationException
	 */
	public static String mergeTemplateIntoString(
			VelocityEngine velocityEngine, String templateLocation, String encoding, Map<String, Object> model)
			throws VelocityException {

		StringWriter result = new StringWriter();
		mergeTemplate(velocityEngine, templateLocation, encoding, model, result);
		return result.toString();
	}

	public static void main(String[] args) {
		VelocityEngine velocityEngine = new VelocityEngine();
		velocityEngine.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath");
		velocityEngine.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		velocityEngine.init();
		Map<String, Object> templateMap = new HashMap<String, Object>();
		templateMap.put("name", "Rahul");
		System.out.println(mergeTemplateIntoString(velocityEngine, "test-velocity.vm", "UTF-8", templateMap));
	}
}
