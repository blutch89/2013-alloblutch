package ch.blutch.alloblutch.model.comparators;

import java.util.Comparator;

import ch.blutch.alloblutch.model.entity.Movie;

public class MovieBestComparator implements Comparator<Movie> {

	@Override
	public int compare(Movie o1, Movie o2) {
		return o2.getGlobalNote() - o1.getGlobalNote();
	}

}
