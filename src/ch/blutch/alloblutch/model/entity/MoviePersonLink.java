package ch.blutch.alloblutch.model.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

@Entity
@Table(name="movie_person_links")
public class MoviePersonLink implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	private Movie movie;
	
	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	private Person person;
	
	@Column(name="link_type")
	private int linkType;
	
	public MoviePersonLink() {
		
	}
	
	public MoviePersonLink(Movie movie, Person person, LinkType linkType) {
		super();
		this.movie = movie;
		this.person = person;
		this.linkType = linkType.toInt();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public LinkType getLinkType() {
		return LinkType.fromInt(this.linkType);
	}

	public void setLinkType(LinkType linkType) {
		this.linkType = linkType.toInt();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + linkType;
		result = prime * result + ((movie == null) ? 0 : movie.hashCode());
		result = prime * result + ((person == null) ? 0 : person.hashCode());
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
		MoviePersonLink other = (MoviePersonLink) obj;
		if (linkType != other.linkType)
			return false;
		if (movie == null) {
			if (other.movie != null)
				return false;
		} else if (!movie.equals(other.movie))
			return false;
		if (person == null) {
			if (other.person != null)
				return false;
		} else if (!person.equals(other.person))
			return false;
		return true;
	}
	
	
}
