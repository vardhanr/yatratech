package com.yatra.tech.client;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yatra.tech.dto.RestRequest;
import com.yatra.tech.dto.RestResponse;
import com.yatra.tech.utils.Constants;

@Service("restClient")
public class RestClient {
	
	private RestTemplate restTemplate;
	
	public RestClient() {
		this.restTemplate = new RestTemplate();
	}
	
	public RestClient(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public RestClient(int readTimeout, int connectTimeout) {
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
		requestFactory.setConnectTimeout(connectTimeout);
		requestFactory.setReadTimeout(readTimeout);
		this.restTemplate = new RestTemplate(requestFactory);
	}

	private Logger logger = Logger.getLogger(RestClient.class);

	public <T, U> RestResponse<U> sendRestRequest(RestRequest<T, U> request) {
		RestResponse<U> restResponse = new RestResponse<U>();
		try {
			HttpHeaders headers = prepareHeaders(request.getHeaderMap());
			
			HttpEntity<T> entity = new HttpEntity<T>(request.getRequestBody(), headers);
			HttpMethod method = request.getMethodType();
			Class<U> responseType = request.getResponseType();
			Map<String, String> parameterMap = request.getParameterMap();
			
			ResponseEntity<U> response = this.restTemplate.exchange(request.getUrl(), method, entity, responseType, parameterMap);
			restResponse = prepareResponse(response);
		} catch (Exception e) {
			logger.error("Exception occured while send Rest request.", e);
			restResponse.setStatus(Constants.REST_RESPONSE_FAILED);
			restResponse.setResponseMessage(e.getMessage());
		}
		return restResponse;
	}
	
	private HttpHeaders prepareHeaders(Map<String, String> headerMap) {
		HttpHeaders headers = new HttpHeaders();
		if (headerMap  != null && !headerMap.isEmpty()) {
			headers.setAll(headerMap);
		}
		return headers;
	}
	
	private <U> RestResponse<U> prepareResponse(ResponseEntity<U> responseEntity) {
		RestResponse<U> restResponse = new RestResponse<U>();
		HttpStatus status = responseEntity.getStatusCode();
		if (status.isError()) {
			restResponse.setStatus(Constants.REST_RESPONSE_FAILED);
		} else {
			restResponse.setStatus(Constants.REST_RESPONSE_SUCESS);
		}
		restResponse.setResponseMessage(status.getReasonPhrase());
		restResponse.setStatusCode(status);
		restResponse.setHttpHeaders(responseEntity.getHeaders());
		restResponse.setResponseBody(responseEntity.getBody());
		return restResponse;
	}
}
