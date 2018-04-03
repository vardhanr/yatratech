package com.yatra.tech.dao;

import java.util.List;

import com.yatra.tech.dto.ExcelLineItem;
import com.yatra.tech.entities.ABFUserAuthorization;

public interface SpringTemplateDAO {

	public List<ABFUserAuthorization> findAllUserAuthorization();
	
	public void testTicketingData();
	
	public List<ExcelLineItem> fetchInsuranceData(String start, String end);
	
	public void getTenantSearchResult() throws Exception;
}
