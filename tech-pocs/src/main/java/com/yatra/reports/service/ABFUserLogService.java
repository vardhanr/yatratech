package com.yatra.reports.service;

import java.util.List;

import com.yatra.reports.entities.ABFUserLog;

public interface ABFUserLogService {

	public List<ABFUserLog> findAll();
	
	public ABFUserLog findBySuperPnr(String superPnr);
	
	public void saveOrUpdate(ABFUserLog userLog);

	public void saveOrUpdate(List<ABFUserLog> list);
}
