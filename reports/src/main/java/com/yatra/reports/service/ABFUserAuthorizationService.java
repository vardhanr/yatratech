package com.yatra.reports.service;

import java.util.List;

import com.yatra.reports.entities.ABFUserAuthorization;

public interface ABFUserAuthorizationService {

	public List<ABFUserAuthorization> findAll();
	
	public ABFUserAuthorization findByUserId(String userId);
	
	public void saveOrUpdate(ABFUserAuthorization authorization);

	public void saveOrUpdate(List<ABFUserAuthorization> list);
}
