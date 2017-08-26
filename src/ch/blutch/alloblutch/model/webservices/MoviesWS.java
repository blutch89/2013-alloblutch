package ch.blutch.alloblutch.model.webservices;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import net.sf.json.JSONObject;
import ch.blutch.alloblutch.Main;
import ch.blutch.alloblutch.model.dao.GenreDAO;
import ch.blutch.alloblutch.model.dao.MoviesDAO;
import ch.blutch.alloblutch.model.dao.PersonsDAO;
import ch.blutch.alloblutch.model.entity.Genre;
import ch.blutch.alloblutch.model.entity.Movie;
import ch.blutch.alloblutch.model.entity.Person;
import ch.blutch.alloblutch.utils.JSONutils;
import ch.blutch.alloblutch.utils.ParamUtils;

@Path("/movies")
public class MoviesWS {

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
	
	private ResponseBuilder createImageResponse(Movie movie) {
		String imagePath = Main.class.getResource("/default/movies/" + movie.getPictureName()).getPath();
		File file = new File(imagePath);
		
		ResponseBuilder response = Response.ok((Object) file);
		ResponseBuilder rb = response.header("Content-Disposition", "attachment; filename=" + movie.getPictureName());
		
		return rb;
	}
	
	@GET
	@Path("/getPictureFromMovieId/{id}")
	@Produces("image/*")
	public Response getPictureFromMovieId(@PathParam("id") String id) {	
		Movie movie = moviesDAO.findById(Long.parseLong(id));		
		
		ResponseBuilder rb = createImageResponse(movie);
		
		return rb.build();		
	}
	
	@GET
	@Path("/getFromId/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getMovieFromId(@PathParam("id") String id) {
		Movie movie = moviesDAO.findById(Long.parseLong(id));		
		JSONObject movieJSON = JSONutils.convertMovieToJSON(movie);
		String txt = movieJSON.toString();
		
		ResponseBuilder rb = createResponseHeader();
		rb = rb.entity(txt);		
		
		return rb.build();
	}
	
	@GET
	@Path("/getLastReleased")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getLastMoviesReleased() {
		List<Movie> movies = moviesDAO.findLastMoviesReleased(ParamUtils.nbMoviesReleasedToDisplay);
		JSONObject moviesJSON = JSONutils.convertMoviesToJSON(movies);
		String txt = moviesJSON.toString();
		
		ResponseBuilder rb = createResponseHeader();
		rb = rb.entity(txt);		
		
		return rb.build();
	}
	
	@GET
	@Path("/getBestMovies")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getBestMovies() {
		List<Movie> movies = moviesDAO.findBestMovies();
		JSONObject moviesJSON = JSONutils.convertMoviesToJSON(movies);
		String txt = moviesJSON.toString();
		
		ResponseBuilder rb = createResponseHeader();
		rb = rb.entity(txt);		
		
		return rb.build();
	}
	
	@GET
	@Path("/getInCinema")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getMoviesInCinema() {
		List<Movie> movies = moviesDAO.findMoviesInCinema();
		JSONObject moviesJSON = JSONutils.convertMoviesToJSON(movies);
		String txt = moviesJSON.toString();
		
		ResponseBuilder rb = createResponseHeader();
		rb = rb.entity(txt);		
		
		return rb.build();
	}
	
	@GET
	@Path("/getFromGenre/{id}")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getMoviesFromGenre(@PathParam("id") String id) {
		List<Movie> movies = moviesDAO.findMoviesByGenre(id);		
		JSONObject moviesJSON = JSONutils.convertMoviesToJSON(movies);
		String txt = moviesJSON.toString();
		
		ResponseBuilder rb = createResponseHeader();
		rb = rb.entity(txt);		
		
		return rb.build();
	}
	
	@GET
	@Path("/getByAlphabeticalOrder")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getMoviesByAlphabeticalOrder() {
		List<Movie> movies = moviesDAO.findAllMoviesByAlphabeticalOrder();		
		JSONObject moviesJSON = JSONutils.convertMoviesToJSON(movies);
		String txt = moviesJSON.toString();
		
		ResponseBuilder rb = createResponseHeader();
		rb = rb.entity(txt);		
		
		return rb.build();
	}
	
}
