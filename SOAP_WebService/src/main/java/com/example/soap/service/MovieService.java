package com.example.soap.service;

import java.util.List;

import com.example.soap.entity.Movie;

public interface MovieService {
	
	public Movie getOneById(long id);
	public Movie getOneByTitle(String title);
	public List<Movie> getAll();
	public Movie add(Movie movie);
	public boolean update(Movie movie);
	public boolean delete(long id);

}
