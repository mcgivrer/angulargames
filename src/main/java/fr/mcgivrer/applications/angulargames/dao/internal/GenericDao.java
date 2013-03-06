package fr.mcgivrer.applications.angulargames.dao.internal;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 * GenericDAO providing base of all database operation on entity T.
 * 
 * largely inspired by the following GIST: https://gist.github.com/1261256
 * Thanks to augusto for providing such a cool implementation.
 * 
 * @author mcgivrer
 * 
 * @param <T>
 * @param <PK>
 */
public class GenericDao<T extends Serializable, PK extends Serializable>
		implements Dao<T, PK> {

	protected Class<T> entityClass;
	@PersistenceContext(name = "application")
	protected EntityManager entityManager;

	public GenericDao() {
		ParameterizedType genericSuperClass = (ParameterizedType) getClass()
				.getGenericSuperclass();
		@SuppressWarnings("unchecked")
		Class<T> class1 = (Class<T>) genericSuperClass.getActualTypeArguments()[0];
		this.entityClass = class1;
	}

	public GenericDao(EntityManager em) {
		this();
		entityManager = em;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.mcgivrer.applications.angulargames.dao.internal.Dao#save(java.lang
	 * .Object)
	 */
	@Override
	public T save(T entity) {
		EntityTransaction tx = entityManager.getTransaction();
		boolean transactionActive = tx.isActive();
		try {
			if (!transactionActive) {
				tx.begin();
			}

			this.entityManager.persist(entity);
			if (!transactionActive) {
				tx.commit();
			}
			// entity = this.entityManager.merge(entity);
		} catch (Exception e) {
			tx.rollback();
		}

		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.mcgivrer.applications.angulargames.dao.internal.Dao#findById(java.
	 * io.Serializable)
	 */
	@Override
	public T findById(PK id) {
		return this.entityManager.find(entityClass, id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.mcgivrer.applications.angulargames.dao.internal.Dao#findByNamedQuery
	 * (java.lang.String, java.lang.Object[])
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQuery(final String name, Object... params) {
		javax.persistence.Query query = entityManager.createNamedQuery(name);

		for (int i = 0; i < params.length; i++) {
			query.setParameter(i + 1, params[i]);
		}

		return (List<T>) query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.mcgivrer.applications.angulargames.dao.internal.Dao#
	 * findByNamedQueryAndNamedParams(java.lang.String, java.util.Map)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<T> findByNamedQueryAndNamedParams(final String name,
			final Map<String, ?> params) {
		javax.persistence.Query query = entityManager.createNamedQuery(name);

		for (final Map.Entry<String, ?> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}

		return (List<T>) query.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.mcgivrer.applications.angulargames.dao.internal.Dao#merge(java.lang
	 * .Object)
	 */
	@Override
	public T merge(T entity) {
		return this.entityManager.merge(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.mcgivrer.applications.angulargames.dao.internal.Dao#delete(java.lang
	 * .Object)
	 */
	@Override
	public void delete(T entity) {

		EntityTransaction tx = entityManager.getTransaction();
		boolean transactionActive = tx.isActive();
		try {

			if (!transactionActive) {
				tx.begin();
			}

			this.entityManager.remove(entity);

			if (!transactionActive) {
				tx.commit();
			}
			// entity = this.entityManager.merge(entity);
		} catch (Exception e) {
			tx.rollback();
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.mcgivrer.applications.angulargames.dao.internal.Dao#findAll()
	 */
	@Override
	public List<T> findAll() {
		CriteriaQuery<T> query = this.entityManager.getCriteriaBuilder()
				.createQuery(entityClass);
		Root<T> roots = query.from(entityClass);
		query.select(roots);
		query.from(entityClass);
		return find(-1, -1, query);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.mcgivrer.applications.angulargames.dao.internal.Dao#findAll(int,
	 * int)
	 */
	@Override
	public List<T> findAll(int offset, int page) {
		CriteriaQuery<T> query = this.entityManager.getCriteriaBuilder()
				.createQuery(entityClass);

		Root<T> roots = query.from(entityClass);
		query.select(roots);
		query.from(entityClass);
		return find(offset, page, query);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see fr.mcgivrer.applications.angulargames.dao.internal.Dao#countAll()
	 */
	@Override
	public long countAll() {
		CriteriaBuilder qb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> cq = qb.createQuery(Long.class);
		cq.select(qb.count(cq.from(entityClass)));
		return entityManager.createQuery(cq).getSingleResult().longValue();
	}

	/**
	 * Return List of entity T with paginated scope form <code>offset</code> on
	 * <code>maxPage</code> occurrences.
	 * 
	 * @param offset
	 * @param maxPage
	 * @param query
	 * @return
	 */
	protected List<T> find(int offset, int maxPage, CriteriaQuery<T> query) {

		TypedQuery<T> hq = entityManager.createQuery(query);
		if (offset > 0) {
			hq.setFirstResult(offset);
		}
		if (maxPage > 0) {
			hq.setMaxResults(maxPage);
		}
		return hq.getResultList();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * fr.mcgivrer.applications.angulargames.dao.internal.Dao#setEntityManager
	 * (javax.persistence.EntityManager)
	 */
	@Override
	public void setEntityManager(EntityManager em) {
		this.entityManager = em;

	}

}