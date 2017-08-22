package com.yatra.tech.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.yatra.tech.dao.ABFUserLogDAO;
import com.yatra.tech.entities.ABFUserLog;

@Repository("abfUserLogDao")
public class ABFUserLogDaoImpl extends GenericHibernateDAO<ABFUserLog, Long> implements ABFUserLogDAO {

	@Override
	public ABFUserLog findBySuperPnr(String superPnr) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ABFUserLog.class);
		criteria.add(Restrictions.eq("superPnr", superPnr));
		List<ABFUserLog> list = super.findByCriteria(criteria);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

}
