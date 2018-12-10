package com.example.practica1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.practica1.models.MovieModel;

public interface MovieRepository extends CrudRepository<MovieModel,String>{
	
	MovieModel findById(int id);
	MovieModel findByImdoId(String id);
	MovieModel findByTitleLike(String name);
	List<MovieModel> findAll();
	MovieModel deleteById(int idMovie);
	MovieModel deleteByImdoId(String imdoId);
	MovieModel save(MovieModel movie);
	
	//@Query("SELECT mv FROM MOVIE_MODEL mv WHERE mv.TITLE LIKE CONCAT('%',:title,'%')")
	//@Query("SELECT movie FROM MOVIE_MODEL movie WHERE LOWER(movie.TITLE) LIKE LOWER(concat('%',:title, '%'))")
	//@Query("SELECT movie FROM MOVIE_MODEL movie WHERE LOWER(movie.TITLE) LIKE concat('%',:title, '%')")
    List<MovieModel> findByTitleContainingIgnoreCase(String title);
	//List<MovieModel> findByTitleLike(String title);
}
