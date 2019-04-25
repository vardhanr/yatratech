package com.yatra.tech.dto;

import java.util.List;

public class GuestUserProfileResponseWO {

	private String code;
	private List<String> messages;
	private Boolean status;
	private String httpCode;
	private List<String> developerMessages;
	private Long userId;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public List<String> getMessages() {
		return messages;
	}

	public void setMessages(List<String> messages) {
		this.messages = messages;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(String httpCode) {
		this.httpCode = httpCode;
	}

	public List<String> getDeveloperMessages() {
		return developerMessages;
	}

	public void setDeveloperMessages(List<String> developerMessages) {
		this.developerMessages = developerMessages;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
