package com.yatra.tech.dto;

public class UserAccountWO {

	private String emailId;
	private String userId;
	private Boolean emailVerified;
	private String createdOn;
	private String lastUpdatedOn;
	private Boolean mobileVerified;
	private String mobileNumber;
	private String mobileISDCode;
	private Boolean passwordCompromised;

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getMobileISDCode() {
		return mobileISDCode;
	}

	public void setMobileISDCode(String mobileISDCode) {
		this.mobileISDCode = mobileISDCode;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getLastUpdatedOn() {
		return lastUpdatedOn;
	}

	public void setLastUpdatedOn(String lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

	public Boolean getMobileVerified() {
		return mobileVerified;
	}

	public void setMobileVerified(Boolean mobileVerified) {
		this.mobileVerified = mobileVerified;
	}

	public Boolean getPasswordCompromised() {
		return passwordCompromised;
	}

	public void setPasswordCompromised(Boolean passwordCompromised) {
		this.passwordCompromised = passwordCompromised;
	}

}
