package com.yatra.reports.dao;

import com.yatra.reports.entities.ABFUserAuthorization;

public interface ABFUserAuthorizationDAO extends GenericDAO<ABFUserAuthorization, Long> {

	public ABFUserAuthorization findByUserId(String userId);
}
