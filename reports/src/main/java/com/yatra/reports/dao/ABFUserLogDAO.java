package com.yatra.reports.dao;

import com.yatra.reports.entities.ABFUserLog;

public interface ABFUserLogDAO extends GenericDAO<ABFUserLog, Long> {

	public ABFUserLog findBySuperPnr(String superPnr);
}
