package com.yatra.tech.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.MathTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.yatra.tech.dao.ConfirmationPropertiesDao;
import com.yatra.tech.dao.TicketingPdfDao;
import com.yatra.tech.entities.TicketingPdf;
import com.yatra.tech.service.TechDaoService;

import net.sf.json.JSONObject;

@Service
@Transactional
public class TechDaoServiceImpl implements TechDaoService {

	@Autowired
	private TicketingPdfDao ticketingPdfDao;
	@Autowired
	private VelocityEngine velocityEngine;
	@Autowired
	private ConfirmationPropertiesDao confirmationPropertiesDao;

	@Override
	public List<TicketingPdf> findTicketsBySuperPnr(String superPnr) {
		return this.ticketingPdfDao.findTicketsBySuperPnr(superPnr);
	}

	@Override
	public TicketingPdf findTicketBySuperPnr(String superPnr) {
		return this.ticketingPdfDao.findTicketBySuperPnr(superPnr);
	}

	@Override
	public String generateEticketHtml(String superPnr) {
		TicketingPdf ticketingPdf = this.findTicketBySuperPnr(superPnr);
		String json = this.confirmationPropertiesDao.getConfirmationJson(superPnr);

		Map<String, Object> templateValues = new HashMap<String, Object>();
		templateValues.put("confirmationJson", JSONObject.fromObject(json));
		templateValues.put("imagePath", "https://img.yatra.com/content/air-pay-book-service/css/images");
		templateValues.put("bannerImageUrl", "https://www.yatra.com/air-pay-book-service/resources/css");
		templateValues.put("rurlPath", "");
		templateValues.put("reschedulingUrl", "");
		templateValues.put("superPnr", superPnr);
		templateValues.put("source", "Flights");
		templateValues.put("cancellationUrl", "");
		templateValues.put("queryResponderEnabled", "");
		templateValues.put("cssPath", "http://css.yatracdn.com/content/air-pay-book-service/css");
		templateValues.put("date", new DateTool());
		templateValues.put("math", new MathTool());
		templateValues.put("stringUtils", new StringUtils());
		templateValues.put("bookingDate", new Date());

		String velocityTemplate = "/template/eTicket_perPax.vm";

		templateValues.put("paxId", ticketingPdf.getPaxId());
		templateValues.put("barcodeImagePath", "http://www.yatra.com");
		String eticketHtml = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, velocityTemplate, templateValues);
		return eticketHtml;
	}

	@Override
	public byte[] getTicketPdfByteArray(String superPnr) {
		String html = generateEticketHtml(superPnr);
		return generatePdf(html);
	}
	
	public byte[] generatePdf(String html) {
		try {
			ByteArrayOutputStream targetOS = new ByteArrayOutputStream();
			ITextRenderer renderer = new ITextRenderer();
			renderer.setDocumentFromString(html);
			renderer.layout();
			renderer.createPDF(targetOS);
			byte[] byteArray = targetOS.toByteArray();
			targetOS.close();
			return byteArray;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
