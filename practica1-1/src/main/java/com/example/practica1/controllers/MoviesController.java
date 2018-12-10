package com.example.practica1.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.practica1.models.ExceptionModel;
import com.example.practica1.models.MovieModel;
import com.example.practica1.services.MovieService;
import com.fasterxml.jackson.databind.util.JSONPObject;

@RestController
public class MoviesController {

private static final String NOT_EXITS = "La película no existe.";

private static final String PREFIX_URL_REQUEST_TITLE_OMDB = "http://www.omdbapi.com/?s=";
private static final String SUFIX_URL_REQUEST_TITLE_OMDB = "&page=1&apikey=f02996b0";

private static final String PREFIX_URL_REQUEST_IMDBID_OMDB = "http://www.omdbapi.com/?i=";
private static final String SUFIX_URL_REQUEST_IMDBID_OMDB = "&apikey=f02996b0";

private static final String URL_TRAILER = "https://www.imdb.com/title/";


	@Autowired
	private MovieService movieService;
	
	@Autowired
    private RestTemplate restTemplate = new RestTemplate();
	
	@Autowired
	public MoviesController(MovieService movieService) {
		this.movieService = movieService;
	}
	
	/**
	 * Método que se comunica con la API de MyAPIFilms
	 * para obtener los datos de la película.
	 * @return
	 */
	private Map<String,?> getMovieOMDB(String title) {
		
		if(isTitle(title)) {
			Map<String,?> result =  (Map<String, ?>) restTemplate.getForObject(PREFIX_URL_REQUEST_TITLE_OMDB +title+ SUFIX_URL_REQUEST_TITLE_OMDB,Object.class);
			String response = (String)result.get("Response");
			if(response.equals("True")) {
				return result;
			}
		}
		return null;
	}
	
	private Map<String,?> getMovieByIDOMDB(String id) {
		
		Map<String,?> result =  (Map<String, ?>) restTemplate.getForObject(PREFIX_URL_REQUEST_IMDBID_OMDB +id+ SUFIX_URL_REQUEST_IMDBID_OMDB,Object.class);
		return result;
	}
	
	private boolean isTitle(String title) {
		
		if(title == null || title == "") {
			return false;
		}else {
			return true;
		}
		
	}
	
	
	
	//
	
	
	/////////////////////
	
	/**
	 * Devuelve la lista de todos los usuarios creados
	 * @return
	 */
	@GetMapping("/listMovies")
	@ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<MovieModel>> listAllProducts(){
		List<MovieModel>  users = new ArrayList<>();
		users = movieService.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity<List<MovieModel>>(users,HttpStatus.NO_CONTENT);
		}else {
        	return new ResponseEntity<List<MovieModel>>(users, HttpStatus.OK);
        }
    }
	
	
	
	
	private void updateMovieWithIMDBID(MovieModel movieModel) {
		
		String imdbID = movieModel.getImdoId();
		Map<String,?> movieAPI = getMovieByIDOMDB(imdbID);
		movieModel.setDescription((String)movieAPI.get("Plot"));
		movieModel.setUrl(URL_TRAILER + imdbID);
		movieModel.setMovieCover((String)movieAPI.get("Poster"));
		movieModel.setDirectors((String)movieAPI.get("Director"));
		
		List<Map<String,String>> listRatings = (List<Map<String,String>>)movieAPI.get("Ratings");
		if(listRatings.size() == 0) {
			movieModel.setValuation("-");
		}else {
			String value = listRatings.get(0).get("Value");
			movieModel.setValuation(value);
		}
		movieModel.setActors((String)movieAPI.get("Actors"));
	}
	
	/**
	 * Devuelve la lista de todos los usuarios creados
	 * @return
	 */
	@GetMapping("/searchMovie")
	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<Map<String,List<MovieModel>>> searchMovie(@RequestParam(value="title",required=false) String title) throws ExceptionModel {
	    
		Map<String,List<MovieModel>> responseServer = new HashMap<String,List<MovieModel>>();
		List<MovieModel> myMovie = movieService.findAllTitle(title);
		if(myMovie == null || myMovie.size() == 0) {
			//consultar API
			Map<String,?> resultOMDB = getMovieOMDB(title);
			if(resultOMDB == null) {
				throw new ExceptionModel(2,NOT_EXITS);
			}else {
	        	List<Map<String, ?>> moviesAPI = new ArrayList<>();
	        	moviesAPI = (List<Map<String, ?>>) resultOMDB.get("Search");
	        	for (Map<String, ?> movie : moviesAPI) {
	    			MovieModel movieModel = new MovieModel();
	    			movieModel.setTitle((String) movie.get("Title"));
	    			movieModel.setYear((String)movie.get("Year"));
	    			movieModel.setImdoId((String)movie.get("imdbID"));
	    			updateMovieWithIMDBID(movieModel);
	    			movieModel = movieService.save(new MovieModel(movieModel.getImdoId(),movieModel.getTitle(),movieModel.getUrl(),movieModel.getDescription(),movieModel.getYear(),movieModel.getMovieCover(),movieModel.getValuation(),movieModel.getDirectors(),movieModel.getActors()));
	    			myMovie.add(movieModel);
				}
	        	responseServer.put("resultado", myMovie);
	        	return new ResponseEntity<Map<String,List<MovieModel>>>(responseServer, HttpStatus.OK);
	        }
			
		}else {
			responseServer.put("resultado", myMovie);
			return new ResponseEntity<Map<String,List<MovieModel>>>(responseServer, HttpStatus.OK);
		}
		
    }
	
	/**
	 * Inserta un nuevo usuario a la base de datos
	 * @param movie
	 * @param ucBuilder
	 * @return
	 * @throws ExceptionModel 
	 */
	@PostMapping("/addMovie")
	@ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<String> addUser(@RequestBody MovieModel movie, UriComponentsBuilder ucBuilder) throws ExceptionModel {
       
		if(movieService.findByTitle(movie.getTitle()) != null) {
            throw new ExceptionModel(1,"La película ya existe.");
        }else {
        	movieService.save(movie);
            HttpHeaders headers = new HttpHeaders();
            headers.setLocation(ucBuilder.path("/resumeMovie/{imdoId}").buildAndExpand(movie.getImdoId()).toUri());
            return new ResponseEntity<String>(headers, HttpStatus.CREATED);
        }
    	
    }
	
	/**
	 * Elimina un usuario de la base de datos
	 * @param id
	 * @return
	 * @throws ExceptionModel 
	 */
	@GetMapping("/removeMovie/{imdoId}")
    public ResponseEntity<MovieModel> deleteProduct(@PathVariable("imdoId") String imdoId) throws ExceptionModel {
		
		MovieModel product = movieService.deleteByImdoId(imdoId);
		if (product == null) {
			throw new ExceptionModel(2,NOT_EXITS);
        }else {
        	return new ResponseEntity<MovieModel>(HttpStatus.NO_CONTENT);
        }
    }
	
	/**
	 * Muestra un usuario
	 * @param id
	 * @return
	 * @throws ExceptionModel 
	 */
	@GetMapping("/showMovie/{imdoId}")
    public ResponseEntity<MovieModel> getProduct(@PathVariable("idMovie") String idMovie) throws ExceptionModel {
		
		MovieModel movie = movieService.findByImdoId(idMovie);
        if (movie == null) {
        	throw new ExceptionModel(2,NOT_EXITS);
        }else {
        	return new ResponseEntity<MovieModel>(movie, HttpStatus.OK);
        }
    }

	/**
	 * Actualiza un producto existente
	 * @param product
	 * @param ucBuilder
	 * @return
	 * @throws ExceptionModel 
	 */
	@PutMapping("/modifyMovie")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<String> modifyProducts(@RequestBody MovieModel movie, UriComponentsBuilder ucBuilder) throws ExceptionModel {
		return null;
//		if (movieService.findByTitle(movie.getTitle()) == null) {
//			throw new ExceptionModel(2,NOT_EXITS);
//		}
//        else{
//
//    		String movieID = movie.getTitle();
//        	movieService.deleteByTit(movieID);
//        	movieService.save(movie);
//        	HttpHeaders headers = new HttpHeaders();
//    		headers.setLocation(ucBuilder.path("/resumeMovie/{imdoId}").buildAndExpand(movieID).toUri());
//    		return new ResponseEntity<String>(headers, HttpStatus.CREATED);
//    	
//		}
	}
}
