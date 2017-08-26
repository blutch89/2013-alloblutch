package ch.blutch.alloblutch.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import ch.blutch.alloblutch.model.dao.PersonsDAO;
import ch.blutch.alloblutch.model.entity.LinkType;
import ch.blutch.alloblutch.model.entity.Movie;
import ch.blutch.alloblutch.model.entity.MoviePersonLink;
import ch.blutch.alloblutch.model.entity.Person;
import ch.blutch.alloblutch.utils.DateUtils;
import ch.blutch.alloblutch.utils.Log4JUtils;
import ch.blutch.alloblutch.utils.ParamUtils;
import ch.blutch.alloblutch.utils.UploadUtils;

@ManagedBean
@RequestScoped
public class AdminPersonBean implements Serializable {

	private PersonsDAO personsDAO;
	
	// Pour la partie ajout
	private Person person = new Person();
	private UploadedFile picture;
	
	// Pour la partie liste
	private Person selectedPerson;
	
	// Pour la partie modification
	private String personIdParam;
	
	@PostConstruct
	private void postConstruct() {
		Log4JUtils.getLogger().trace("Création du managed bean: AdminPersonBean");
		personsDAO = new PersonsDAO(Person.class);
		
		personIdParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("editid");
		
		// Si la page demandée est la page de modification, charger la personne concernée
		if (personIdParam != null) {
			person = personsDAO.findById(Long.parseLong(personIdParam));
		}
	}
	
	@PreDestroy
	public void preDestroy() {
		Log4JUtils.getLogger().trace("Destruction du managed bean: AdminPersonBean");
	}
	
	public String addPerson() {
		// Upload le fichier
		if (this.picture != null) {
			try {
				String pictureName = person.getFirstName() + person.getLastName() + DateUtils.dateFormat.format(person.getBirthday());
				pictureName = UploadUtils.purifyPictureName(pictureName);
				pictureName += UploadUtils.getFileExtension(picture.getFileName());
				UploadUtils.uploadFile(picture, ParamUtils.personsPicturesDirectory, pictureName);
				
				// Ajoute le nom de fichier image à l'entité movie
				person.setPictureName(pictureName);	
			} catch (IOException e) {
				Log4JUtils.getLogger().error("Impossible d'uploader l'image sur le serveur");
				e.printStackTrace();
			}
		}
		
		personsDAO.saveOrUpdate(person);
		Log4JUtils.getLogger().info("AdminPersonBean: personne ajoutée");
		
		person = new Person();
		
		return null;
	}
	
	public List<Person> getAllPersonsByAlphabeticalOrder() {
		return personsDAO.findAllByAlphabeticalOrder();
	}
	
	public void gotoEditPersonPage() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/web-pages/admin-persons/admin-edit-person.xhtml?editid=" + this.selectedPerson.getId());
		} catch (IOException e) {
			Log4JUtils.getLogger().warn("AdminPersonBean: impossible de rediriger l'utilisateur vers la page de modification de personne");
		}
	}
	
	public void gotoAddPersonPage() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/web-pages/admin-persons/admin-add-person.xhtml");
		} catch (IOException e) {
			Log4JUtils.getLogger().warn("AdminPersonBean: impossible de rediriger l'utilisateur vers la page d'ajout de personne");
		}
	}
	
	public String editPerson() {
		// Upload le fichier
		if (this.picture != null) {
			try {
				// Upload l'image
				String pictureName = person.getFirstName() + person.getLastName() + DateUtils.dateFormat.format(person.getBirthday());
				pictureName = UploadUtils.purifyPictureName(pictureName);
				pictureName += UploadUtils.getFileExtension(picture.getFileName());
				UploadUtils.uploadFile(picture, ParamUtils.personsPicturesDirectory, pictureName);
				
				// Supprime l'ancienne image
				Path oldPicture = Paths.get(ParamUtils.personsPicturesDirectory + this.person.getPictureName());
				Files.deleteIfExists(oldPicture);
				
				// Ajoute le nom de fichier image à l'entité Person
				this.person.setPictureName(pictureName);	
			} catch (IOException e) {
				Log4JUtils.getLogger().error("Impossible d'uploader l'image sur le serveur");
				e.printStackTrace();
			}
		}
		
		personsDAO.saveOrUpdate(person);
		Log4JUtils.getLogger().info("AdminPersonBean: personne modifiée");
		
		person = new Person();
		
		return "/web-pages/admin-persons/admin-list-persons.xhtml";
	}
	
	public String deletePerson() {
		personsDAO.delete(this.selectedPerson);
		
		return null;
	}

	public UploadedFile getPicture() {
		return picture;
	}

	public void setPicture(UploadedFile picture) {
		this.picture = picture;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public Person getSelectedPerson() {
		return selectedPerson;
	}

	public void setSelectedPerson(Person selectedPerson) {
		this.selectedPerson = selectedPerson;
	}

	public String getPersonIdParam() {
		return personIdParam;
	}

	public void setPersonIdParam(String personIdParam) {
		this.personIdParam = personIdParam;
	}
	
}
