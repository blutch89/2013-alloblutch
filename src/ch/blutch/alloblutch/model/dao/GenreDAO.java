package ch.blutch.alloblutch.model.dao;

import java.util.List;

import org.hibernate.Query;

import ch.blutch.alloblutch.model.entity.Genre;
import ch.blutch.alloblutch.model.entity.Person;
import ch.blutch.alloblutch.utils.Log4JUtils;

public class GenreDAO extends HibernateDAO<Genre> {

	public GenreDAO(Class persistendClass) {
		super(persistendClass);
		Log4JUtils.getLogger().trace("Hibernate: création d'un GenreDAO");
	}
	
	public List<Genre> findAllByAlphabeticalOrder() {
		Query q = session.createQuery("SELECT g FROM Genre g ORDER BY g.description ASC");
		
		return q.list();
	}

}
