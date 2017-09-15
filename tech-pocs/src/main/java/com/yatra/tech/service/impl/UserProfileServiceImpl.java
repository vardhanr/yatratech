package com.yatra.tech.service.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.yatra.tech.client.RestClient;
import com.yatra.tech.dto.AddPaxDTO;
import com.yatra.tech.dto.AddPaxResponse;
import com.yatra.tech.dto.AddressResponseWO;
import com.yatra.tech.dto.BaseUserProfileDTO;
import com.yatra.tech.dto.GuestUserDTO;
import com.yatra.tech.dto.GuestUserProfileResponseWO;
import com.yatra.tech.dto.GuestUserProfileWO;
import com.yatra.tech.dto.GuestUserResponseDTO;
import com.yatra.tech.dto.PaxDetail;
import com.yatra.tech.dto.RestRequest;
import com.yatra.tech.dto.RestResponse;
import com.yatra.tech.dto.UpdateMobileWO;
import com.yatra.tech.dto.UpdateMobileWODTO;
import com.yatra.tech.dto.UserAccountResponseWO;
import com.yatra.tech.dto.UserAccountWO;
import com.yatra.tech.dto.UserAddressWO;
import com.yatra.tech.dto.UserProfileDTO;
import com.yatra.tech.dto.UserProfileWO;
import com.yatra.tech.service.UserProfileService;
import com.yatra.tech.utils.UserProfileUtils;

@Service
public class UserProfileServiceImpl implements UserProfileService {
	
	private static final Logger logger = Logger.getLogger(UserProfileServiceImpl.class);
	
	@Autowired
	private RestClient restClient;
	

	@Override
	public String addPaxList(String ssoToken, List<PaxDetail> paxDetailsWOList) {
		String message = "";
		try {
		Map<String, String> headers = UserProfileUtils.getHeaders(getTenant());
		AddPaxDTO requestBody = UserProfileUtils.generateAddAllPaxRequestBody(ssoToken, paxDetailsWOList);
		RestRequest<AddPaxDTO, AddPaxResponse> request = new RestRequest<>(AddPaxResponse.class, 
				"http://service1.yatra.com:6060/user-profile-service/services/user-pax-service/user/passengers", HttpMethod.POST);
		request = UserProfileUtils.enrichRestRequest(headers, requestBody, request, null);
		RestResponse<AddPaxResponse> restResponse = restClient.sendRestRequest(request);
		message = restResponse.getResponseBody().getMessages().get(0);
		}catch (Exception e) {
			logger.error("Exception while adding multiple pax" + e);
		}
		return message;
	}
	
	@Override
	public String getEmailAvailibilityMessage(String userEmailId) {
		return checkEmailAvailability(userEmailId).getMessages().get(0);
	}

	@Override
	public BaseUserProfileDTO checkEmailAvailability(String emailId) {
		BaseUserProfileDTO emailReponseDTO = prepareFailureDTO();
		try {
		Map<String, String> headers = UserProfileUtils.getHeaders(getTenant());
		RestRequest<Map<String, String>, BaseUserProfileDTO> request = new RestRequest<>(BaseUserProfileDTO.class, 
				"http://service1.yatra.com:6060/single-signon-service/services/sso-validation-service/user/userType/YATRA/account/availability" + "?emailId=" + emailId, HttpMethod.GET);
		request = UserProfileUtils.enrichRestRequest(headers, null, request, null);
		RestResponse<BaseUserProfileDTO> restResponse = restClient.sendRestRequest(request);
		emailReponseDTO = restResponse.getResponseBody();
		}catch (Exception e) {
			logger.error("Exception while checking emial availability" + e);
		}
		return emailReponseDTO;
	}
	
	public static BaseUserProfileDTO prepareFailureDTO() {
		BaseUserProfileDTO profileDTO = new BaseUserProfileDTO();
		return profileDTO;
	}

	@Override
	public Long createGusetUser(String emailId, String mobileNo, String isd) {
		Long userId = null;
		try {
		Map<String, String> headers = UserProfileUtils.getHeaders(getTenant());
		GuestUserDTO requestBody = UserProfileUtils.generateCreateGuestUserRequestBody(emailId, mobileNo, isd);
		RestRequest<GuestUserDTO, GuestUserResponseDTO> request = new RestRequest<>(GuestUserResponseDTO.class, 
				"http://service1.yatra.com:6060/single-signon-service/services/single-signon-service/customer/accounts", HttpMethod.PUT);
		request = UserProfileUtils.enrichRestRequest(headers, requestBody, request, null);
		RestResponse<GuestUserResponseDTO> restResponse = restClient.sendRestRequest(request);
		GuestUserResponseDTO guestUserResponseDTO = restResponse.getResponseBody();
		if(!guestUserResponseDTO.getMessages().get(0).equalsIgnoreCase("Failed to create guest user because wrong/empty input params.")) {
		userId = Long.parseLong(guestUserResponseDTO.getUserId());
		}
		}catch (Exception e) {
			logger.error("Exception while craeting guest user" + e);
		}
		return userId;
	}

	@Override
	public String createGuestUserProfile(String firstName, String lastName, Long userId) {
		String result = "";
		try {
		Map<String, String> headers = UserProfileUtils.getHeaders(getTenant());
		GuestUserProfileWO requestBody = UserProfileUtils.generateCreateGusetUserProfileRequestBody(firstName, lastName, userId);
		RestRequest<GuestUserProfileWO, GuestUserProfileResponseWO> request = new RestRequest<>(GuestUserProfileResponseWO.class, 
				"http://service1.yatra.com:6060/user-profile-service/services/user-profile-service/user/profiles", HttpMethod.PUT);
		request = UserProfileUtils.enrichRestRequest(headers, requestBody, request, null);
		RestResponse<GuestUserProfileResponseWO> restResponse = restClient.sendRestRequest(request);
		result = restResponse.getResponseBody().getMessages().get(0);
		}catch (Exception e) {
			logger.error("Exception while creating guest user profile" + e);
		}
		return result;
	}

	@Override
	public UserProfileWO getUserProfile(String tokenId) {
		UserProfileWO userProfileWO = null;
		try{
			Map<String, String> headers = UserProfileUtils.getHeaders(getTenant());
			UserProfileDTO requestBody = UserProfileUtils.generateGetUserProfileRequestBody(tokenId);
			String url = "http://service1.yatra.com:6060/user-profile-service/services/user-profile-service/user/profile";
			RestRequest<UserProfileDTO, UserProfileWO> request = new RestRequest<>(UserProfileWO.class, url, HttpMethod.POST);
			request = UserProfileUtils.enrichRestRequest(headers, requestBody, request, null);
			RestResponse<UserProfileWO> restResponse = this.restClient.sendRestRequest(request);
			userProfileWO = restResponse.getResponseBody();
			if(userProfileWO == null){
				//userProfileWO = new UserProfileWO(prepareFailureDTO(GENERIC_ERROR_MESSAGE));
			}
		}catch (Exception e){
			logger.error("Exception while getting user profile", e);
			//userProfileWO = new UserProfileWO(prepareFailureDTO(e.getMessage()));
		}
		return userProfileWO;
	}

	@Override
	public UserAccountWO getUserInfo(String tokenId) {
		UserAccountWO userAccountWO = null;
		try {
		Map<String, String> headers = UserProfileUtils.getHeaders(getTenant());
		UserProfileDTO requestBody = UserProfileUtils.generateGetUserProfileRequestBody(tokenId);
		RestRequest<UserProfileDTO, UserAccountResponseWO> request = new RestRequest<>(UserAccountResponseWO.class, 
				"http://service1.yatra.com:6060/single-signon-service/services/single-signon-service/customer/account", HttpMethod.POST);
		request = UserProfileUtils.enrichRestRequest(headers, requestBody, request, null);
		RestResponse<UserAccountResponseWO> restResponse = restClient.sendRestRequest(request);
		userAccountWO = restResponse.getResponseBody().getUserAccountWO();
		}catch (Exception e) {
			logger.error("Exception while getting user info" + e);
		}
		return userAccountWO;
	}

	public String getTenant() {
		return String.valueOf(17);
	}

	@Override
	public UserAddressWO getUserAddress(String tokenId) {
		UserAddressWO addressWO = null;
		try {
			Map<String, String> headers = UserProfileUtils.getHeaders(getTenant());
			UserProfileDTO requestBody = UserProfileUtils.generateGetUserProfileRequestBody(tokenId);
			RestRequest<UserProfileDTO, AddressResponseWO> request = new RestRequest<>(AddressResponseWO.class, 
					"http://service1.yatra.com:6060/user-profile-service/services/address-service/user/profile/addresses", HttpMethod.POST);
			request = UserProfileUtils.enrichRestRequest(headers, requestBody, request, null);
			RestResponse<AddressResponseWO> restResponse = restClient.sendRestRequest(request);
			Iterator<UserAddressWO> iterator = restResponse.getResponseBody().getAddresses().values().iterator();
			while(iterator.hasNext()) {
				if(restResponse.getResponseBody().getAddresses().values().iterator().next().getIsAddressPrimary()) {
					addressWO = restResponse.getResponseBody().getAddresses().values().iterator().next();
					break;
				}
			}
		} catch (Exception e) {
			logger.error("Exception while getting user address" + e);
		}
		return addressWO;
	}

	@Override
	public String updateMobileNumber(String mobileNo, String isd, String ssoToken) {
		String result = "";
		try {
		Map<String, String> headers = UserProfileUtils.getHeaders(getTenant());
		UpdateMobileWODTO requestBody = UserProfileUtils.generateupdateMobileRequestBody(mobileNo, isd, ssoToken);
		RestRequest<UpdateMobileWODTO, UpdateMobileWO> request = new RestRequest<>(UpdateMobileWO.class, 
				"http://service1.yatra.com:6060/single-signon-service/services/user-common-service/user/account/mobile", HttpMethod.PUT);
		request = UserProfileUtils.enrichRestRequest(headers, requestBody, request, null);
		RestResponse<UpdateMobileWO> restResponse = restClient.sendRestRequest(request);
		result = restResponse.getResponseBody().getMessages().get(0);
		} catch (Exception e) {
			logger.error("Exception while updating mobile number" + e);
		}
		return result;
	}
}
