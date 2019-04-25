package com.yatra.tech.dao;

import com.yatra.tech.entities.YatraAddon;

/**
 * An interface for User add on dao object.
 *
 * @author Sadiya Kazi
 *
 */
public interface UserAddOnDao extends GenericDAO<YatraAddon, Long> {
	
	public YatraAddon getAddon(String ttId, Long addonId);
}
