package com.yatra.tech.dto;

import java.util.Map;

import org.springframework.http.HttpMethod;

/**
 * 
 * @author Rahul Vardhan 
 * Generic request class for generic rest client
 * @param <T>
 * @param <U>
 */
public class RestRequest<T, U> extends BaseDTO {

	private T request;
	private String url;
	private HttpMethod methodType;
	private Class<U> responseType;
	private Map<String, String> headerMap;
	private Map<String, String> parameterMap;

	public T getRequest() {
		return request;
	}

	public void setRequest(T request) {
		this.request = request;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, String> getHeaderMap() {
		return headerMap;
	}

	public void setHeaderMap(Map<String, String> headerMap) {
		this.headerMap = headerMap;
	}

	public HttpMethod getMethodType() {
		return methodType;
	}

	public void setMethodType(HttpMethod methodType) {
		this.methodType = methodType;
	}

	public Class<U> getResponseType() {
		return responseType;
	}

	public void setResponseType(Class<U> responseType) {
		this.responseType = responseType;
	}

	public Map<String, String> getParameterMap() {
		return parameterMap;
	}

	public void setParameterMap(Map<String, String> parameterMap) {
		this.parameterMap = parameterMap;
	}
}
