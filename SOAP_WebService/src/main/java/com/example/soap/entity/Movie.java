package com.example.soap.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="movies")
public class Movie {
	
	@Id
	@GeneratedValue
	private long movieId;
	private String title;
	private String category;
	
	public Movie() {
		
	}

	public Movie(String title, String category) {
		this.title = title;
		this.category = category;
	}

	public long getMovieId() {
		return movieId;
	}
	
	public void setMovieId(long movieId) {
		this.movieId = movieId;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		this.category = category;
	}

}
