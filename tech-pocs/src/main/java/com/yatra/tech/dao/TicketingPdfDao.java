package com.yatra.tech.dao;

import java.util.List;

import com.yatra.tech.entities.TicketingPdf;

public interface TicketingPdfDao extends GenericDAO<TicketingPdf, Long> {

	public List<TicketingPdf> findTicketsBySuperPnr(String superPnr);
	
	public TicketingPdf findTicketBySuperPnr(String superPnr);
}
