package com.yatra.tech.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.yatra.tech.entities.YatraAddon;

public class YatraAddonRowMapper implements RowMapper<YatraAddon> {

	@Override
	public YatraAddon mapRow(ResultSet rs, int rowNum) throws SQLException {
		YatraAddon addon = new YatraAddon();
		addon.setAddOnId(rs.getLong("ua_id"));
		addon.setTtId(rs.getString("ttid"));
		return addon;
	}

}
