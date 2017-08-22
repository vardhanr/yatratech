package com.yatra.reports.dao.impl;

import java.util.List;


import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.yatra.reports.dao.ABFUserAuthorizationDAO;
import com.yatra.reports.entities.ABFUserAuthorization;

@Repository("abfUserAuthorizationDao")
@Transactional
public class ABFUserAuthorizationDaoImpl extends GenericHibernateDAO<ABFUserAuthorization, Long> implements ABFUserAuthorizationDAO {

	@Override
	public ABFUserAuthorization findByUserId(String userId) {
		DetachedCriteria criteria = DetachedCriteria.forClass(ABFUserAuthorization.class);
		criteria.add(Restrictions.eq("userId", userId));
		List<ABFUserAuthorization> list = super.findByCriteria(criteria);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

}
