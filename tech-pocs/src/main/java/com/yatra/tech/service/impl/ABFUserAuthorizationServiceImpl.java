package com.yatra.tech.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yatra.tech.dao.ABFUserAuthorizationDAO;
import com.yatra.tech.entities.ABFUserAuthorization;
import com.yatra.tech.service.ABFUserAuthorizationService;

@Service("abfUserAuthorizationService")
public class ABFUserAuthorizationServiceImpl implements ABFUserAuthorizationService {

	@Autowired
	private ABFUserAuthorizationDAO abfUserAuthorizationDao;

	@Override
	public List<ABFUserAuthorization> findAll() {
		return this.abfUserAuthorizationDao.findAll();
	}

	@Override
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
