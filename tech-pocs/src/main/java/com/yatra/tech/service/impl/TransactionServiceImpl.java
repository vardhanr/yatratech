package com.yatra.tech.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yatra.tech.entities.ABFUserAuthorization;
import com.yatra.tech.entities.ABFUserLog;
import com.yatra.tech.service.ABFUserAuthorizationService;
import com.yatra.tech.service.ABFUserLogService;
import com.yatra.tech.service.TransactionService;

@Service("transactionService")
@Transactional(readOnly = true)
public class TransactionServiceImpl implements TransactionService {

	private static Logger logger = Logger.getLogger(TransactionServiceImpl.class);

	@Autowired
	private ABFUserLogService abfUserLogService;
	@Autowired
	private ABFUserAuthorizationService abfUserAuthorizationService;

	@Override
	@Transactional(propagation = Propagation.REQUIRED, rollbackFor = { Exception.class }, readOnly = false)
	public void saveTransactionData(Map<String, Object> data) {
		logger.info("Initiating transaction");
		ABFUserAuthorization authorization = (ABFUserAuthorization) data.get("authorization");
		this.abfUserAuthorizationService.saveOrUpdate(authorization);
		ABFUserLog log = (ABFUserLog) data.get("log");
		System.out.println(log.getSsoToken());
		this.abfUserLogService.saveOrUpdate(log);
		ABFUserLog userLog = (ABFUserLog) data.get("userLog");
		this.abfUserLogService.saveOrUpdate(userLog);
	}

	public void setAbfUserLogService(ABFUserLogService abfUserLogService) {
		this.abfUserLogService = abfUserLogService;
	}

	public void setAbfUserAuthorizationService(ABFUserAuthorizationService abfUserAuthorizationService) {
		this.abfUserAuthorizationService = abfUserAuthorizationService;
	}
}
