package com.yatra.tech.dto;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.annotate.JsonAnySetter;

public class FlightSchedule extends BaseDTO {

	private String scid;
	private String lang;
	private Map<String, String> airlineNames;
	private Map<String, String> airportNames;
	private Map<String, String> cityNames;
	private Map<String, String> taxLabel;

	private Map<String, Object> additionalProperties = new HashMap<>();

	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	public String getScid() {
		return scid;
	}

	public void setScid(String scid) {
		this.scid = scid;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public Map<String, String> getAirlineNames() {
		return airlineNames;
	}

	public void setAirlineNames(Map<String, String> airlineNames) {
		this.airlineNames = airlineNames;
	}

	public Map<String, String> getAirportNames() {
		return airportNames;
	}

	public void setAirportNames(Map<String, String> airportNames) {
		this.airportNames = airportNames;
	}

	public Map<String, String> getCityNames() {
		return cityNames;
	}

	public void setCityNames(Map<String, String> cityNames) {
		this.cityNames = cityNames;
	}

	public Map<String, String> getTaxLabel() {
		return taxLabel;
	}

	public void setTaxLabel(Map<String, String> taxLabel) {
		this.taxLabel = taxLabel;
	}
}
