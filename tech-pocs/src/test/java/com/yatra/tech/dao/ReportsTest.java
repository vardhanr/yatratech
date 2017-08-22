package com.yatra.tech.dao;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.yatra.tech.dto.ExcelLineItem;
import com.yatra.tech.entities.ABFUserAuthorization;
import com.yatra.tech.entities.ABFUserLog;
import com.yatra.tech.service.ABFUserAuthorizationService;
import com.yatra.tech.service.ABFUserLogService;
import com.yatra.tech.service.ExcelConverterService;
import com.yatra.tech.service.TransactionService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ReportsTestConfig.class })
public class ReportsTest {

	@Autowired
	private ABFUserAuthorizationService abfUserAuthorizationService;
	@Autowired
	private ABFUserLogService abfUserLogService;
	@Autowired
	private ExcelConverterService excelConverterService;
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private SpringTemplateDAO springTemplateDao;

	@Test
	public void testABFDao() {
		List<ABFUserAuthorization> list = this.abfUserAuthorizationService.findAll();
		System.out.println(list);
		Assert.assertNotNull(list);
	}

	@Test
	public void testABFLogDao() {
		List<ABFUserLog> list = this.abfUserLogService.findAll();
		System.out.println(list);
		Assert.assertEquals(list.isEmpty(), true);
	}
	
	@Test
	public void testSpringTemplateDao() {
		List<ABFUserAuthorization> list = this.springTemplateDao.findAllUserAuthorization();
		System.out.println(list);
		Assert.assertNotNull(list);
	}

	@Test
	public void testExcelParsing() {
		File file = new File(ClassLoader.getSystemResource("test-data/airfailed-timestamp-analysis.xlsx").getFile());
		List<ExcelLineItem> lineItems = this.excelConverterService.readExcelData(ExcelLineItem.class, file, "booking-data");
		System.out.println(lineItems);
		Assert.assertNotNull(lineItems);
	}

	@Test
	public void testTransaction() throws Exception {
		Map<String, Object> map = new HashMap<>();
		ABFUserAuthorization authorization = new ABFUserAuthorization();
		String userId = "rahul";
		authorization.setUserId(userId);
		authorization.setAuthorization("REVIEW");
		map.put("authorization", authorization);
		
		ABFUserLog userLog = new ABFUserLog();
		String superPnr = "938475";
		userLog.setIdAddress("192.168.61.21");
		userLog.setPricingId("92834798347");
		userLog.setSearchId("93845798345");
		userLog.setSsoToken("9845798374585");
		userLog.setStage("REVIEW");
		userLog.setSuperPnr(superPnr);
		userLog.setTenant("dom2");
		userLog.setTtid("0498598437");
		userLog.setUserId("rahul");
		map.put("userLog", userLog);
		try {
			this.transactionService.saveTransactionData(map);
		} catch (Exception e) {
		}
		
		authorization = this.abfUserAuthorizationService.findByUserId(userId);
		Assert.assertNull(authorization);
		
		userLog = this.abfUserLogService.findBySuperPnr(superPnr);
		Assert.assertNull(userLog);
	}
}
