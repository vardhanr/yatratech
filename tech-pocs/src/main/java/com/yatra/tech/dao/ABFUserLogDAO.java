package com.yatra.tech.dao;

import com.yatra.tech.entities.ABFUserLog;

public interface ABFUserLogDAO extends GenericDAO<ABFUserLog, Long> {

	public ABFUserLog findBySuperPnr(String superPnr);
}
