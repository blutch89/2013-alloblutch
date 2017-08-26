package ch.blutch.alloblutch.model.comparators;

import java.util.Comparator;

import ch.blutch.alloblutch.model.entity.Person;

public class PersonAlphabeticalComparator implements Comparator<Person> {

	@Override
	public int compare(Person o1, Person o2) {
		return o1.nameToString().compareTo(o2.nameToString());
	}

}
