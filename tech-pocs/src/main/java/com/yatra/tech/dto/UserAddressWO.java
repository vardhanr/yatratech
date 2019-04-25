package com.yatra.tech.dto;

public class UserAddressWO {

	private String cityName;
	private String countryName;
	private String postalCode;
	private String stateProv;
	private String addressLine1;
	private String addressLine2;
	private Boolean isAddressPrimary;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getStateProv() {
		return stateProv;
	}

	public void setStateProv(String stateProv) {
		this.stateProv = stateProv;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public Boolean getIsAddressPrimary() {
		return isAddressPrimary;
	}

	public void setIsAddressPrimary(Boolean isAddressPrimary) {
		this.isAddressPrimary = isAddressPrimary;
	}

}
