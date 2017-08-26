package ch.blutch.alloblutch.utils;

import java.util.List;





import net.sf.json.JSONObject;
import ch.blutch.alloblutch.model.entity.Genre;
import ch.blutch.alloblutch.model.entity.Movie;
import ch.blutch.alloblutch.model.entity.Person;

public class JSONutils {

	public static JSONObject convertGenresToJSON(List<Genre> genres) {
		JSONObject genresJSON = new JSONObject();
		
		for (Genre genre : genres) {
			JSONObject genreJSON = new JSONObject();
			
			genreJSON.put("id", genre.getId());
			genreJSON.put("description", genre.getDescription());
			
			// Ajoute la movie dans le genre associé			
			genresJSON.put(genre.getId(), genreJSON);
		}
		
		return genresJSON;
	}
	
	public static JSONObject convertMoviesToJSON(List<Movie> movies) {
		JSONObject moviesJSON = new JSONObject();
		
		for (Movie movie : movies) {
			JSONObject movieJSON = new JSONObject();
			
			movieJSON.put("id", movie.getId());
			movieJSON.put("title", movie.getTitle());
			movieJSON.put("genre", movie.getGenre().getDescription());
			movieJSON.put("cinemaDate", movie.cinemaDateToString());
			movieJSON.put("publicDate", movie.publicDateToString());
			movieJSON.put("mediaNote", movie.getMediaNote());
			movieJSON.put("publicNote", movie.getPublicNote());
			
			// Ajoute la movie dans le genre associé			
			moviesJSON.put(movie.getId(), movieJSON);
		}
		
		return moviesJSON;
	}
	
	public static JSONObject convertMovieToJSON(Movie movie) {
		JSONObject movieJSON = new JSONObject();
		// pictureName + persons
		movieJSON.put("id", movie.getId());
		movieJSON.put("title", movie.getTitle());
		movieJSON.put("synopsis", movie.getSynopsis());
		movieJSON.put("genre", movie.getGenre().getDescription());
		movieJSON.put("cinemaDate", movie.cinemaDateToString());
		movieJSON.put("publicDate", movie.publicDateToString());
		movieJSON.put("mediaNote", movie.getMediaNote());
		movieJSON.put("publicNote", movie.getPublicNote());
		
		// Personnes
		JSONObject directorJSON = new JSONObject();
		directorJSON.put("id", movie.getDirector().getId());
		directorJSON.put("name", movie.getDirector().nameToString());
		
		JSONObject producerJSON = new JSONObject();
		producerJSON.put("id", movie.getProducer().getId());
		producerJSON.put("name", movie.getProducer().nameToString());
		
		movieJSON.put("director", directorJSON);
		movieJSON.put("producer", producerJSON);
		
		for (Person actor : movie.getActors()) {
			JSONObject actorJSON = new JSONObject();
			actorJSON.put("id", actor.getId());
			actorJSON.put("name", actor.nameToString());
			
			movieJSON.accumulate("actors", actorJSON);
		}
		
		return movieJSON;
	}
	
}
