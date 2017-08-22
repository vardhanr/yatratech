package com.yatra.tech.dao;

import com.yatra.tech.entities.ABFUserAuthorization;

public interface ABFUserAuthorizationDAO extends GenericDAO<ABFUserAuthorization, Long> {

	public ABFUserAuthorization findByUserId(String userId);
}	
