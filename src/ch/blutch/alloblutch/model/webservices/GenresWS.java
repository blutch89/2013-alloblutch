package ch.blutch.alloblutch.model.webservices;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import net.sf.json.JSONObject;
import ch.blutch.alloblutch.model.dao.GenreDAO;
import ch.blutch.alloblutch.model.dao.MoviesDAO;
import ch.blutch.alloblutch.model.dao.PersonsDAO;
import ch.blutch.alloblutch.model.entity.Genre;
import ch.blutch.alloblutch.model.entity.Movie;
import ch.blutch.alloblutch.model.entity.Person;
import ch.blutch.alloblutch.utils.JSONutils;

@Path("/genres")
public class GenresWS {

	private MoviesDAO moviesDAO;
	private GenreDAO genreDAO;
	private PersonsDAO personsDAO;
	
	@PostConstruct
	private void init() {
		this.moviesDAO = new MoviesDAO(Movie.class);
		this.genreDAO = new GenreDAO(Genre.class);
		this.personsDAO = new PersonsDAO(Person.class);
	}
	
	private ResponseBuilder createResponseHeader() {
		ResponseBuilder rb = Response.status(200);
		rb = rb.header("Access-Control-Allow-Origin", "*");
		rb = rb.header("Access-Control-Allow-Methods", "GET, POST, OPTIONS");
		
		return rb;
	}
	
	@GET
	@Path("/getByAlphabeticalOrder")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getMoviesOrderByGenre() {
		List<Genre> genres = genreDAO.findAllByAlphabeticalOrder();		
		JSONObject genresJSON = JSONutils.convertGenresToJSON(genres);
		String txt = genresJSON.toString();
		
		ResponseBuilder rb = createResponseHeader();
		rb = rb.entity(txt);		
		
		return rb.build();
	}
	
}
