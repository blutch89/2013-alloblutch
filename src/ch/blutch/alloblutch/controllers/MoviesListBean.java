package ch.blutch.alloblutch.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import ch.blutch.alloblutch.model.dao.GenreDAO;
import ch.blutch.alloblutch.model.dao.MoviesDAO;
import ch.blutch.alloblutch.model.entity.Genre;
import ch.blutch.alloblutch.model.entity.Movie;
import ch.blutch.alloblutch.utils.Log4JUtils;
import ch.blutch.alloblutch.utils.ParamUtils;

@ManagedBean
@RequestScoped
public class MoviesListBean implements Serializable {

	private MoviesDAO moviesDAO;
	private GenreDAO genreDAO;
	private String filterParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("filter");
	private String genreParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("genre");

	
	@PostConstruct
	private void postConstruct() {
		Log4JUtils.getLogger().trace("Création du managed bean: AdminMovieBean");
		moviesDAO = new MoviesDAO(Movie.class);
		genreDAO = new GenreDAO(Genre.class);
	}
	
	@PreDestroy
	public void preDestroy() {
		Log4JUtils.getLogger().trace("Destruction du managed bean: AdminMovieBean");
	}
	
	public List<Movie> findMoviesFromUrl() {
		List<Movie> movies = new ArrayList<>();
		
		if (filterParam == null) {
			return moviesDAO.findAll();
		}
		
		switch (filterParam) {
			case "best":
				movies = moviesDAO.findBestMovies();
				break;
			case "last_movies_released":
				movies = moviesDAO.findLastMoviesReleased(ParamUtils.nbMoviesReleasedToDisplay);
				break;
			case "in_cinema":
				movies = moviesDAO.findMoviesInCinema();
				break;
			default:
				movies = moviesDAO.findAll();
				break;
		}
		
		return movies;
	}
	
	public List<Movie> findMoviesByGenreFromUrl() {
		List<Movie> movies = new ArrayList<>();

		if (genreParam == null) {
			return movies;
		}
		
		movies = moviesDAO.findMoviesByGenre(genreParam);
		
		return movies;
	}
	
	public List<Genre> findGenreByAlphabeticalOrder() {
		List<Genre> genres = genreDAO.findAllByAlphabeticalOrder();
		
		return genres;
	}
	
	public String getByGenreTitle() {
		String startText = "Les films par genre";
		
		if (genreParam != null) {
			startText += " : " + genreDAO.findById(Long.parseLong(genreParam)).getDescription();
		}
		
		return startText;
	}

	public String urlToString() {
		String toReturn;
		
		if (filterParam == null) {
			return "Les films";
		}
		
		switch (filterParam) {
			case "best":
				toReturn = "Les meilleurs films";
				break;
			case "last_movies_released":
				toReturn = "Les dernières sorties";
				break;
			case "in_cinema":
				toReturn = "Au cinéma";
				break;
			default:
				toReturn = "Les films";
				break;
		}
		
		return toReturn;
	}
	
}
