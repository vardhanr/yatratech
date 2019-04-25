package com.yatra.tech.dto;

import java.util.List;
import java.util.Map;

public class AddressResponseWO {

	private String code;
	private List<String> messages;
	private Boolean status;
	private String httpCode;
	private List<String> developerMessages;
	private Long userId;
	private Map<String, UserAddressWO> addresses;

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

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Map<String, UserAddressWO> getAddresses() {
		return addresses;
	}

	public void setAddresses(Map<String, UserAddressWO> addresses) {
		this.addresses = addresses;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

}
