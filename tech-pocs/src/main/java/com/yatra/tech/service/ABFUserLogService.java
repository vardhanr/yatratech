package com.yatra.tech.service;

import java.util.List;

import com.yatra.tech.entities.ABFUserLog;

public interface ABFUserLogService {

	public List<ABFUserLog> findAll();
	
	public ABFUserLog findBySuperPnr(String superPnr);
	
	public void saveOrUpdate(ABFUserLog userLog);

	public void saveOrUpdate(List<ABFUserLog> list);
}
