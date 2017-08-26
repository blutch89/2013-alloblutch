package ch.blutch.alloblutch.controllers.converters;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import ch.blutch.alloblutch.model.dao.GenreDAO;
import ch.blutch.alloblutch.model.entity.Genre;
import ch.blutch.alloblutch.model.entity.Person;

@FacesConverter(value = "genreConverter")
public class GenreConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value)
			throws ConverterException {
		GenreDAO genreDAO = new GenreDAO(Genre.class);
		List<Genre> genres = genreDAO.findAll();
		
		for (Genre g : genres) {
			if (value.equals(g.getDescription())) {
				return g;
			}
		}
		
		Genre g = new Genre();
		return g;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object genre)
			throws ConverterException {

		if (genre == null) {
			return "";
		} else {
			return ((Genre) genre).getDescription();
		}
	}

}
