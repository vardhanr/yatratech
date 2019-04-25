package com.yatra.tech.dto;

import java.util.List;

public class GuestUserResponseDTO {

	private String code;
	private List<String> messages;
	private String status;
	private String httpCode;
	private String userId;
	private List<String> developerMessages;

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

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
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

}
