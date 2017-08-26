package ch.blutch.alloblutch.controllers;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import ch.blutch.alloblutch.model.dao.PersonsDAO;
import ch.blutch.alloblutch.model.entity.Person;
import ch.blutch.alloblutch.utils.Log4JUtils;

@ManagedBean
@RequestScoped
public class PersonBean implements Serializable {
	
	private int idParam;
	{
		if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id") == null) {
			// TODO Afficher une erreur
		} else {
			idParam = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
		}
	}
	
	private PersonsDAO personsDAO;
	private Person person;
	
	
	@PostConstruct
	private void postConstruct() {
		Log4JUtils.getLogger().trace("Cr�ation du managed bean: AdminMovieBean");
		personsDAO = new PersonsDAO(Person.class);
		person = personsDAO.findById(idParam);
	}
	
	@PreDestroy
	public void preDestroy() {
		Log4JUtils.getLogger().trace("Destruction du managed bean: AdminMovieBean");
	}
	
	public Person getPerson() {
		return person;
	}
	public void setperson(Person person) {
		this.person = person;
	}
	
	
}
