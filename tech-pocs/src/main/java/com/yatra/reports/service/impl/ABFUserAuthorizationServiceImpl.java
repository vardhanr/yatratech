package com.yatra.reports.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yatra.reports.dao.ABFUserAuthorizationDAO;
import com.yatra.reports.entities.ABFUserAuthorization;
import com.yatra.reports.service.ABFUserAuthorizationService;

@Service("abfUserAuthorizationService")
@Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRES_NEW)
public class ABFUserAuthorizationServiceImpl implements ABFUserAuthorizationService {

	@Autowired
	private ABFUserAuthorizationDAO abfUserAuthorizationDao;

	@Override
	@Transactional
	public List<ABFUserAuthorization> findAll() {
		return this.abfUserAuthorizationDao.findAll();
	}

	@Override
	@Transactional(readOnly = false)
	public void saveOrUpdate(List<ABFUserAuthorization> list) {
		this.abfUserAuthorizationDao.saveOrUpdateAll(list);
	}
	
	@Override
	public ABFUserAuthorization findByUserId(String userId) {
		return this.abfUserAuthorizationDao.findByUserId(userId);
	}
	
	@Override
	public void saveOrUpdate(ABFUserAuthorization authorization) {
		this.abfUserAuthorizationDao.saveOrUpdate(authorization);
	}

	public void setAbfUserAuthorizationDao(ABFUserAuthorizationDAO abfUserAuthorizationDao) {
		this.abfUserAuthorizationDao = abfUserAuthorizationDao;
	}
}
