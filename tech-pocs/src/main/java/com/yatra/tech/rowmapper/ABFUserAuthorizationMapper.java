package com.yatra.tech.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yatra.tech.entities.ABFUserAuthorization;

public class ABFUserAuthorizationMapper implements RowMapper<ABFUserAuthorization> {

	@Override
	public ABFUserAuthorization mapRow(ResultSet rs, int rowNum) throws SQLException {
		ABFUserAuthorization userAuthorization = new ABFUserAuthorization();
		userAuthorization.setId(rs.getLong("id"));
		userAuthorization.setUserId(rs.getString("user_id"));
		userAuthorization.setAuthorization(rs.getString("authorization"));
		return userAuthorization;
	}

}
