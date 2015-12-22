package org.utmost.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * Packaging hibernate access database class
 * 
 * @author wanglm
 * 
 */
// @Scope("prototype") non singleton
@Repository("DBSupport")
public class DBSupport{
	private static Logger logger = Logger.getLogger(DBSupport.class);

	@Autowired
    private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory(){
		return this.sessionFactory;
	}

	/**
	 * MapSession exit
	 * 
	 * @return
	 */
	public Session getDynamicSession() {
		return this.sessionFactory.getCurrentSession();
	}

	/**
	 * return list according hql
	 * 
	 * @param hql
	 * @return
	 */
	public List findByHql(String hql, boolean bool) {
		logger.debug("findByHql List with hql: " + hql);
		try {
			return getDynamicSession().createQuery(hql).setCacheable(bool).list();
		} catch (RuntimeException re) {
			logger.error("findByHql failed");
			throw re;
		}
	}
	/**
	 * sql query
	 * @param sql
	 * @return
	 */
	public Query createSQLQuery(String sql) {
		logger.debug("createSQLQuery List with hql: " + sql);
		try {
			return getDynamicSession().createSQLQuery(sql);
		} catch (RuntimeException re) {
			logger.error("createSQLQuery failed", re);
			throw re;
		}
	}

	/**
	 * condition selecting
	 * 
	 * @param exampleEntity
	 * @return
	 */
	// public List findByExample(Object exampleEntity) {
	// return this.getHibernateTemplate().findByExample(exampleEntity);
	// }
	/**
	 * return first object
	 * 
	 * @param hql
	 * @return
	 */
	public Object findByHqlToObj(String hql) {
		Iterator it = this.findByHql(hql, false).iterator();
		if (it.hasNext()) {
			return it.next();
		}
		return null;
	}

	/**
	 * query all 
	 * 
	 * @param tableName
	 * @return
	 */
	public List findAll(String tableName) {
		logger.debug("findAll List with tableName: " + tableName);
		try {
			return getDynamicSession().createQuery("from " + tableName).list();
		} catch (RuntimeException re) {
			logger.error("findAll failed", re);
			throw re;
		}
	}

	/**
	 * add
	 * 
	 * @param entity
	 */
	public void save(Object entity) {
		logger.debug("save void with entity: " + entity);
		try {
			getDynamicSession().save(entity);
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

//	/**
//	 * add
//	 * 
//	 * @param entity
//	 */
//	public void save(String entityName, Map entity) {
//		logger.debug("save void with entity by entityName: " + entity);
//		try {
//			getDynamicSession().save(entityName, entity);
//		} catch (RuntimeException re) {
//			logger.error("save failed", re);
//			throw re;
//		}
//	}
	
	/**
	 * saveAndReturnUUID: save and return uuid
	 *
	 * @param entityName table name
	 * @param entity	need to save object
	 * @return String	uuid
	 * @throws 
	*/
	public String save(String entityName, Map entity) {
		logger.debug("save void with entity by entityName: " + entity);
		
		try {
			getDynamicSession().save(entityName, entity);
			return entity.get("uuid").toString();
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/**
	 * external save
	 * 
	 * @param entity
	 */
	public void exteriorSave(String entityName, Map entity) {
		logger.debug("save void with entity by entityName: " + entity);
		Session session = getDynamicSession();
		Transaction tx = session.beginTransaction();
		try {
			session.save(entityName, entity);
			tx.commit();
		} catch (RuntimeException re) {
			tx.rollback();
			logger.error("save failed", re);
			throw re;
		} finally {
			session.close();
		}
	}

	/**
	 * modify
	 * 
	 * @param entity
	 */
	public void update(Object entity) {
		logger.debug("update void with entity: " + entity);
		try {
			getDynamicSession().update(entity);
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	/**
	 * modify
	 * 
	 * @param entity
	 */
	public void update(String entityName, Map entity) {
		logger.debug("update void with entity by entityName: " + entity);
		try {
			getDynamicSession().update(entityName, entity);
		} catch (RuntimeException re) {
			logger.error("update failed", re);
			throw re;
		}
	}

	/**
	 * save or update
	 * 
	 * @param entity
	 * @return
	 */
	public void saveOrUpdate(Object entity) {
		logger.debug("saveOrUpdate void with entity: " + entity);
		try {
			getDynamicSession().saveOrUpdate(entity);
		} catch (RuntimeException re) {
			logger.error("saveOrUpdate failed", re);
			throw re;
		}
	}

	/**
	 * save or update
	 * 
	 * @param entity
	 * @return
	 */
	public String saveOrUpdate(String entityName, Map entity) {
		logger.debug("saveOrUpdate void with entitie: " + entity);
		
		
		try {
			// uuid handle null
			if (entity.containsKey("uuid")) {
				Object obj = entity.get("uuid");
				if (obj == null || obj.toString().equalsIgnoreCase("null")
						|| obj.toString().equals("")) {
					entity.remove("uuid");
				}
			}
			getDynamicSession().saveOrUpdate(entityName, entity);
			
			return entity.get("uuid").toString();
			
		} catch (RuntimeException re) {
			logger.error("saveOrUpdate failed", re);
			throw re;
		}
	}

	/**
	 * save or update list
	 * 
	 * @param entity
	 * @return
	 */
	public void saveOrUpdateAll(Collection<Object> entities) {
		logger.debug("saveOrUpdateAll void with entities: " + entities);
		try {
			for (Object entity : entities) {
				getDynamicSession().saveOrUpdate(entity);
			}
			// this.getHibernateTemplate().saveOrUpdateAll(entities);
		} catch (RuntimeException re) {
			logger.error("saveOrUpdateAll failed", re);
			throw re;
		}
	}

	/**
	 * delete
	 * 
	 * @param entity
	 * @return
	 */
	public void delete(String entityName, Map entity) {
		logger.debug("delete void with entity by entityName: " + entity);
		try {
			getDynamicSession().delete(entityName, entity);
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * delete
	 * 
	 * @param entity
	 * @return
	 */
	public void delete(Object entity) {
		logger.debug("delete void with entity: " + entity);
		try {
			getDynamicSession().delete(entity);
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/**
	 * delete according hql
	 * 
	 * @param hql
	 */
	public void deleteByHql(String hql) {
		List list = this.findByHql(hql, false);
		for (Object o : list) {
			this.delete(o);
		}
	}

	/**
	 * delete list
	 * 
	 * @param entities
	 */
	public void deleteAll(Collection<Object> entities) {
		logger.debug("deleteAll void with entities: " + entities);
		try {
			for (Object entity : entities) {
				getDynamicSession().delete(entity);
			}
			// this.getHibernateTemplate().deleteAll(entities);
		} catch (RuntimeException re) {
			logger.error("deleteAll failed", re);
			throw re;
		}
	}

	/**
	 * paging, return all recorder when pageNo or pageList less than or equal 0
	 * 
	 * @param pageNo
	 *            page number, start from 1, return all recorder when less than or equal 0
	 * @param pageList
	 *            a page recorder list, return all recorder when less than or equal 0
	 * @param query
	 */
	// public void pagination(int pageNo, int pageList, Query query) {
	// if (pageList > 0 && pageNo > 0) {
	// query.setMaxResults(pageList);
	// int beginIndex = (pageNo - 1) * pageList;
	// query.setFirstResult(beginIndex);
	// }
	// }
	/**
	 * paging, return all recorder when pageNo or pagelist less than or equal 0
	 * 
	 * @param pageNo
	 *            page number, start from 1,return all recorder when less than or equal 0
	 * @param pageList
	 *            a page recorder list, return all recorder when less than or equal 0
	 * @param hql
	 */
	public List pagination(int pageNo, int pageSize, String hql) {
		List list = new ArrayList();
		if (pageNo > 0 && pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			list = getDynamicSession().createQuery(hql).setFirstResult(
					pageNo).setMaxResults(pageSize).list();
		}
		return list;
	}


	/**
	 * standard sql paging
	 * 
	 * @param pageNo
	 * @param pageSize
	 * @param sql
	 * @return
	 */
	// public List paginationBySql(int pageNo, int pageSize, String sql) {
	// List list = this.getSession().createSQLQuery(sql)
	// .setFirstResult(pageNo).setMaxResults(pageSize).list();
	// return list;
	// }
	/**
	 * paging, return all recorder when pageNo or page list less than or equal 0
	 * 
	 * @param pageNo
	 *            page number, start from 1, return all data when less than or equal 0
	 * @param pageList
	 *            a page recorder list, return all data when less than or equal 0
	 * @param criteria
	 */
	// public void pagination(int pageNo, int pageList, Criteria criteria) {
	// if (pageList > 0 && pageNo > 0) {
	// criteria.setMaxResults(pageList);
	// int beginIndex = (pageNo - 1) * pageList;
	// criteria.setFirstResult(beginIndex);
	// }
	// }
}