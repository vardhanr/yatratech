package com.yatra.tech.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.usertype.ParameterizedType;

public interface GenericDAO<T, ID extends Serializable> {

	public Session getSession();
	/**
	 * Get the entity by Id.
	 */
	public T findById(ID id);

	/**
	 * Saves or Updates the entity of the {@link ParameterizedType} return it.
	 * 
	 * @param entity
	 * @return T
	 */
	public void saveOrUpdate(T entity);

	/**
	 * Saves or Updates the a collection of entities of the
	 * {@link ParameterizedType} return it.
	 * 
	 * @param entity
	 * @return List<T> entities
	 */
	public void saveOrUpdateAll(List<T> entities);

	public List<T> findAll();
	
	public List<T> findByCriteria(Criteria criteria);
	
	public List<T> findByCriteria(DetachedCriteria criteria);
	
	public void delete(T entity);
	
	public void delete(List<T> entities);
	
	public void delete(ID id);
	
	public void setSessionFactory(SessionFactory sessionFactory);
}
