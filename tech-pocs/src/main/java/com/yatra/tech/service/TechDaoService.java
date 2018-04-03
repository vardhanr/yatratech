package com.yatra.tech.service;

import java.util.List;

import com.yatra.tech.entities.TicketingPdf;

public interface TechDaoService {

	public List<TicketingPdf> findTicketsBySuperPnr(String superPnr);
	
	public TicketingPdf findTicketBySuperPnr(String superPnr);
	
	public String generateEticketHtml(String superPnr);
	
	public byte[] getTicketPdfByteArray(String superPnr);
}
