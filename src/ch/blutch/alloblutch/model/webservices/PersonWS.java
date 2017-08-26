package ch.blutch.alloblutch.model.webservices;

import java.io.File;

import javax.annotation.PostConstruct;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import ch.blutch.alloblutch.Main;
import ch.blutch.alloblutch.model.dao.GenreDAO;
import ch.blutch.alloblutch.model.dao.MoviesDAO;
import ch.blutch.alloblutch.model.dao.PersonsDAO;
import ch.blutch.alloblutch.model.entity.Genre;
import ch.blutch.alloblutch.model.entity.Movie;
import ch.blutch.alloblutch.model.entity.Person;

@Path("/persons")
public class PersonWS {

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
	
	private ResponseBuilder createImageResponse(Person person) {
		String imagePath = Main.class.getResource("/default/persons/" + person.getPictureName()).getPath();
		File file = new File(imagePath);
		
		ResponseBuilder response = Response.ok((Object) file);
		ResponseBuilder rb = response.header("Content-Disposition", "attachment; filename=" + person.getPictureName());
		
		return rb;
	}
	
	@GET
	@Path("/getPictureFromPersonId/{id}")
	@Produces("image/*")
	public Response getPictureFromMovieId(@PathParam("id") String id) {	
		Person person = personsDAO.findById(Long.parseLong(id));		
		
		ResponseBuilder rb = createImageResponse(person);
		
		return rb.build();		
	}
	
}
