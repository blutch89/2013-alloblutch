package ch.blutch.alloblutch.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import ch.blutch.alloblutch.utils.DateUtils;
import ch.blutch.alloblutch.utils.ParamUtils;

@Entity
@Table(name="persons")
public class Person implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Temporal(TemporalType.DATE)
	@Column(name="birthday")
	private Date birthday;
	
	@Column(name="picture_name")
	private String pictureName;
	
	@OneToMany
	@JoinColumn(name="person_id")
	@Cascade(CascadeType.ALL)
	private Set<MoviePersonLink> moviePersonLinks = new HashSet<>();
	
	
	public Person() {
		
	}
	
	// Récupère ses films joués en tant que réalisateur
	public List<Movie> getMoviesAsDirector() {
		List<Movie> movies = new ArrayList<>();
		for (MoviePersonLink mpl : this.moviePersonLinks) {
			if (mpl.getLinkType().equals(LinkType.DIRECTOR)) {
				movies.add(mpl.getMovie());
			}
		}
		
		return movies;
	}
	
	// Récupère ses films joués en tant que producteur
	public List<Movie> getMoviesAsProducer() {
		List<Movie> movies = new ArrayList<>();
		for (MoviePersonLink mpl : this.moviePersonLinks) {
			if (mpl.getLinkType().equals(LinkType.PRODUCER)) {
				movies.add(mpl.getMovie());
			}
		}
		
		return movies;
	}
	
	// Récupère ses films joués en tant qu'acteur
	public List<Movie> getMoviesAsActor() {
		List<Movie> movies = new ArrayList<>();
		for (MoviePersonLink mpl : this.moviePersonLinks) {
			if (mpl.getLinkType().equals(LinkType.ACTOR)) {
				movies.add(mpl.getMovie());
			}
		}
		
		return movies;
	}
	
	// Renvoie ses métiers (acteurs, producteur etc...)
	public String getProfessionsToString() {
		String toReturn = "";
		
		toReturn += isDirector() == true ? "Réalisateur, " : "";
		toReturn += isProducer() == true ? "Producteur, " : "";
		toReturn += isActor() == true ? "Acteur, " : "";
		
		if (toReturn.length() > 0) {
			toReturn = toReturn.substring(0, toReturn.length() - 2);
		}
		
		return toReturn;
	}
	
	// Renvoie true si cette personne est du type sélectionné
	public boolean isType(LinkType linkType) {
		for (MoviePersonLink mpl : this.moviePersonLinks) {
			if (mpl.getLinkType().equals(linkType)) {
				return true;
			}
		}
		
		return false;
	}
	
	// Renvoie true si cette personne est un réalisateur
	public boolean isDirector() {
		for (MoviePersonLink mpl : this.moviePersonLinks) {
			if (mpl.getLinkType().equals(LinkType.DIRECTOR)) {
				return true;
			}
		}
		
		return false;
	}
	
	// Renvoie true si cette personne est un producteur
	public boolean isProducer() {
		for (MoviePersonLink mpl : this.moviePersonLinks) {
			if (mpl.getLinkType().equals(LinkType.PRODUCER)) {
				return true;
			}
		}
		
		return false;
	}
	
	// Renvoie true si cette personne est un acteur
	public boolean isActor() {
		for (MoviePersonLink mpl : this.moviePersonLinks) {
			if (mpl.getLinkType().equals(LinkType.ACTOR)) {
				return true;
			}
		}
		
		return false;
	}
	
	public String birthdayToString() {
		return this.birthday == null ? " - " : DateUtils.dateFormat.format(this.birthday);
	}
	
	public String nameToString() {
		return this.firstName + " " + this.lastName;
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getPictureName() {
		return pictureName == null ? ParamUtils.defaultPicture : pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}


	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Set<MoviePersonLink> getMoviePersonLinks() {
		return moviePersonLinks;
	}

	public void setMoviePersonLinks(Set<MoviePersonLink> moviePersonLinks) {
		this.moviePersonLinks = moviePersonLinks;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((pictureName == null) ? 0 : pictureName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (pictureName == null) {
			if (other.pictureName != null)
				return false;
		} else if (!pictureName.equals(other.pictureName))
			return false;
		return true;
	}
	
}
