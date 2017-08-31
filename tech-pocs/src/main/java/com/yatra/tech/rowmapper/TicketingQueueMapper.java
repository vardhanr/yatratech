package com.yatra.tech.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yatra.tech.dto.TicketingQueue;

public class TicketingQueueMapper implements RowMapper<TicketingQueue> {

	@Override
	public TicketingQueue mapRow(ResultSet rs, int rowNum) throws SQLException {
		TicketingQueue ticketingQueue = new TicketingQueue();
		ticketingQueue.setId(rs.getLong("id"));
		ticketingQueue.setCreatedOn(rs.getTimestamp("created_on"));
		ticketingQueue.setStatus(rs.getString("status"));
		ticketingQueue.setAttempts(rs.getInt("attempts"));
		ticketingQueue.setSupplierCodes(rs.getString("supplier_codes"));
		ticketingQueue.setSuperPnr(rs.getString("super_pnr"));
		return ticketingQueue;
	}

}
