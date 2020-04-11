package com.example.soap.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.soap.entity.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie,Long> {
	
	public Movie findByTitle(String title);
	
}
