package com.yatra.tech.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yatra.tech.entities.InsuranceProperties;

public class InsurancePropertiesRowMapper implements RowMapper<InsuranceProperties> {

	@Override
	public InsuranceProperties mapRow(ResultSet rs, int rowNum) throws SQLException {
		InsuranceProperties insuranceProperties = new InsuranceProperties();
		insuranceProperties.setPropertyValue(rs.getString("property_value"));
		insuranceProperties.setCreatedOn(rs.getTimestamp("created_on"));
		insuranceProperties.setPropertyName(rs.getString("property_name"));
	    return insuranceProperties;
	}
}