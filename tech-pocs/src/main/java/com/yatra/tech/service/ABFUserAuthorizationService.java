package com.yatra.tech.service;

import java.util.List;

import com.yatra.tech.entities.ABFUserAuthorization;
import com.yatra.tech.entities.YatraAddon;

public interface ABFUserAuthorizationService {

	public List<ABFUserAuthorization> findAll();
	
	public ABFUserAuthorization findByUserId(String userId);
	
	public void saveOrUpdate(ABFUserAuthorization authorization);

	public void saveOrUpdate(List<ABFUserAuthorization> list);
	
	public YatraAddon getAddon(String ttId, Long addonId);
}
