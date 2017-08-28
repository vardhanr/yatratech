package com.yatra.tech.dto;

import java.lang.reflect.ParameterizedType;
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

	private T requestBody;
	private String url;
	private HttpMethod methodType;
	private Class<U> responseType;
	private Map<String, String> headerMap;
	private Map<String, String> parameterMap;
	
	@SuppressWarnings("unchecked")
	public RestRequest() {
		this.responseType = (Class<U>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	}

	public T getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(T request) {
		this.requestBody = request;
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
