package com.yatra.tech.dto;

import java.util.List;

public class AddPaxDTO {

	private String ssoToken;
	private List<PaxDetail> paxDetails;

	public String getSsoToken() {
		return ssoToken;
	}

	public void setSsoToken(String ssoToken) {
		this.ssoToken = ssoToken;
	}

	public List<PaxDetail> getPaxDetails() {
		return paxDetails;
	}

	public void setPaxDetails(List<PaxDetail> paxDetails) {
		this.paxDetails = paxDetails;
	}

}
