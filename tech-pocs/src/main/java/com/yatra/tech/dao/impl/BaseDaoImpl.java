package com.yatra.tech.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

import org.apache.log4j.Logger;

import com.yatra.tech.dao.BaseDao;

/**
 * This is a super class of all DAO's in yatra persistence layer and provides
 * implementation of methods lile getUpdatedBy() which are reused across all
 * DAOs
 * 
 * @param <T> 	the entity instance
 * @param <ID> 	the primary identifier of the entity
 */
public class BaseDaoImpl<T> implements BaseDao<T> {

	/**
	 * Entity manager factory instance
	 */
	@PersistenceUnit(unitName = "YATRADS")
	private EntityManagerFactory entityManagerFactory;

	private static final Logger logger = Logger.getLogger(BaseDaoImpl.class);

	public EntityManager getEntityManager() {
		return entityManagerFactory.createEntityManager();
	}

	@Override
	public boolean save(T entity) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		boolean status = false;

		try {
			transaction.begin();
			
			entityManager.persist(entity);
			status = true;
		} catch (Exception e) {
			logger.error("Exception occured while persisting object of class type : " + entity.getClass().getName(), e);
			
			if(transaction.isActive()) {
				transaction.rollback();
			}
			
		} finally {
			transaction.commit();
			entityManager.close();
		}

		return status;
	}

	@Override
	public boolean update(T entity) {
		EntityManager entityManager = getEntityManager();
		EntityTransaction transaction = entityManager.getTransaction();
		boolean status = false;

		try {
			transaction.begin();
			
			entityManager.merge(entity);
			status = true;
		} catch (Exception e) {
			logger.error("Exception occured while updating object of class type : " + entity.getClass().getName(), e);

			if(transaction.isActive()) {
				transaction.rollback();
			}

		} finally {
			transaction.commit();
			entityManager.close();
		}
		
		return status;
	}

}