package ch.blutch.alloblutch.controllers.validators;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import ch.blutch.alloblutch.model.dao.MoviesDAO;
import ch.blutch.alloblutch.model.entity.Movie;

@ManagedBean
@RequestScoped
public class MovieTitleValidator implements Validator {

	@Override
	public void validate(FacesContext context, UIComponent component, Object value)	throws ValidatorException {
		String title = (String) value;
		
		// Si c'est depuis la page de modification de film que ce validator a été appelé, l'attribut id derait contenir l'id du film à modifier
		String idEditMovie = (String) component.getAttributes().get("idEditMovie");
		
		MoviesDAO moviesDAO = new MoviesDAO(Movie.class);
		
		// Si le film existe déjà
		if (moviesDAO.testIfMovieTitleAlreadyExist(title, idEditMovie != null ? Long.parseLong(idEditMovie) : 0) == true) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Ce film existe déjà", null));
		}
		
	}

}
