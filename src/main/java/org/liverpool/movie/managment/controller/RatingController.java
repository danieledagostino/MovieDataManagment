package org.liverpool.movie.managment.controller;

import java.math.BigDecimal;
import java.util.List;

import org.liverpool.movie.managment.beanapi.MovieBeanApi;
import org.liverpool.movie.managment.beanapi.RatingBeanApi;
import org.liverpool.movie.managment.component.Messages;
import org.liverpool.movie.managment.service.MovieService;
import org.liverpool.movie.managment.service.RatingService;
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
@RequestMapping("/api/rating")
@Api(value="Ratings", tags="Manage the CRUD operations within the Rating table")
@Slf4j
public class RatingController {
    
    @Autowired
    RatingService ratingService;
    
    @Autowired
	MovieService movieService;
    
    @Autowired
    Messages messages;

    @ApiOperation(
		      value = "Find a Rating by its identifier", 
		      notes = "Returns data about the Rating",
		      response = RatingBeanApi.class, 
		      produces = "application/json")
    @ApiResponses(value =
	{   
		@ApiResponse(code = 200, message = "Rating found")
	})
    @GetMapping(value = "/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RatingBeanApi> findById(@PathVariable(name = "id") Integer id) throws Exception
    {
    	log.info("findById method requested");
    	
    	RatingBeanApi beanApi = ratingService.getById(id);
    	
    	return new ResponseEntity<RatingBeanApi>(beanApi, HttpStatus.OK);
    }
    
    @ApiOperation(
		      value = "Insert a new Rating",
		      consumes = "application/json")
	@ApiResponses(value =
	{   
		@ApiResponse(code = 201, message = "Rating inserted"),
		@ApiResponse(code = 400, message = "Rating not inserted due to a malformed request")
	})
    @PutMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RatingBeanApi> newRating(@RequestBody(required = true) RatingBeanApi rating) throws Exception
    {
    	log.info("newRating method requested");
    	
    	RatingBeanApi inserted = null;
    	
    	try {
    		inserted = ratingService.insert(rating);
	  	} catch (Exception e) {
			log.error("Rating not inserted", e);
			
			return new ResponseEntity<RatingBeanApi>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	
    	if (inserted != null) {
    		return new ResponseEntity<RatingBeanApi>(inserted, HttpStatus.CREATED);
    	} else {
    		return new ResponseEntity<RatingBeanApi>(HttpStatus.BAD_REQUEST);
    	}
    }
    
    @ApiOperation(
		      value = "Find a Rating which has the name like those present in database", 
		      notes = "Returns a collection found of Rating",
		      response = RatingBeanApi.class, 
		      produces = "application/json")
    @GetMapping(value = "/searchMoviebyRatingAboveGivenScore", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<MovieBeanApi> searchMoviebyRatingAboveGivenScore(@RequestBody BigDecimal score) throws Exception	 
    {
    	log.info("findByTitle method requested");
    	
    	List<MovieBeanApi> list = null;
    	
    	try {
    		list = movieService.searchMoviesWithScoreAbove(score);
    	}catch (Exception e) {
			log.error("Movies not found due to an internal server error", e);
		}
    	
    	return list; 
    }
    
    @ApiOperation(
		      value = "Delete a Rating by its identifier")
    @ApiResponses(value =
	{   
		@ApiResponse(code = 200, message = "Rating deleted"),
		@ApiResponse(code = 400, message = "Rating not deleted due to a malformed request")
	})
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteRating(@PathVariable(name = "id") Integer id) throws Exception
    {
    	log.info("deleteRating method requested");
    	
    	try {
    		ratingService.delete(id);
    		
    		return new ResponseEntity<String>(HttpStatus.OK); 
    	} catch (Exception e) {
    		log.error("Rating not deleted", e);
    		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
    }
    
    @ApiOperation(
		      value = "Update an existing Rating",
		      consumes = "application/json")
	@ApiResponses(value =
	{   
		@ApiResponse(code = 200, message = "Rating updated"),
		@ApiResponse(code = 400, message = "Rating not updated due to a malformed request")
	})
	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateRating(@RequestBody(required = true) RatingBeanApi rating) throws Exception
	{
	  	log.info("updateRating method requested");
	  	
	  	boolean updated = false;
	  	
	  	try {
	  		updated = ratingService.update(rating);
	  	} catch (Exception e) {
			log.error("Rating not updated", e);
			
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	  	
	  	if (updated) {
	  		return new ResponseEntity<String>(HttpStatus.OK);
	  	} else {
	  		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	  	}
	}
}
