package com.yatra.tech.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.yatra.tech.dao.ConfirmationPropertiesDao;
import com.yatra.tech.entities.ConfirmationProperties;

@Repository("confirmationPropertiesDao")
public class ConfirmationPropertiesDaoImpl extends GenericHibernateDAO<ConfirmationProperties, Long> implements ConfirmationPropertiesDao {

    @Override
    public void insertConfirmationProperty(String superPnr, String bookingId, String propertyName, String propetyValue) {
        ConfirmationProperties confirmationProp = null;
        confirmationProp = new ConfirmationProperties();
        confirmationProp.setBookingId(bookingId);
        confirmationProp.setSuperPnr(superPnr);
        confirmationProp.setPropertyName(propertyName);
        confirmationProp.setPropertyValue(propetyValue);
        super.saveOrUpdate(confirmationProp);
    }

    @Override
    public List<ConfirmationProperties> getConfirmationProperties(String superPnr, String bookingId){
    	DetachedCriteria criteria = DetachedCriteria.forClass(ConfirmationProperties.class);
		criteria.add(Restrictions.eq("superPnr", superPnr));
		criteria.add(Restrictions.eq("bookingId", bookingId));
		return super.findByCriteria(criteria);
    }

    @Override
    public Map<String,String> getConfirmationProperties(String superPnr) {
    	DetachedCriteria criteria = DetachedCriteria.forClass(ConfirmationProperties.class);
		criteria.add(Restrictions.eq("superPnr", superPnr));
		List<ConfirmationProperties> lstProperties = super.findByCriteria(criteria);

		Map<String,String> propertiesMap = new HashMap<String,String>();
		for(ConfirmationProperties properties: lstProperties) {
			propertiesMap.put(properties.getPropertyName(), properties.getPropertyValue());
		}
		return propertiesMap;
	}
    
	@Override
	public String getConfirmationJson(String superPnr) {
		String confirmationJson = "";
		DetachedCriteria criteria = DetachedCriteria.forClass(ConfirmationProperties.class);
		criteria.add(Restrictions.eq("superPnr", superPnr));
		criteria.add(Restrictions.eq("propertyName", "confirmationResponseJSON"));
		criteria.addOrder(Order.desc("createdOn"));
		List<ConfirmationProperties> confirmationProperties = super.findByCriteria(criteria);
		if (confirmationProperties != null && !confirmationProperties.isEmpty()) {
			confirmationJson = confirmationProperties.get(0).getPropertyValue();
		}
		return confirmationJson;
	}
}


