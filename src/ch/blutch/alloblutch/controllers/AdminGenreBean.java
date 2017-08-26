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
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import ch.blutch.alloblutch.model.dao.GenreDAO;
import ch.blutch.alloblutch.model.entity.Genre;
import ch.blutch.alloblutch.model.entity.LinkType;
import ch.blutch.alloblutch.model.entity.Movie;
import ch.blutch.alloblutch.model.entity.MoviePersonLink;
import ch.blutch.alloblutch.model.entity.Person;
import ch.blutch.alloblutch.utils.Log4JUtils;
import ch.blutch.alloblutch.utils.ParamUtils;
import ch.blutch.alloblutch.utils.UploadUtils;

@ManagedBean
@RequestScoped
public class AdminGenreBean implements Serializable {

	private GenreDAO genreDAO;
	
	// Pour la partie ajout
	private Genre genre = new Genre();
	
	// Pour la partie list
	private Genre selectedGenre;
	
	// Pour la partie edit
	private String genreIdParam;
	
	
	@PostConstruct
	private void postConstruct() {
		Log4JUtils.getLogger().trace("Création du managed bean: AdminGenreBean");
		genreDAO = new GenreDAO(Genre.class);
		
		genreIdParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("editid");
		
		// Si la page demandée est la page de modification de genre, charger le genre concerné
		if (genreIdParam != null) {
			genre = genreDAO.findById(Long.parseLong(genreIdParam));
		}
	}
	
	@PreDestroy
	public void preDestroy() {
		Log4JUtils.getLogger().trace("Destruction du managed bean: AdminGenreBean");
	}
	
	public String addGenre() {
		genreDAO.saveOrUpdate(genre);
		Log4JUtils.getLogger().info("AdminGenreBean: genre ajouté");
		
		genre = new Genre();
		
		return null;
	}
	
	public List<Genre> getAllGenresByAlphabeticalOrder() {
		return genreDAO.findAllByAlphabeticalOrder();
	}
	
	public void gotoEditGenrePage() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/web-pages/admin-genres/admin-edit-genre.xhtml?editid=" + this.selectedGenre.getId());
		} catch (IOException e) {
			Log4JUtils.getLogger().warn("AdminGenreBean: impossible de rediriger l'utilisateur vers la page de modification de genre");
		}
	}
	
	public void gotoAddGenrePage() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/web-pages/admin-genres/admin-add-genre.xhtml");
		} catch (IOException e) {
			Log4JUtils.getLogger().warn("AdminGenreBean: impossible de rediriger l'utilisateur vers la page d'ajout de genre");
		}
	}
	
	public String editGenre() {				
		genreDAO.saveOrUpdate(genre);
		Log4JUtils.getLogger().info("AdminGenreBean: genre modifié");
		
		genre = new Genre();
		
		return "/web-pages/admin-genres/admin-list-genres.xhtml";
	}
	
	public String deleteGenre() {
		genreDAO.delete(this.selectedGenre);
		
		return null;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Genre getSelectedGenre() {
		return selectedGenre;
	}

	public void setSelectedGenre(Genre selectedGenre) {
		this.selectedGenre = selectedGenre;
	}

	public String getGenreIdParam() {
		return genreIdParam;
	}

	public void setGenreIdParam(String genreIdParam) {
		this.genreIdParam = genreIdParam;
	}
	
}
