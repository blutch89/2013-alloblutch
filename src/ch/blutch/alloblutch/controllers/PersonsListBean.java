package ch.blutch.alloblutch.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import ch.blutch.alloblutch.model.dao.PersonsDAO;
import ch.blutch.alloblutch.model.entity.LinkType;
import ch.blutch.alloblutch.model.entity.Person;
import ch.blutch.alloblutch.utils.Log4JUtils;

@ManagedBean
@RequestScoped
public class PersonsListBean implements Serializable {

	private PersonsDAO personsDAO;
	private String typeParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("type");
	
	@PostConstruct
	private void postConstruct() {
		Log4JUtils.getLogger().trace("Création du managed bean: AdminMovieBean");
		personsDAO = new PersonsDAO(Person.class);
	}
	
	@PreDestroy
	public void preDestroy() {
		Log4JUtils.getLogger().trace("Destruction du managed bean: AdminMovieBean");
	}
	
	public String getPageTitle() {
		if (typeParam == null) {
			return "Célébrités";
		}
		
		switch (typeParam) {
			case "directors":
				return "Réalisateurs";
			case "producers":
				return "Producteurs";
			case "actors":
				return "Acteurs";
			default:
				return "Célébrités";
		}
	}
	
	public List<Person> findPersonsFromUrl() {
		List<Person> persons = new ArrayList<>();
		
		if (typeParam == null) {
			return personsDAO.findAllByAlphabeticalOrder();
		}
		
		switch (typeParam) {
			case "directors":
				persons = findPersonsFromType(LinkType.DIRECTOR);
				break;
			case "producers":
				persons = findPersonsFromType(LinkType.PRODUCER);
				break;
			case "actors":
				persons = findPersonsFromType(LinkType.ACTOR);
				break;
			default:
				persons = personsDAO.findAll();
				break;
		}
		
		return persons;
	}
	
	private List<Person> findPersonsFromType(LinkType linkType) {
		List<Person> personsToReturn = new ArrayList<>();
		List<Person> persons = personsDAO.findAllByAlphabeticalOrder();
		
		for (Person person : persons) {
			if (person.isType(linkType)) {
				personsToReturn.add(person);
			}
		}
		
		return personsToReturn;
	}
	
}
