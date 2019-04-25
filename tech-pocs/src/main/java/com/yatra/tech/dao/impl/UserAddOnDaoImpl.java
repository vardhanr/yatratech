package com.yatra.tech.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.yatra.tech.dao.UserAddOnDao;
import com.yatra.tech.entities.YatraAddon;

/**
 * This is an implementation class for saving selected optional add ons.
 *
 * @author 236628
 *
 */
@Repository("userAddOnDao")
public class UserAddOnDaoImpl extends GenericHibernateDAO<YatraAddon, Long> implements UserAddOnDao {

	@Override
	public YatraAddon getAddon(String ttId, Long addonId){
		List<YatraAddon> addonList = null;
    	Query query = super.getSession().createQuery("from YatraAddon where ttId=:ttId and addOnId=:addOnId");
	    query.setParameter("ttId", ttId);
	    query.setParameter("addOnId", addonId);
	    addonList = (List<YatraAddon>) query.getResultList();
	    if (addonList != null && !addonList.isEmpty()) {
	    	return addonList.get(0);
	    }
	    
	    return null;
	}
}
