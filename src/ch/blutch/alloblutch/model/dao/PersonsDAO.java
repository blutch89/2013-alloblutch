package ch.blutch.alloblutch.model.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;

import ch.blutch.alloblutch.model.comparators.PersonAlphabeticalComparator;
import ch.blutch.alloblutch.model.entity.LinkType;
import ch.blutch.alloblutch.model.entity.MoviePersonLink;
import ch.blutch.alloblutch.model.entity.Person;
import ch.blutch.alloblutch.utils.Log4JUtils;

public class PersonsDAO extends HibernateDAO<Person> {

	public PersonsDAO(Class persistendClass) {
		super(persistendClass);
		Log4JUtils.getLogger().trace("Hibernate: création d'un PersonsDAO");
	}
	
	public List<Person> findAllByAlphabeticalOrder() {
		Query q = session.createQuery("SELECT p FROM Person p ORDER BY p.firstName ASC");
		
		return q.list();
	}
	
	public List<Person> findPersonsByType(LinkType linkType) {
		List<Person> persons = this.findAll();
		List<Person> actors = new ArrayList<>();
		
		for (Person person : persons) {
			for (MoviePersonLink mpl : person.getMoviePersonLinks()) {
				if (mpl.getLinkType().equals(linkType)) {
					actors.add(person);
				}
			}
		}
		
		Collections.sort(actors, new PersonAlphabeticalComparator());
		
		return actors;
	}

}
