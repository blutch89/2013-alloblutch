package ch.blutch.alloblutch.model.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;


public enum LinkType {

	DIRECTOR(1),
	PRODUCER(2),
	ACTOR(3);
	
	private int id;
	
	LinkType(int id) {
		this.id = id;
	}
	
	public int toInt() {
		return this.id;
	}
	
	public static LinkType fromInt(int value) {
		switch (value) {
			case 1:
				return LinkType.DIRECTOR;
			case 2:
				return LinkType.PRODUCER;
			case 3:
				return LinkType.ACTOR;
			default:
				return null;
		}
	}
	
}
