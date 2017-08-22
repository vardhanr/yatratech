package com.yatra.tech.client;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.yatra.tech.dto.RestRequest;
import com.yatra.tech.dto.RestResponse;
import com.yatra.tech.utils.Constants;

@Service
public class RestClient<T, U> {

	private Logger logger = Logger.getLogger(RestClient.class);

	public RestResponse<U> sendRestRequest(RestRequest<T, U> request) {
		RestResponse<U> restResponse = new RestResponse<U>();
		try {
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			for (Map.Entry<String, String> entry : request.getHeaderMap().entrySet()) {
				headers.set(entry.getKey(), entry.getValue());
			}
			HttpEntity<T> entity = new HttpEntity<T>(request.getRequest(), headers);
			ResponseEntity<U> response = restTemplate.exchange(request.getUrl(), request.getMethodType(), entity, request.getResponseType(), request.getParameterMap());
			restResponse.setStatus(Constants.REST_RESPONSE_SUCESS);
			restResponse.setResponse(response.getBody());
		} catch (Exception e) {
			logger.error("Exception occured while send Rest request.", e);
			restResponse.setStatus(Constants.REST_RESPONSE_FAILED);
			restResponse.setResponseMessage(e.getMessage());
		}
		return restResponse;
	}

}
