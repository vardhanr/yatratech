package com.yatra.tech.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yatra.tech.entities.PaxDetail;

public class PaxDetailRowMapper implements RowMapper<PaxDetail> {

	@Override
	public PaxDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
		PaxDetail detail = new PaxDetail();
		detail.setSuperPnr(rs.getString("super_pnr"));
		detail.setTtid(rs.getString("ttid"));
		detail.setPaxInsured(rs.getString("pd_insured"));
		return detail;
	}

}
