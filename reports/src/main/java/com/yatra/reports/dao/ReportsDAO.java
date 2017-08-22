package com.yatra.reports.dao;

import java.util.List;

import com.yatra.reports.entities.ABFUserAuthorization;

public interface ReportsDAO {

	public List<ABFUserAuthorization> findAllUserAuthorization();
}
