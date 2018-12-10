package com.example.practica1.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.practica1.models.MovieModel;
import com.example.practica1.repositories.MovieRepository;

@Component
public class MovieService {

	@Autowired
	private MovieRepository movieRepository;

	public List<MovieModel> findAll() {
		return (List<MovieModel>) movieRepository.findAll();
	};

	public MovieModel save(MovieModel user) {
		try {
			MovieModel newMovie = movieRepository.save(user);
			return newMovie;
		} catch (Exception e2) {
			return null;
		}
	};

	public MovieModel deleteByIdMovie(int idMovie) {
		try {
			MovieModel movie = movieRepository.deleteById(idMovie);
			return movie;
		} catch (Exception e2) {
			return null;
		}
	};
	
	public MovieModel deleteByImdoId(String imdoId) {
		try {
			MovieModel movie = movieRepository.deleteByImdoId(imdoId);
			return movie;
		} catch (Exception e2) {
			return null;
		}
	};
	
	public MovieModel findById(int idMovie) {
		try {
			MovieModel movie = movieRepository.findById(idMovie);
			return movie;
		} catch (Exception e2) {
			return null;
		}
	}

	public MovieModel findByImdoId(String imdoId) {
		try {
			MovieModel movie = movieRepository.findByImdoId(imdoId);
			return movie;
		} catch (Exception e2) {
			return null;
		}
	}

	
	public MovieModel findByTitle(String title) {
		try {
			MovieModel movie = movieRepository.findByTitleLike(title);
			return movie;
		} catch (Exception e2) {
			return null;
		}
	}
	
	public List<MovieModel> findAllTitle(String title){
		try {
			List<MovieModel> movies = movieRepository.findByTitleContainingIgnoreCase(title.toLowerCase());
			return movies;
		} catch (Exception e2) {
			return null;
		}
	}

}
