package com.yatra.reports.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yatra.reports.dao.ReportsDAO;
import com.yatra.reports.entities.ABFUserAuthorization;
import com.yatra.reports.rowmapper.ABFUserAuthorizationMapper;

@Repository("reportsDao")
public class ReportsDaoImpl implements ReportsDAO {

	@Autowired private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<ABFUserAuthorization> findAllUserAuthorization() {
		String sql = "select * from abf_user_authorization";
		return this.jdbcTemplate.query(sql, new ABFUserAuthorizationMapper());
	}
	
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
}
