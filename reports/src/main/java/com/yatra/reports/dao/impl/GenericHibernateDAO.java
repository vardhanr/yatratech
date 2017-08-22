package com.yatra.reports.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.yatra.reports.dao.GenericDAO;

public abstract class GenericHibernateDAO<T, ID extends Serializable> implements GenericDAO<T, ID> {

	private final Class<T> parameterizedType;

	@Autowired
	protected SessionFactory sessionFactory;

	@SuppressWarnings("unchecked")
	public GenericHibernateDAO() {
		this.parameterizedType = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}

	@Override
	public Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public T findById(ID id) {
		return getSession().get(this.parameterizedType, id);
	}

	@Override
	public void saveOrUpdate(T entity) {
		getSession().saveOrUpdate(entity);
	}

	@Override
	public void saveOrUpdateAll(List<T> entities) {
		for (T entity : entities) {
			this.saveOrUpdate(entity);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findAll() {
		DetachedCriteria criteria = DetachedCriteria.forClass(this.parameterizedType);
		Criteria criteria2 = criteria.getExecutableCriteria(getSession());
		return criteria2.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriteria(Criteria criteria) {
		return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByCriteria(DetachedCriteria criteria) {
		return criteria.getExecutableCriteria(getSession()).list();
	}

	@Override
	public void delete(T entity) {
		getSession().delete(entity);
	}

	@Override
	public void delete(List<T> entities) {
		for (T entity : entities) {
			delete(entity);
		}
	}

	@Override
	public void delete(ID id) {
		T entity = findById(id);
		getSession().delete(entity);
	}

	public Class<T> getParameterizedType() {
		return parameterizedType;
	}

	@Override
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
}
