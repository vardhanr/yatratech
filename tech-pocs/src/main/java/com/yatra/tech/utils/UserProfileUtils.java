package com.yatra.tech.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.yatra.tech.dto.AddPaxDTO;
import com.yatra.tech.dto.GuestUserDTO;
import com.yatra.tech.dto.GuestUserProfileWO;
import com.yatra.tech.dto.MobileWO;
import com.yatra.tech.dto.PaxDetail;
import com.yatra.tech.dto.PersonWO;
import com.yatra.tech.dto.ProfileWO;
import com.yatra.tech.dto.RestRequest;
import com.yatra.tech.dto.UpdateMobileWODTO;
import com.yatra.tech.dto.UserProfileDTO;

public class UserProfileUtils {

	public static <T, U> RestRequest<T, U> enrichRestRequest(Map<String, String> headers, T requestBody,
			RestRequest<T, U> request, Map<String, String> parameterMap) {
		request.setHeaderMap(headers);
		request.setRequestBody(requestBody);
		request.setParameterMap(parameterMap);
		return request;
	}

	public static Map<String, String> getHeaders(String tenantId) {
		Map<String, String> headers = new HashMap<>();
		new HashMap<String, String>();
		headers.put("com.yatra.tenant.header.tenantId", tenantId);
		headers.put("Content-Type", "application/json");
		return headers;
	}

	public static AddPaxDTO generateAddAllPaxRequestBody(String ssoToken, List<PaxDetail> paxDetailsWOList) {
		AddPaxDTO requestBody = new AddPaxDTO();
		requestBody.setSsoToken(ssoToken);
		requestBody.setPaxDetails(paxDetailsWOList);
		return requestBody;
	}

	public static Map<String, String> generateCheckEmailAvailabilityRequestBody(String emailId) {
		Map<String, String> parameterMap = new HashMap<>();
		parameterMap.put("emailId", emailId);
		return parameterMap;
	}

	public static GuestUserDTO generateCreateGuestUserRequestBody(String emailId, String mobileNo, String isd) {
		GuestUserDTO requestBody = new GuestUserDTO();
		MobileWO mobileWO = new MobileWO();
		requestBody.setEmailId(emailId);
		mobileWO.setIsdCode(isd);
		mobileWO.setMobileNumber(mobileNo);
		requestBody.setMobileWO(mobileWO);
		return requestBody;
	}

	public static GuestUserProfileWO generateCreateGusetUserProfileRequestBody(String firstName, String lastName,
			Long userId) {
		GuestUserProfileWO requestBody = new GuestUserProfileWO();
		PersonWO personWO = new PersonWO();
		personWO.setFirstName(firstName);
		personWO.setLastName(lastName);
		ProfileWO userProfileWO = new ProfileWO();
		userProfileWO.setUserId(userId);
		userProfileWO.setPersonName(personWO);
		requestBody.setUserProfileWO(userProfileWO);
		return requestBody;
	}

	public static UserProfileDTO generateGetUserProfileRequestBody(String tokenId) {
		UserProfileDTO requestBody = new UserProfileDTO();
		requestBody.setSsoToken(tokenId);
		return requestBody;
	}

	public static UpdateMobileWODTO generateupdateMobileRequestBody(String mobileNo, String isd, String ssoToken) {
		MobileWO requestBody = new MobileWO();
		requestBody.setIsdCode(isd);
		requestBody.setMobileNumber(mobileNo);
		UpdateMobileWODTO updateMobileWODTO = new UpdateMobileWODTO();
		updateMobileWODTO.setSsoToken(ssoToken);
		updateMobileWODTO.setMobileWO(requestBody);
		return updateMobileWODTO;
	}
}
