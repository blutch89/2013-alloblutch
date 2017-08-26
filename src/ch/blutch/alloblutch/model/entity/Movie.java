package ch.blutch.alloblutch.model.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import ch.blutch.alloblutch.utils.DateUtils;
import ch.blutch.alloblutch.utils.ParamUtils;

@Entity
@Table(name="movies")
public class Movie implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private long id;
	
	@Column(name="title")
	private String title = "";
	
	@Column(name="synopsis")
	private String synopsis = "";
	
	@ManyToOne
	@Cascade(CascadeType.SAVE_UPDATE)
	private Genre genre;
	
	@OneToMany(cascade=javax.persistence.CascadeType.ALL)
	@JoinColumn(name="movie_id")
	@Cascade(CascadeType.ALL)
	private Set<MoviePersonLink> moviePersonLinks = new HashSet<>();
	
	@Column(name="picture_name")
	private String pictureName;
	
	@Column(name="cinema_date")
	private Date cinemaDate;
	
	@Column(name="public_date")
	private Date publicDate;
	
	@Column(name="public_note")
	private int publicNote;
	
	@Column(name="media_note")
	private int mediaNote;
	
	public Movie() {
		
	}
	
	// Calcul la note du film
	public int getGlobalNote() {
		return this.mediaNote + this.publicNote;
	}
	
	// Affiche le synopsis avec un maximum de caractère
	public String shortSynopsis() {
		int nbMaxChar = 450;
		String syno = "";
		
		if (this.synopsis.length() > nbMaxChar) {
			syno = this.synopsis.substring(0, nbMaxChar);
			syno += " ...";
		}
		
		return syno;
	}
	
	// Retourne tous les acteurs
	public List<Person> getActors() {
		List<Person> actors = new ArrayList<>();
		for (MoviePersonLink mpl : this.moviePersonLinks) {
			if (mpl.getLinkType().equals(LinkType.ACTOR)) {
				actors.add(mpl.getPerson());
			}
		}
		
		return actors;
	}
	
	// Retourne les 5 premiers acteurs trouvés en texte
	public String getFirstActorsToString() {
		if (this.getActors().size() > 0) {
			int i = 0;
			String toString = "";
			
			for (MoviePersonLink mpl : this.moviePersonLinks) {
				if (mpl.getLinkType().equals(LinkType.ACTOR)) {
					if (i < 5) {
						toString += mpl.getPerson().getFirstName() + " " + mpl.getPerson().getLastName() + ", ";
					} else {
						
						
						return toString;
					}
					
					i++;
				}
			}
			
			toString = toString.substring(0, toString.length() -2);	// Enlève la dernière virgule et l'espace
			
			return toString;
		} else {
			return "-";
		}
	}
	
	// Obtient le réalisateur
	public Person getDirector() {
		for (MoviePersonLink mpl : this.moviePersonLinks) {
			if (mpl.getLinkType().equals(LinkType.DIRECTOR)) {
				return mpl.getPerson();
			}
		}
		
		return null;
	}
	
	// Obtient le producteur
	public Person getProducer() {
		for (MoviePersonLink mpl : this.moviePersonLinks) {
			if (mpl.getLinkType().equals(LinkType.PRODUCER)) {
				return mpl.getPerson();
			}
		}
		
		return null;
	}
	
	// Affiche la date de sortie publique en texte
	public String publicDateToString() {
		return this.publicDate == null ? "-" : DateUtils.dateFormat.format(this.publicDate);
	}
	
	// Affiche la date de sortie au cinéma en texte
	public String cinemaDateToString() {
		return this.cinemaDate == null ? "-" : DateUtils.dateFormat.format(this.cinemaDate);
	}
	
	// Affiche l'année de sortie au cinéma en texte
	public String cinemaDateToYearString() {
		return this.cinemaDate == null ? "-" : DateUtils.yearFormat.format(this.cinemaDate);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public Genre getGenre() {
		return genre;
	}

	public void setGenre(Genre genre) {
		this.genre = genre;
	}

	public Set<MoviePersonLink> getMoviePersonLinks() {
		return moviePersonLinks;
	}

	public void setMoviePersonLinks(Set<MoviePersonLink> moviePersonLinks) {
		this.moviePersonLinks = moviePersonLinks;
	}

	public String getPictureName() {
		return pictureName == null ? ParamUtils.defaultPicture : pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	public Date getCinemaDate() {
		return cinemaDate;
	}

	public void setCinemaDate(Date cinemaDate) {
		this.cinemaDate = cinemaDate;
	}

	public Date getPublicDate() {
		return publicDate;
	}

	public void setPublicDate(Date publicDate) {
		this.publicDate = publicDate;
	}

	public int getPublicNote() {
		return publicNote;
	}

	public void setPublicNote(int publicNote) {
		this.publicNote = publicNote;
	}

	public int getMediaNote() {
		return mediaNote;
	}

	public void setMediaNote(int mediaNote) {
		this.mediaNote = mediaNote;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cinemaDate == null) ? 0 : cinemaDate.hashCode());
		result = prime * result + ((genre == null) ? 0 : genre.hashCode());
		result = prime * result + mediaNote;
		result = prime * result
				+ ((publicDate == null) ? 0 : publicDate.hashCode());
		result = prime * result + publicNote;
		result = prime * result
				+ ((synopsis == null) ? 0 : synopsis.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Movie other = (Movie) obj;
		if (cinemaDate == null) {
			if (other.cinemaDate != null)
				return false;
		} else if (!cinemaDate.equals(other.cinemaDate))
			return false;
		if (genre == null) {
			if (other.genre != null)
				return false;
		} else if (!genre.equals(other.genre))
			return false;
		if (mediaNote != other.mediaNote)
			return false;
		if (publicDate == null) {
			if (other.publicDate != null)
				return false;
		} else if (!publicDate.equals(other.publicDate))
			return false;
		if (publicNote != other.publicNote)
			return false;
		if (synopsis == null) {
			if (other.synopsis != null)
				return false;
		} else if (!synopsis.equals(other.synopsis))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
}
