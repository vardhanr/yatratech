package com.yatra.tech.client;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.yatra.tech.dto.RestRequest;
import com.yatra.tech.dto.RestResponse;
import com.yatra.tech.utils.Constants;

@Service("restClient")
public class RestClient {

	private static Logger logger = Logger.getLogger(RestClient.class);
	
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

	public <T, U> RestResponse<U> sendRestRequest(RestRequest<T, U> request) {
		RestResponse<U> restResponse = new RestResponse<U>();
		try {
			HttpEntity<?> entity = preparePayload(request);
			Map<String, String> uriParameters = getUriParameters(request);

			ResponseEntity<U> response = this.restTemplate.exchange(request.getUrl(), request.getMethodType(), entity, request.getResponseType(), uriParameters);
			restResponse = prepareResponse(response);
		} catch (Exception e) {
			logger.error("Exception occured while send Rest request.", e);
			restResponse.setStatus(Constants.REST_RESPONSE_FAILED);
			restResponse.setResponseMessage(e.getMessage());
		}
		return restResponse;
	}

	private <T, U> Map<String, String> getUriParameters(RestRequest<T, U> request) {
		Map<String, String> uriParameters = new HashMap<>();
		Map<String, String> requestParamMap = request.getParameterMap();
		if (HttpMethod.GET.equals(request.getMethodType()) && requestParamMap != null) {
			uriParameters = request.getParameterMap();
		}
		return uriParameters;
	}

	private <T, U> HttpEntity<?> preparePayload(RestRequest<T, U> request) {

		Map<String, String> headerMap = request.getHeaderMap();
		HttpHeaders headers = new HttpHeaders();
		if (headerMap != null && !headerMap.isEmpty()) {
			headers.setAll(headerMap);
		}
		
		HttpEntity<?> entity = new HttpEntity<T>(headers);

		// Http allows either request body or form data to be transferred over the network
		if (!HttpMethod.GET.equals(request.getMethodType())) {
			T body = request.getRequestBody();
			Map<String, String> parameterMap = request.getParameterMap();
			if (body != null) {
				entity = new HttpEntity<T>(request.getRequestBody(), headers);
			} else if (parameterMap != null && !parameterMap.isEmpty()) {
				MultiValueMap<String, String> parameters = new LinkedMultiValueMap<String, String>();
				parameters.setAll(parameterMap);
				entity = new HttpEntity<MultiValueMap<String, String>>(parameters, headers);
			}
		}
		return entity;
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
