package com.yatra.tech.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.StandardToStringStyle;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

/**
 * 
 * @author Rahul Vardhan
 * Generic response class for generic rest client
 * @param <T>
 * @param <U>
 */
public class RestResponse<U> extends BaseDTO {

	private HttpStatus statusCode;
	private HttpHeaders httpHeaders;
	private String status;
	private String responseMessage;
	private U responseBody;

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public U getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(U response) {
		this.responseBody = response;
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

	public HttpStatus getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(HttpStatus statusCode) {
		this.statusCode = statusCode;
	}

	public HttpHeaders getHttpHeaders() {
		return httpHeaders;
	}

	public void setHttpHeaders(HttpHeaders httpHeaders) {
		this.httpHeaders = httpHeaders;
	}
}
