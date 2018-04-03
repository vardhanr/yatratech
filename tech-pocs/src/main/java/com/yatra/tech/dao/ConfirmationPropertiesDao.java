package com.yatra.tech.dao;

import java.util.List;
import java.util.Map;

import com.yatra.tech.entities.ConfirmationProperties;

public interface ConfirmationPropertiesDao extends GenericDAO<ConfirmationProperties, Long>{

    List<ConfirmationProperties> getConfirmationProperties(String superPnr, String bookingId);

    void insertConfirmationProperty(String superPnr, String bookingId, String propertyName, String propetyValue);

	Map<String, String> getConfirmationProperties(String superPnr);
	
	String getConfirmationJson(String superPnr);
	
}
