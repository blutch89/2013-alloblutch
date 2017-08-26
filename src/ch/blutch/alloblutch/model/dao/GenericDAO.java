package ch.blutch.alloblutch.model.dao;

import java.io.Serializable;
import java.util.List;

interface GenericDAO <T> {
	  public T findById(long id);  
	  
	  public List<T> findAll();  
	  
	  public T saveOrUpdate(T entity);
	  
	  public void delete( T entity);    
}
