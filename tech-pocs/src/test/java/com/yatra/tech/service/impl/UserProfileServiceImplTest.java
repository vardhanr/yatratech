package com.yatra.tech.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.yatra.tech.config.BaseTestConfig;
import com.yatra.tech.dto.PaxDetail;
import com.yatra.tech.service.UserProfileService;

public class UserProfileServiceImplTest extends BaseTestConfig {

	@Autowired UserProfileService userProfileService;
	@Autowired VelocityEngine velocityEngine;
	
	@Test
	public void testAddAllPaxWithCorrectParameters() {
		List<PaxDetail> list = new ArrayList<>();
		PaxDetail paxDetail = new PaxDetail();
		paxDetail.setTitle("Mr");
		paxDetail.setFirstName("test");
		paxDetail.setLastName("tes");
		list.add(paxDetail);
		System.out.println(this.userProfileService.addPaxList("a26e7ed9-4420-4863-bac3-9495e6b84157", list));
	}
	
	@Test
	public void testAddAllPaxWithAllNullParameters() {
		List<PaxDetail> list = new ArrayList<>();
		PaxDetail paxDetail = new PaxDetail();
		paxDetail.setTitle("Mr");
		paxDetail.setFirstName("test");
		paxDetail.setLastName("tes");
		list.add(paxDetail);
		System.out.println(this.userProfileService.addPaxList(null, null));
	}
	
	@Test
	public void testAddAllPaxWithListAsNull() {
		List<PaxDetail> list = new ArrayList<>();
		PaxDetail paxDetail = new PaxDetail();
		paxDetail.setTitle("Mr");
		paxDetail.setFirstName("test");
		paxDetail.setLastName("tes");
		list.add(paxDetail);
		System.out.println(this.userProfileService.addPaxList("a26e7ed9-4420-4863-bac3-9495e6b84157", null));
	}
	
	@Test
	public void testAddAllPaxWithTokenIdAsNull() {
		List<PaxDetail> list = new ArrayList<>();
		PaxDetail paxDetail = new PaxDetail();
		paxDetail.setTitle("Mr");
		paxDetail.setFirstName("test");
		paxDetail.setLastName("tes");
		list.add(paxDetail);
		System.out.println(this.userProfileService.addPaxList(null, list));
	}
	
	@Test
	public void testEmailAvailabilityWithCorrectEmailId() {
		System.out.println(this.userProfileService.checkEmailAvailability("aayushi.jain@gmail.com"));
	}
	
	@Test
	public void testEmailAvailabilityWithEmailIdNotAvailable() {
		System.out.println(this.userProfileService.checkEmailAvailability("yushijain@gmail.com"));
	}
	
	@Test
	public void testEmailAvailabilityWithEmailIdAsNUll() {
		System.out.println(this.userProfileService.checkEmailAvailability(null));
	}
	
	@Test
	public void testEmailAvailabilityWithEmailIdAsEmpty() {
		System.out.println(this.userProfileService.checkEmailAvailability(""));
	}
	
	@Test
	public void testCreateGusetUserWithAllParameters() {
		System.out.println(this.userProfileService.createGusetUser("yushijain@gmail.com", "9999999999", "91"));
	}
	
	@Test
	public void testCreateGusetUserWithIsdCodenull() {
		System.out.println(this.userProfileService.createGusetUser("yushijain@gmail.com", "9999999999", null));
	}
	
	@Test
	public void testCreateGusetUserWithIsdCodeempty() {
		System.out.println(this.userProfileService.createGusetUser("yushijain@gmail.com", "9999999999", ""));
	}
	
	@Test
	public void testCreateGusetUserWithMobilenumberNull() {
		System.out.println(this.userProfileService.createGusetUser("yushijain@gmail.com", null, "91"));
	}
	
	@Test
	public void testCreateGusetUserWithMobilenumberAndIsdCodeNull() {
		System.out.println(this.userProfileService.createGusetUser("yushijain@gmail.com", null, null));
	}
	
	@Test
	public void testCreateGusetUserWithAllParametersNull() {
		System.out.println(this.userProfileService.createGusetUser(null, null, null));
	}
	
	@Test
	public void testCreateGusetUserWithEmailIdNull() {
		System.out.println(this.userProfileService.createGusetUser(null, "9999999999", "91" ));
	}
	
	@Test
	public void testCreateGusetUserProfileWithAllParametersCorrect() {
		System.out.println(this.userProfileService.createGuestUserProfile("abc", "cde", 45678L));
	}
	
	@Test
	public void testCreateGusetUserProfileWithFisrtNameNull() {
		System.out.println(this.userProfileService.createGuestUserProfile(null, "cde", 45678L));
	}
	
	@Test
	public void testCreateGusetUserProfileWithLastNameNull() {
		System.out.println(this.userProfileService.createGuestUserProfile("abc", null, 45678L));
	}
	
	@Test
	public void testCreateGusetUserProfileWithFirstAndLastNameAsNull() {
		System.out.println(this.userProfileService.createGuestUserProfile(null, null, 45678L));
	}
	
	@Test
	public void testCreateGusetUserProfileWithUserIDAsNull() {
		System.out.println(this.userProfileService.createGuestUserProfile("abc", "cde", null));
	}
	
	@Test
	public void testCreateGusetUserProfileWithAllParametersAsNull() {
		System.out.println(this.userProfileService.createGuestUserProfile(null, null, null));
	}
	
	@Test
	public void testCreateGusetUserProfileWithUserIdZero() {
		System.out.println(this.userProfileService.createGuestUserProfile("abc", "cde", 0L));
	}
	
	@Test
	public void testGetUserProfileWithCorrectParameters() {
		System.out.println(this.userProfileService.getUserProfile("a26e7ed9-4420-4863-bac3-9495e6b84157"));
	}
	
	@Test
	public void testGetUserProfileWithSSoTokenNull() {
		System.out.println(this.userProfileService.getUserProfile(null));
	}
	
	@Test
	public void testGetUserInfoWithSSoToken() {
		System.out.println(this.userProfileService.getUserInfo("a26e7ed9-4420-4863-bac3-9495e6b84157"));
	} 
	
	
	@Test
	public void testGetUserInfoWithSSoTokenNull() {
		System.out.println(this.userProfileService.getUserInfo(null));
	}
	
	
	@Test
	public void testGetUserAddressWithSSoTokenNull() {
		System.out.println(this.userProfileService.getUserAddress(null));
	}
	
	@Test
	public void testGetUserAddressWithSSoToken() {
		System.out.println(this.userProfileService.getUserAddress("a26e7ed9-4420-4863-bac3-9495e6b84157"));
	}
	
	@Test
	public void testUpdateMobileWithAllParameters() {
		System.out.println(this.userProfileService.updateMobileNumber("9999999999", "91", "a26e7ed9-4420-4863-bac3-9495e6b84157"));
	} 
	
	
	@Test
	public void testUpdateMobileWithAllEmptyParameters() {
		System.out.println(this.userProfileService.updateMobileNumber("", "", ""));
	}
}
