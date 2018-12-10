package com.example.practica1.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

@Entity
public class MovieModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String imdoId;
	private String title;
	private String url;
	private String description;
	private String year;
	private String movieCover;
	private String valuation;
	private String directors;
	private String actors;
	
//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(name="movie_director", joinColumns=@JoinColumn(name="idMovie"), inverseJoinColumns=@JoinColumn(name="idDirector"))
//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(name = "movie_director", joinColumns = @JoinColumn(name = "movie_id", referencedColumnName = "idMovie"), inverseJoinColumns = @JoinColumn(name = "director_id", referencedColumnName = "idDirector"))
//  private List<DirectorModel> directors;
	
//	@ManyToMany(cascade = CascadeType.ALL)
//	@JoinTable(name="movie_actor", joinColumns=@JoinColumn(name="idMovie"), inverseJoinColumns=@JoinColumn(name="idActor"))
//	private List<ActorModel> actors;
	
	
	
//	public MovieModel(String imdoId, String title, String url, String description, String year, List<DirectorModel> directors,
//			List<ActorModel> actors, String movieCover, String valuation) {
//		super();
//		this.imdoId = imdoId;
//		this.title = title;
//		this.url = url;
//		this.description = description;
//		this.year = year;
//		this.directors = directors;
//		this.actors = actors;
//		this.movieCover = movieCover;
//		this.valuation = valuation;
//	}

	
	
	public MovieModel() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MovieModel(String imdoId, String title, String url, String description, String year, String movieCover,
		String valuation, String directors, String actors) {
	this.imdoId = imdoId;
	this.title = title;
	this.url = url;
	this.description = description;
	this.year = year;
	this.movieCover = movieCover;
	this.valuation = valuation;
	this.directors = directors;
	this.actors = actors;
}

	public String getImdoId() {
		return imdoId;
	}

	public void setImdoId(String imdoId) {
		this.imdoId = imdoId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

//	public List<DirectorModel> getDirectors() {
//		return directors;
//	}
//
//	public void setDirectors(List<DirectorModel> directors) {
//		this.directors = directors;
//	}
//
//	public List<ActorModel> getActors() {
//		return actors;
//	}
//
//	public void setActors(List<ActorModel> actors) {
//		this.actors = actors;
//	}

	public String getMovieCover() {
		return movieCover;
	}

	public void setMovieCover(String movieCover) {
		this.movieCover = movieCover;
	}

	public String getValuation() {
		return valuation;
	}

	public void setValuation(String valuation) {
		this.valuation = valuation;
	}


	public String getDirectors() {
		return directors;
	}

	public void setDirectors(String directors) {
		this.directors = directors;
	}

	public String getActors() {
		return actors;
	}

	public void setActors(String actors) {
		this.actors = actors;
	}
	

	
}
