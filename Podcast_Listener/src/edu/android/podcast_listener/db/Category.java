package edu.android.podcast_listener.db;

import java.io.Serializable;

public class Category implements Serializable {

	private static final long serialVersionUID = -574950074394429838L;
	private long id;
	private String name;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
