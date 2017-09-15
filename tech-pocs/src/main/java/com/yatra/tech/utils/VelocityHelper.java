package com.yatra.tech.utils;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;

public class VelocityHelper {

	private static final Logger logger = Logger.getLogger(VelocityHelper.class);

	public static void mergeTemplate(VelocityEngine velocityEngine, String templateLocation, String encoding,
			Map<String, Object> model, Writer writer) throws VelocityException {

		try {
			VelocityContext velocityContext = new VelocityContext(model);
			velocityEngine.mergeTemplate(templateLocation, encoding, velocityContext, writer);
		} catch (VelocityException ex) {
			throw ex;
		} catch (RuntimeException ex) {
			throw ex;
		} catch (Exception ex) {
			logger.error("Why does VelocityEngine throw a generic checked exception, after all?", ex);
			throw new VelocityException(ex.toString());
		}
	}

	public static String mergeTemplateIntoString(VelocityEngine velocityEngine, String templateLocation,
			String encoding, Map<String, Object> model) throws VelocityException {

		StringWriter result = new StringWriter();
		mergeTemplate(velocityEngine, templateLocation, encoding, model, result);
		return result.toString();
	}

}
