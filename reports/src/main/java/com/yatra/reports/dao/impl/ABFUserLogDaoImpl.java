package com.yatra.reports.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yatra.reports.dao.ABFUserLogDAO;
import com.yatra.reports.entities.ABFUserLog;

@Repository("abfUserLogDao")
@Transactional
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
