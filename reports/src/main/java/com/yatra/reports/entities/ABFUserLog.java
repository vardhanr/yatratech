package com.yatra.reports.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "abf_user_log")
public class ABFUserLog extends BaseEntity {
	
	@Column(name = "user_id", nullable = false)
	private String userId;
	@Column(name = "sso_token", nullable = false)
	private String ssoToken;
	@Column(name = "search_id")
	private String searchId;
	@Column(name = "pricing_id")
	private String pricingId;
	@Column(name = "ip_address")
	private String idAddress;
	@Column(name = "super_pnr")
	private String superPnr;
	@Column(name = "ttid")
	private String ttid;
	@Column(name = "stage")
	private String stage;
	@Column(name = "tenant")
	private String tenant;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSsoToken() {
		return ssoToken;
	}

	public void setSsoToken(String ssoToken) {
		this.ssoToken = ssoToken;
	}

	public String getSearchId() {
		return searchId;
	}

	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}

	public String getPricingId() {
		return pricingId;
	}

	public void setPricingId(String pricingId) {
		this.pricingId = pricingId;
	}

	public String getIdAddress() {
		return idAddress;
	}

	public void setIdAddress(String idAddress) {
		this.idAddress = idAddress;
	}

	public String getSuperPnr() {
		return superPnr;
	}

	public void setSuperPnr(String superPnr) {
		this.superPnr = superPnr;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getTenant() {
		return tenant;
	}

	public void setTenant(String tenant) {
		this.tenant = tenant;
	}

	public String getTtid() {
		return ttid;
	}

	public void setTtid(String ttid) {
		this.ttid = ttid;
	}
}
