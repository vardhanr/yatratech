package com.yatra.tech.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.yatra.tech.dao.SpringTemplateDAO;
import com.yatra.tech.entities.ABFUserAuthorization;
import com.yatra.tech.rowmapper.ABFUserAuthorizationMapper;

@Repository("springTemplateDao")
public class SpringTemplateDaoImpl implements SpringTemplateDAO {

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
