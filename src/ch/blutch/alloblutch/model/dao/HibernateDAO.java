package ch.blutch.alloblutch.model.dao;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import ch.blutch.alloblutch.utils.HibernateUtils;
import ch.blutch.alloblutch.utils.Log4JUtils;

public class HibernateDAO <T> implements GenericDAO<T>, Serializable {

	protected Class<T> persistentClass;
	public Session session = HibernateUtils.getCurrentSession();
	
	public HibernateDAO(Class persistendClass) {
		this.persistentClass = persistendClass;
	}
	
	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	@Override
	public T findById(long id) {
		Log4JUtils.getLogger().trace("Hibernate: accès par ID à une entité de la BDD");
		T entity = (T)session.load(persistentClass, id);
		
		return entity;
	}

	@Override
	public List<T> findAll() {
		Log4JUtils.getLogger().trace("Hibernate: accès findAll d'un type d'entité de la BDD");
		List<T> entities = session.createCriteria(persistentClass).list();
		
		return entities;
	}

	@Override
	public T saveOrUpdate(T entity) {
		Log4JUtils.getLogger().trace("Hibernate: MAJ/save d'un entité dans la BDD");
		session.beginTransaction();
		session.saveOrUpdate(entity);
		session.getTransaction().commit();
		
		return entity;
	}

	@Override
	public void delete(T entity) {
		Log4JUtils.getLogger().trace("Hibernate: suppression d'une entité dans la BDD");
		session.beginTransaction();
		session.delete(entity);
		session.getTransaction().commit();
	}

}
