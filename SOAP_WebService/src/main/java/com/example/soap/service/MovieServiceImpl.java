package com.example.soap.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.soap.entity.Movie;
import com.example.soap.repository.MovieRepository;

@Service
public class MovieServiceImpl implements MovieService {
	
	@Autowired
	public MovieRepository movieRepository;
	
	@Override
	@Transactional
	public Movie getOneById(long id) {
		return movieRepository.findById(id).get();
	}

	@Override
	@Transactional
	public Movie getOneByTitle(String title) {
		return movieRepository.findByTitle(title);
	}

	@Override
	@Transactional
	public List<Movie> getAll() {
		List<Movie> list = new ArrayList<>();
		movieRepository.findAll().forEach(movie -> list.add(movie));
		return list;
	}

	@Override
	@Transactional
	public Movie add(Movie movie) {
		try {
			return movieRepository.save(movie);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	@Transactional
	public boolean update(Movie movie) {
		try {
			movieRepository.save(movie);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	@Transactional
	public boolean delete(long id) {
		try {
			movieRepository.deleteById(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
