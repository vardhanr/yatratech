package com.yatra.tech.dto;

import java.util.Map;

/**
 * 
 * @author rahul.vardhan
 * 
 * DTO class for ydist search response
 *
 */

public class SearchResponse extends BaseDTO {

    private String isFlights;
    private String isError;
    private String isWarnings;
    
    private FlightSchedule fltSchedule;
    private Map<String, Map<String, FareDetailNode>> fareDetails;

    public String getIsFlights() {
	return isFlights;
    }

    public void setIsFlights(String isFlights) {
	this.isFlights = isFlights;
    }

    public String getIsError() {
	return isError;
    }

    public void setIsError(String isError) {
	this.isError = isError;
    }

    public String getIsWarnings() {
	return isWarnings;
    }

    public void setIsWarnings(String isWarnings) {
	this.isWarnings = isWarnings;
    }

    public FlightSchedule getFltSchedule() {
	return fltSchedule;
    }

    public void setFltSchedule(FlightSchedule fltSchedule) {
	this.fltSchedule = fltSchedule;
    }

    public Map<String, Map<String, FareDetailNode>> getFareDetails() {
	return fareDetails;
    }

    public void setFareDetails(Map<String, Map<String, FareDetailNode>> fareDetails) {
	this.fareDetails = fareDetails;
    }

}
