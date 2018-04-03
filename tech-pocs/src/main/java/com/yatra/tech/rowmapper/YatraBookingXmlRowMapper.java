package com.yatra.tech.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yatra.tech.entities.MoTransferQueue;

public class YatraBookingXmlRowMapper implements RowMapper<MoTransferQueue> {

	@Override
	public MoTransferQueue mapRow(ResultSet rs, int rowNum) throws SQLException {
		MoTransferQueue moTransferQueue = new MoTransferQueue();
		moTransferQueue.setBookingXml(rs.getString("mo_xml"));
		return moTransferQueue;
	}

}