package com.example.soap.endpoint;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.example.soap.entity.Movie;
import com.example.soap.gs_ws.AddMovieRequest;
import com.example.soap.gs_ws.AddMovieResponse;
import com.example.soap.gs_ws.DeleteMovieRequest;
import com.example.soap.gs_ws.DeleteMovieResponse;
import com.example.soap.gs_ws.GetAllMoviesRequest;
import com.example.soap.gs_ws.GetAllMoviesResponse;
import com.example.soap.gs_ws.GetMovieByIdRequest;
import com.example.soap.gs_ws.GetMovieByIdResponse;
import com.example.soap.gs_ws.MovieType;
import com.example.soap.gs_ws.ServiceStatus;
import com.example.soap.gs_ws.UpdateMovieRequest;
import com.example.soap.gs_ws.UpdateMovieResponse;
import com.example.soap.service.MovieService;

@Endpoint
public class MovieEndpoint {

	public static final String NAMESPACE_URI = "http://www.example.soap.com/movies-ws";
	
	@Autowired
	public MovieService movieService;
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getMovieByIdRequest")
	@ResponsePayload
	public GetMovieByIdResponse getMovieById(@RequestPayload GetMovieByIdRequest request) {
		GetMovieByIdResponse response = new GetMovieByIdResponse();
		
		Movie movie = movieService.getOneById(request.getMovieId());
		MovieType movieType = new MovieType();
		
		BeanUtils.copyProperties(movie, movieType);
		
		response.setMovieType(movieType);
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllMoviesRequest")
	@ResponsePayload
	public GetAllMoviesResponse getAllMovies(@RequestPayload GetAllMoviesRequest request) {
		GetAllMoviesResponse response = new GetAllMoviesResponse();
		
		List<Movie> movieList = movieService.getAll();
		List<MovieType> movieTypeList = new ArrayList<MovieType>();
		
		for(Movie movie : movieList) {
			MovieType movieType = new MovieType();
			BeanUtils.copyProperties(movie, movieType);
			movieTypeList.add(movieType);
		}
		
		response.getMovieType().addAll(movieTypeList);
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "addMovieRequest")
	@ResponsePayload
	public AddMovieResponse addMovie(@RequestPayload AddMovieRequest request) {
		AddMovieResponse response = new AddMovieResponse();
		
		Movie movie = new Movie(request.getTitle(), request.getCategory());
		Movie savedMovie = movieService.add(movie);
		
		MovieType movieType = new MovieType();
		ServiceStatus serviceStatus = new ServiceStatus();
		
		if (savedMovie == null) {
			serviceStatus.setStatusCode("CONFLICT");
			serviceStatus.setMessage("Exception while adding movie");
		} else {
			BeanUtils.copyProperties(savedMovie, movieType);
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Movie added successfully");
		}
		
		response.setMovieType(movieType);
		response.setServiceStatus(serviceStatus);
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateMovieRequest")
	@ResponsePayload
	public UpdateMovieResponse updateMovie(@RequestPayload UpdateMovieRequest request) {
		UpdateMovieResponse response = new UpdateMovieResponse();
		
		Movie movie = movieService.getOneByTitle(request.getTitle());
		
		ServiceStatus serviceStatus = new ServiceStatus();
		
		if (movie == null) {
			serviceStatus.setStatusCode("NOT FOUND");
			serviceStatus.setMessage("Movie " + request.getTitle() + " not found");
		} else {
			movie.setTitle(request.getTitle());
			movie.setCategory(request.getCategory());
			
			boolean flag = movieService.update(movie);
			
			if (flag == false) {
				serviceStatus.setStatusCode("CONFLICT");
				serviceStatus.setMessage("Exception while updating movie " + request.getTitle());
			} else {
				serviceStatus.setStatusCode("SUCCESS");
				serviceStatus.setMessage("Movie updated successfully");
			}
		}
		
		response.setServiceStatus(serviceStatus);
		
		return response;
	}
	
	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteMovieRequest")
	@ResponsePayload
	public DeleteMovieResponse deleteMovie(@RequestPayload DeleteMovieRequest request) {
		DeleteMovieResponse response = new DeleteMovieResponse();
		
		ServiceStatus serviceStatus = new ServiceStatus();
		
		boolean flag = movieService.delete(request.getMovieId());
		
		if (flag == false) {
			serviceStatus.setStatusCode("FAIL");
			serviceStatus.setMessage("Exception while deleting movie " + request.getMovieId());
		} else {
			serviceStatus.setStatusCode("SUCCESS");
			serviceStatus.setMessage("Movie deleted successfully");
		}
		
		response.setServiceStatus(serviceStatus);
		
		return response;
	}

}
