package ch.blutch.alloblutch.model.dao;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

import org.hibernate.Query;

import ch.blutch.alloblutch.model.comparators.MovieBestComparator;
import ch.blutch.alloblutch.model.entity.Movie;
import ch.blutch.alloblutch.utils.Log4JUtils;
import ch.blutch.alloblutch.utils.ParamUtils;

public class MoviesDAO extends HibernateDAO<Movie> {

	public MoviesDAO(Class persistendClass) {
		super(persistendClass);
		Log4JUtils.getLogger().trace("Hibernate: cr�ation d'un MoviesDAO");
	}
	
	public List<Movie> findBestMovies() {
		Log4JUtils.getLogger().trace("Hibernate: acc�s aux meilleurs films");
		
		Query q = session.createQuery("SELECT m FROM Movie m ORDER BY m.publicNote + m.mediaNote DESC");
		List<Movie> movies = (List<Movie>) q.list();
		
		if (movies.size() > 10) {
			movies = movies.subList(0, ParamUtils.nbBestMoviesToDisplay);
		}
		
		return movies;
	}
	
	// Retourne tous les films par ordre alphab�tique
	public List<Movie> findAllMoviesByAlphabeticalOrder() {
		Log4JUtils.getLogger().trace("Hibernate: acc�s � toutes les vid�os par ordre alphab�tique");
		
		Query q = session.createQuery("SELECT m FROM Movie m ORDER BY m.title ASC");
		List<Movie> movies = (List<Movie>) q.list();
		
		return movies;
	}
	
	// Retourne les derniers films sortis
	public List<Movie> findLastMoviesReleased(int nbMovie) {
		Log4JUtils.getLogger().trace("Hibernate: acc�s aux derni�res vid�os sortie en publique");
		
		Query q = session.createQuery("SELECT m FROM Movie m WHERE m.publicDate != '' ORDER BY m.publicDate DESC");
		q.setMaxResults(nbMovie);
		List<Movie> movies = (List<Movie>) q.list();
		
		return movies;
	}
	
	// Retourne les films actuellement au cin�ma
	public List<Movie> findMoviesInCinema() {
		Log4JUtils.getLogger().trace("Hibernate: acc�s aux films au cinema");
		
		Query q = session.createQuery("SELECT m FROM Movie m WHERE m.publicDate IS NULL ORDER BY m.cinemaDate DESC");
		List<Movie> movies = (List<Movie>) q.list();
		
		return movies;
	}
	
	public List<Movie> findMoviesByGenre(String id) {
		Log4JUtils.getLogger().trace("Hibernate: acc�s aux filmsd'un genre");
		
		Query q = session.createQuery("SELECT m FROM Movie m WHERE m.genre.id = :genreId");
		q.setString("genreId", id);
		List<Movie> movies = (List<Movie>) q.list();
		
		return movies;
	}
	
	// Test si le titre de la vid�o existe d�j�
	public boolean testIfMovieTitleAlreadyExist(String title, Long idEditMovie) {
		Log4JUtils.getLogger().trace("Hibernate: test si le film existe d�j�");
		
		Query query = session.createQuery("SELECT m FROM Movie m WHERE m.title = :title");
		query.setString("title", title);
		Movie movie = (Movie) query.uniqueResult();
		
		if (movie != null) {
			// Si le film trouv� est celui qui se fait �diter
			if (movie.getId() == idEditMovie) {
				return false;
			}
			
			return true;
		}
		
		return false;
	}

	
}
