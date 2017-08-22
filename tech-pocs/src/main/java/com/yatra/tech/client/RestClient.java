package com.yatra.tech.client;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yatra.tech.dto.RestRequest;
import com.yatra.tech.dto.RestResponse;
import com.yatra.tech.utils.Constants;

@Service("restClient")
public class RestClient {

	private Logger logger = Logger.getLogger(RestClient.class);

	public <T, U> RestResponse<U> sendRestRequest(RestRequest<T, U> request) {
		RestResponse<U> restResponse = new RestResponse<U>();
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = getHttpHeaders(request.getHeaderMap());
			T requestBody = request.getRequest();
			HttpEntity<T> entity = new HttpEntity<T>(requestBody, headers);
			HttpMethod method = request.getMethodType();
			Class<U> responseType = request.getResponseType();
			Map<String, String> parameterMap = request.getParameterMap();
			String url = request.getUrl();
			
			ResponseEntity<U> response = restTemplate.exchange(url, method, entity, responseType, parameterMap);
			restResponse.setStatus(Constants.REST_RESPONSE_SUCESS);
			restResponse.setResponse(response.getBody());
		} catch (Exception e) {
			logger.error("Exception occured while send Rest request.", e);
			restResponse.setStatus(Constants.REST_RESPONSE_FAILED);
			restResponse.setResponseMessage(e.getMessage());
		}
		return restResponse;
	}

	private HttpHeaders getHttpHeaders(Map<String, String> headerMap) {
		HttpHeaders headers = new HttpHeaders();
		
		if (headerMap != null && !headerMap.isEmpty()) {
			for (Map.Entry<String, String> entry : headerMap.entrySet()) {
				headers.set(entry.getKey(), entry.getValue());
			}
		}
		return headers;
	}
}
