package com.yatra.tech.dao.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.yatra.tech.dao.TicketingPdfDao;
import com.yatra.tech.entities.TicketingPdf;

@Repository("ticketingPdfDao")
public class TicketingPdfDaoImpl extends GenericHibernateDAO<TicketingPdf, Long> implements TicketingPdfDao {

	@Override
	public List<TicketingPdf> findTicketsBySuperPnr(String superPnr) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TicketingPdf.class);
		criteria.add(Restrictions.eq("superPnr", superPnr));
		List<TicketingPdf> list = super.findByCriteria(criteria);
		if (list != null && !list.isEmpty()) {
			return list;
		}
		return null;
	}

	@Override
	public TicketingPdf findTicketBySuperPnr(String superPnr) {
		DetachedCriteria criteria = DetachedCriteria.forClass(TicketingPdf.class);
		criteria.add(Restrictions.eq("superPnr", superPnr));
		criteria.addOrder(Order.asc("id"));
		List<TicketingPdf> list = super.findByCriteria(criteria);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
}
