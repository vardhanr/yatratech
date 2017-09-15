package com.yatra.tech.service;

import java.util.List;

import com.yatra.tech.dto.BaseUserProfileDTO;
import com.yatra.tech.dto.PaxDetail;
import com.yatra.tech.dto.UserAccountWO;
import com.yatra.tech.dto.UserAddressWO;
import com.yatra.tech.dto.UserProfileWO;

public interface UserProfileService {

	String addPaxList(String ssoToken, List<PaxDetail> paxDetailsWOList);

	String getEmailAvailibilityMessage(String userEmailId);
	
	BaseUserProfileDTO checkEmailAvailability(String emailId);

	Long createGusetUser(String emailId, String mobileNo, String isd);

	String createGuestUserProfile(String userFirstName, String userLastName, Long userId);

	UserProfileWO getUserProfile(String tokenId);

	UserAccountWO getUserInfo(String tokenId);

	UserAddressWO getUserAddress(String tokenId);

	String updateMobileNumber(String mobileNo, String isd, String ssoToken);

}
