package ch.blutch.alloblutch.controllers;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import ch.blutch.alloblutch.model.dao.MoviesDAO;
import ch.blutch.alloblutch.model.dao.PersonsDAO;
import ch.blutch.alloblutch.model.entity.Movie;
import ch.blutch.alloblutch.model.entity.Person;
import ch.blutch.alloblutch.utils.Log4JUtils;

@ManagedBean
@RequestScoped
public class MovieBean implements Serializable {
	
	private int idParam;
	{
		if (FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id") == null) {
			// TODO Afficher une erreur
		} else {
			idParam = Integer.parseInt(FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("id"));
		}
	}
	
	private MoviesDAO moviesDAO;
	private Movie movie;
	
	
	@PostConstruct
	private void postConstruct() {
		Log4JUtils.getLogger().trace("Création du managed bean: MovieBean");
		moviesDAO = new MoviesDAO(Movie.class);
		movie = moviesDAO.findById(idParam);
	}
	
	@PreDestroy
	public void preDestroy() {
		Log4JUtils.getLogger().trace("Destruction du managed bean: MovieBean");
	}
	
	public Movie getMovie() {
		return movie;
	}
	public void setMovie(Movie movie) {
		this.movie = movie;
	}
	
	
}
