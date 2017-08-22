package com.yatra.tech.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yatra.tech.dao.ABFUserLogDAO;
import com.yatra.tech.entities.ABFUserLog;
import com.yatra.tech.service.ABFUserLogService;

@Service("abfUserLogService")
@Transactional
public class ABFUserLogServiceImpl implements ABFUserLogService {

	@Autowired
	private ABFUserLogDAO abfUserLogDao;

	@Override
	public List<ABFUserLog> findAll() {
		return this.abfUserLogDao.findAll();
	}

	@Override
	public void saveOrUpdate(List<ABFUserLog> list) {
		this.abfUserLogDao.saveOrUpdateAll(list);
	}

	@Override
	public ABFUserLog findBySuperPnr(String superPnr) {
		return this.abfUserLogDao.findBySuperPnr(superPnr);
	}
	
	@Override
	public void saveOrUpdate(ABFUserLog userLog) {
		this.abfUserLogDao.saveOrUpdate(userLog);
	}
	
	public void setAbfUserLogDao(ABFUserLogDAO abfUserLogDao) {
		this.abfUserLogDao = abfUserLogDao;
	}
}
