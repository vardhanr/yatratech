package com.yatra.tech.dto;

public class UpdateMobileWODTO {
	private MobileWO mobileWO;
	private String ssoToken;

	public MobileWO getMobileWO() {
		return mobileWO;
	}

	public void setMobileWO(MobileWO mobileWO) {
		this.mobileWO = mobileWO;
	}

	public String getSsoToken() {
		return ssoToken;
	}

	public void setSsoToken(String ssoToken) {
		this.ssoToken = ssoToken;
	}

}