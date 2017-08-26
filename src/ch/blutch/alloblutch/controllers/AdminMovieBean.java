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
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import org.primefaces.model.UploadedFile;

import ch.blutch.alloblutch.model.dao.GenreDAO;
import ch.blutch.alloblutch.model.dao.MoviesDAO;
import ch.blutch.alloblutch.model.dao.PersonsDAO;
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
public class AdminMovieBean implements Serializable {

	private MoviesDAO moviesDAO;
	private PersonsDAO personsDAO;
	private GenreDAO genreDAO;
	
	// Pour la partie ajout
	private Movie movie = new Movie();
	private List<Person> actors = new ArrayList<>();
	private Person director = null;
	private Person producer = null;
	private UploadedFile picture;
	
	// Pour la partie liste
	private List<Movie> filteredMovies;
	private Movie selectedMovie;
	
	// Pour la partie modification
	private String movieIdParam;
	
	@PostConstruct
	private void postConstruct() {
		Log4JUtils.getLogger().trace("Création du managed bean: AdminMovieBean");
		moviesDAO = new MoviesDAO(Movie.class);
		personsDAO = new PersonsDAO(Person.class);
		genreDAO = new GenreDAO(Genre.class);
	
		movieIdParam = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get("editid");
		
		// Si la page demandée est la page de modification de film, charger le film concerné
		if (movieIdParam != null) {
			movie = moviesDAO.findById(Long.parseLong(movieIdParam));
			actors = movie.getActors();
			director = movie.getDirector();
			producer = movie.getProducer();
		}
	}
	
	@PreDestroy
	public void preDestroy() {
		Log4JUtils.getLogger().trace("Destruction du managed bean: AdminMovieBean");
	}
	
	public List<Person> completePerson(String query) {
		List<Person> persons = personsDAO.findAllByAlphabeticalOrder();
		List<Person> suggestions = new ArrayList<Person>();  
        
        for(Person p : persons) {
        	if (p.nameToString().toLowerCase().contains(query)) {
        		suggestions.add(p);
        	}
        }  
        
        return suggestions; 
	}
	
	public List<Genre> completeGenre(String query) {
		List<Genre> genres = genreDAO.findAllByAlphabeticalOrder();
		List<Genre> suggestions = new ArrayList<Genre>();  
        
        for(Genre g : genres) {
        	if (g.getDescription().toLowerCase().contains(query)) {
        		suggestions.add(g);
        	}
        }  
        
        return suggestions;
	}
	
	public String addMovie() {
		// Ajoute le réalisateur
		if (director != null) {
			this.movie.getMoviePersonLinks().add(new MoviePersonLink(this.movie, this.director, LinkType.DIRECTOR));
		}
		
		// Ajoute le producteur
		if (producer != null) {
			this.movie.getMoviePersonLinks().add(new MoviePersonLink(this.movie, this.producer, LinkType.PRODUCER));
		}
		
		// Ajoute les acteurs
		if (actors != null) {
			if (actors.size() > 0) {
				for (Person p : actors) {
					this.movie.getMoviePersonLinks().add(new MoviePersonLink(this.movie, p, LinkType.ACTOR));
				}
			}
		}
		
		// Upload le fichier
		if (this.picture != null) {
			try {
				String pictureName = this.movie.getTitle();
				pictureName = UploadUtils.purifyPictureName(pictureName);
				pictureName += UploadUtils.getFileExtension(picture.getFileName());
				UploadUtils.uploadFile(picture, ParamUtils.moviesPicturesDirectory, pictureName);
				
				// Ajoute le nom de fichier image à l'entité movie
				this.movie.setPictureName(pictureName);	
			} catch (IOException e) {
				Log4JUtils.getLogger().error("Impossible d'uploader l'image sur le serveur");
				e.printStackTrace();
			}
		}
		
		moviesDAO.saveOrUpdate(movie);
		Log4JUtils.getLogger().info("AdminMovieBean: film ajouté");
		
		movie = new Movie();
		director = null;
		producer = null;
		actors = new ArrayList<>();
		
		return null;
	}
	
	public String editMovie() {		
		// Upload le fichier
		if (this.picture != null) {
			try {
				String pictureName = this.movie.getTitle();
				pictureName = UploadUtils.purifyPictureName(pictureName);
				pictureName += UploadUtils.getFileExtension(picture.getFileName());
				UploadUtils.uploadFile(picture, ParamUtils.moviesPicturesDirectory, pictureName);
				
				// Supprime l'ancienne image
				Path oldPicture = Paths.get(ParamUtils.moviesPicturesDirectory + this.movie.getPictureName());
				Files.deleteIfExists(oldPicture);
				
				// Ajoute le nom de fichier image à l'entité movie
				this.movie.setPictureName(pictureName);	
			} catch (IOException e) {
				Log4JUtils.getLogger().error("Impossible d'uploader l'image sur le serveur");
				e.printStackTrace();
			}
		}
		
		// Supprime toutes les relations de célébrité avec le film
		this.movie.getMoviePersonLinks().clear();
		
		// Ajoute le réalisateur
		if (director != null) {
			this.movie.getMoviePersonLinks().add(new MoviePersonLink(this.movie, this.director, LinkType.DIRECTOR));
		}
		
		// Ajoute le producteur
		if (producer != null) {
			this.movie.getMoviePersonLinks().add(new MoviePersonLink(this.movie, this.producer, LinkType.PRODUCER));
		}
		
		// Ajoute les acteurs
		if (actors != null) {
			if (actors.size() > 0) {
				for (Person p : actors) {
					this.movie.getMoviePersonLinks().add(new MoviePersonLink(this.movie, p, LinkType.ACTOR));
				}
			}
		}
		
		moviesDAO.saveOrUpdate(movie);
		Log4JUtils.getLogger().info("AdminMovieBean: film modifié");
		
		movie = new Movie();
		director = null;
		producer = null;
		actors = new ArrayList<>();
		
		return "/web-pages/admin-movies/admin-list-movies.xhtml";
	}
	
	public String deleteMovie() {
		moviesDAO.delete(this.selectedMovie);
		
		return null;
	}
	
	public List<Movie> getAllMoviesByAlphabeticalOrder() {
		return moviesDAO.findAllMoviesByAlphabeticalOrder();
	}
	
	public void gotoEditMoviePage() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/web-pages/admin-movies/admin-edit-movie.xhtml?editid=" + this.selectedMovie.getId());
		} catch (IOException e) {
			Log4JUtils.getLogger().warn("AdminMovieBean: impossible de rediriger l'utilisateur vers la page de modification de film");
		}
	}
	
	public void gotoAddMoviePage() {
		try {
			FacesContext facesContext = FacesContext.getCurrentInstance();
			facesContext.getExternalContext().redirect(facesContext.getExternalContext().getRequestContextPath() + "/web-pages/admin-movies/admin-add-movie.xhtml");
		} catch (IOException e) {
			Log4JUtils.getLogger().warn("AdminMovieBean: impossible de rediriger l'utilisateur vers la page d'ajout de film");
		}
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public List<Person> getActors() {
		return actors;
	}

	public void setActors(List<Person> actors) {
		this.actors = actors;
	}

	public UploadedFile getPicture() {
		return picture;
	}

	public void setPicture(UploadedFile picture) {
		this.picture = picture;
	}

	public Person getDirector() {
		return director;
	}

	public void setDirector(Person director) {
		this.director = director;
	}

	public Person getProducer() {
		return producer;
	}

	public void setProducer(Person producer) {
		this.producer = producer;
	}

	public Movie getSelectedMovie() {
		return selectedMovie;
	}

	public void setSelectedMovie(Movie selectedMovie) {
		this.selectedMovie = selectedMovie;
	}

	public List<Movie> getFilteredMovies() {
		return filteredMovies;
	}

	public void setFilteredMovies(List<Movie> filteredMovies) {
		this.filteredMovies = filteredMovies;
	}

	public String getMovieIdParam() {
		return movieIdParam;
	}

	public void setMovieIdParam(String movieIdParam) {
		this.movieIdParam = movieIdParam;
	}
	
}
