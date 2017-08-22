package com.yatra.reports.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;

/**
 * 
 * @author Rahul Vardhan
 * Generic response class for generic rest client
 * @param <T>
 * @param <U>
 */
public class RestResponse<U> extends BaseDTO {

	private String status;
	private String responseMessage;
	private U response;

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public U getResponse() {
		return response;
	}

	public void setResponse(U response) {
		this.response = response;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		StandardToStringStyle style = new StandardToStringStyle();
		style.setFieldSeparator(", ");
		style.setUseClassName(false);
		style.setUseIdentityHashCode(false);
		return new ReflectionToStringBuilder(this, style).toString();
	}
}
