package com.yatra.tech.dao.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yatra.tech.dao.SpringTemplateDAO;
import com.yatra.tech.dto.TicketingQueue;
import com.yatra.tech.entities.ABFUserAuthorization;
import com.yatra.tech.rowmapper.ABFUserAuthorizationMapper;
import com.yatra.tech.rowmapper.TicketingQueueMapper;
import com.yatra.tech.utils.TechUtils;

@Repository("springTemplateDao")
public class SpringTemplateDaoImpl implements SpringTemplateDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<ABFUserAuthorization> findAllUserAuthorization() {
		String sql = "select * from abf_user_authorization";
		return this.jdbcTemplate.query(sql, new ABFUserAuthorizationMapper());
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void testTicketingData() {
		
		List<String> supplierCodes = new ArrayList<String>();
		supplierCodes.add("1G");
		supplierCodes.add("GALDOM");
		
		String sql = "select * from ticketing_queue t where t.created_on>? and t.created_on<? and t.`status` in "
				+ "('FAILED_EMPTYRESPONSE', 'FAILED_NOTICKETS', 'FAILED_PARTIALTICKETS', 'FAILED') and t.attempts<3;";
		List<TicketingQueue> list = this.jdbcTemplate.query(sql, new Object[] {"2017-08-31 18:30:41.482", "2017-08-31 20:28:41.482"}, new TicketingQueueMapper());
		List<TicketingQueue> newList = new ArrayList<>();

		System.out.println("Size before filtering : " + list.size());
		for (TicketingQueue ticketingQueue : list) {
			String suppCodes = ticketingQueue.getSupplierCodes();
			System.out.println(suppCodes);
			if (StringUtils.isNotEmpty(suppCodes)) {
				List<String> supCodesList = Arrays.asList(suppCodes.split(","));
				
				List<String> intersection = TechUtils.intersection(supplierCodes, supCodesList);
				if (intersection == null || intersection.isEmpty()) {
					newList.add(ticketingQueue);
				}
			}
		}
	
		System.out.println("Size after Filtering : " +newList.size());
	}
}
