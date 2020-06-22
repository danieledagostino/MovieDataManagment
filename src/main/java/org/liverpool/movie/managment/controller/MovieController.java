package org.liverpool.movie.managment.controller;

import java.util.List;

import org.liverpool.movie.managment.beanapi.MovieBeanApi;
import org.liverpool.movie.managment.component.Messages;
import org.liverpool.movie.managment.model.Movie;
import org.liverpool.movie.managment.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/movie")
@Api(value="Movies", tags="Manage the CRUD operations within the Movie table")
@Slf4j
public class MovieController {
    
    @Autowired
    MovieService movieService;
    
    @Autowired
    Messages messages;

    @ApiOperation(
		      value = "Find a Movie by its identifier", 
		      notes = "Returns data about the Movie",
		      response = MovieBeanApi.class, 
		      produces = "application/json")
    @ApiResponses(value =
	{   
		@ApiResponse(code = 200, message = "Movie found")
	})
    @GetMapping(value = "/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieBeanApi> findById(@PathVariable(name = "id") Integer id) throws Exception
    {
    	log.info("findById method requested");
    	
    	MovieBeanApi beanApi = movieService.getById(id);
    	
    	return new ResponseEntity<MovieBeanApi>(beanApi, HttpStatus.OK);
    }
    
    @ApiOperation(
		      value = "Insert a new Movie",
		      consumes = "application/json")
	@ApiResponses(value =
	{   
		@ApiResponse(code = 201, message = "Movie inserted"),
		@ApiResponse(code = 400, message = "Movie not inserted due to a malformed request")
	})
    @PutMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MovieBeanApi> newMovie(@RequestBody(required = true) MovieBeanApi movie) throws Exception
    {
    	log.info("newMovie method requested");
    	
    	MovieBeanApi inserted = null;
    	
    	try {
    		inserted = movieService.insert(movie);
	  	} catch (Exception e) {
			log.error("Movie not inserted", e);
			
			return new ResponseEntity<MovieBeanApi>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	
    	if (inserted != null) {
    		return new ResponseEntity<MovieBeanApi>(inserted, HttpStatus.CREATED);
    	} else {
    		return new ResponseEntity<MovieBeanApi>(HttpStatus.BAD_REQUEST);
    	}
    }
    
    @ApiOperation(
		      value = "Find a Movie which has title like those present in database", 
		      notes = "Returns a collection found of Movies",
		      response = MovieBeanApi.class, 
		      produces = "application/json")
    @GetMapping(value = "/searchByTitle", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<MovieBeanApi> findByTitle(@RequestBody String title) throws Exception	 
    {
    	log.info("findByTitle method requested");
    	
    	List<MovieBeanApi> list = movieService.searchByTitle(title);
    	
    	return list; 
    }
    
    @ApiOperation(
		      value = "Delete a Movie by its identifier")
    @ApiResponses(value =
	{   
		@ApiResponse(code = 200, message = "Movie deleted"),
		@ApiResponse(code = 400, message = "Movie not deleted due to a malformed request")
	})
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable(name = "id") Integer id) throws Exception
    {
    	log.info("deleteMovie method requested");
    	
    	try {
    		movieService.delete(id);
    		
    		return new ResponseEntity<String>(HttpStatus.OK); 
    	} catch (Exception e) {
    		log.error("Movie not deleted", e);
    		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
    }
    
    @ApiOperation(
		      value = "Update an existing Movie",
		      consumes = "application/json")
	@ApiResponses(value =
	{   
		@ApiResponse(code = 200, message = "Movie updated"),
		@ApiResponse(code = 400, message = "Movie not inserted due to a malformed request")
	})
	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateMovie(@RequestBody(required = true) MovieBeanApi movie) throws Exception
	{
	  	log.info("updateMovie method requested");
	  	
	  	boolean updated = false;
	  	
	  	try {
	  		updated = movieService.update(movie);
	  	} catch (Exception e) {
			log.error("Movie not updated", e);
			
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	  	
	  	if (updated) {
	  		return new ResponseEntity<String>(HttpStatus.OK);
	  	} else {
	  		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	  	}
	}
    
    @ApiOperation(
		      value = "Find a collection of Movies by its Direcotr's name", 
		      notes = "Returns a collection found of Movies",
		      response = MovieBeanApi.class,
		      consumes = "application/json",
		      produces = "application/json")
    @ApiResponses(value =
   	{   
   		@ApiResponse(code = 200, message = "Movies found")
   	})
	@GetMapping(value = "/searchMoviesByDirectorName", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MovieBeanApi>> searchMoviesByDirectorName(@RequestBody String name) throws Exception	 
	{
    	log.info("searchMoviesByDirector method requested");
  
    	List<MovieBeanApi> list = movieService.searchMoviesByDirector(name);
	
    	return new ResponseEntity<List<MovieBeanApi>>(list, HttpStatus.OK);
	}
}
