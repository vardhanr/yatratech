package com.yatra.reports.service.impl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yatra.reports.entities.ABFUserAuthorization;
import com.yatra.reports.entities.ABFUserLog;
import com.yatra.reports.service.ABFUserAuthorizationService;
import com.yatra.reports.service.ABFUserLogService;
import com.yatra.reports.service.TransactionService;

@Service("transactionService")
@Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED)
public class TransactionServiceImpl implements TransactionService {

	private static Logger logger = Logger.getLogger(TransactionServiceImpl.class);

	@Autowired
	private ABFUserLogService abfUserLogService;
	@Autowired
	private ABFUserAuthorizationService abfUserAuthorizationService;

	@Override
	@Transactional(noRollbackFor = Exception.class)
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
