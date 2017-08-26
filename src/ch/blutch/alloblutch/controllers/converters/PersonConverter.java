package ch.blutch.alloblutch.controllers.converters;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import ch.blutch.alloblutch.model.dao.PersonsDAO;
import ch.blutch.alloblutch.model.entity.LinkType;
import ch.blutch.alloblutch.model.entity.Person;
import ch.blutch.alloblutch.utils.WriterToFile;

@FacesConverter(value = "personConverter")
public class PersonConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext arg0, UIComponent arg1, String value)
			throws ConverterException {
		PersonsDAO personsDAO = new PersonsDAO(Person.class);
		List<Person> persons = personsDAO.findAll();
		
		for (Person p : persons) {
			if (value.equals(p.nameToString())) {
				return p;
			}
		}
		
		Person p = new Person();
		
		return p;
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object actor)
			throws ConverterException {

		if (actor == null) {
			return "";
		} else {
			return ((Person) actor).nameToString();
		}
	}

}
