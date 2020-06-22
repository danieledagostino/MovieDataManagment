package org.liverpool.movie.managment.controller;

import java.util.List;

import org.liverpool.movie.managment.beanapi.DirectorBeanApi;
import org.liverpool.movie.managment.component.Messages;
import org.liverpool.movie.managment.service.DirectorService;
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
@RequestMapping("/api/director")
@Api(value="Directors", tags="Manage the CRUD operations within the Director table")
@Slf4j
public class DirectorController {
    
    @Autowired
    DirectorService directorService;
    
    @Autowired
    Messages messages;

    @ApiOperation(
		      value = "Find a Director by its identifier", 
		      notes = "Returns data about the Director",
		      response = DirectorBeanApi.class, 
		      produces = "application/json")
    @ApiResponses(value =
	{   
		@ApiResponse(code = 200, message = "Director found")
	})
    @GetMapping(value = "/findById/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DirectorBeanApi> findById(@PathVariable(name = "id") Integer id) throws Exception
    {
    	log.info("findById method requested");
    	
    	DirectorBeanApi beanApi = directorService.getById(id);
    	
    	return new ResponseEntity<DirectorBeanApi>(beanApi, HttpStatus.OK);
    }
    
    @ApiOperation(
		      value = "Insert a new Director",
		      consumes = "application/json")
	@ApiResponses(value =
	{   
		@ApiResponse(code = 201, message = "Director inserted"),
		@ApiResponse(code = 400, message = "Director not inserted due to a malformed request")
	})
    @PutMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DirectorBeanApi> newDirector(@RequestBody(required = true) DirectorBeanApi director) throws Exception
    {
    	log.info("newDirector method requested");
    	
    	DirectorBeanApi inserted = null;
    	
    	try {
    		inserted = directorService.insert(director);
	  	} catch (Exception e) {
			log.error("Director not inserted", e);
			
			return new ResponseEntity<DirectorBeanApi>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    	
    	if (inserted != null) {
    		return new ResponseEntity<DirectorBeanApi>(inserted, HttpStatus.CREATED);
    	} else {
    		return new ResponseEntity<DirectorBeanApi>(HttpStatus.BAD_REQUEST);
    	}
    }
    
    @ApiOperation(
		      value = "Find a Director which has the name like those present in database", 
		      notes = "Returns a collection found of Director",
		      response = DirectorBeanApi.class, 
		      produces = "application/json")
    @GetMapping(value = "/searchByName", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<DirectorBeanApi> findByName(@RequestBody String name) throws Exception	 
    {
    	log.info("findByTitle method requested");
    	
    	List<DirectorBeanApi> list = directorService.searchByName(name);
    	
    	return list; 
    }
    
    @ApiOperation(
		      value = "Delete a Director by its identifier")
    @ApiResponses(value =
	{   
		@ApiResponse(code = 200, message = "Director deleted"),
		@ApiResponse(code = 400, message = "Director not deleted due to a malformed request")
	})
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteDirector(@PathVariable(name = "id") Integer id) throws Exception
    {
    	log.info("deleteDirector method requested");
    	
    	try {
    		directorService.delete(id);
    		
    		return new ResponseEntity<String>(HttpStatus.OK); 
    	} catch (Exception e) {
    		log.error("Director not deleted", e);
    		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
    }
    
    @ApiOperation(
		      value = "Update an existing Director",
		      consumes = "application/json")
	@ApiResponses(value =
	{   
		@ApiResponse(code = 200, message = "Director updated"),
		@ApiResponse(code = 400, message = "Director not inserted due to a malformed request")
	})
	@PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateDirector(@RequestBody(required = true) DirectorBeanApi director) throws Exception
	{
	  	log.info("updateDirector method requested");
	  	
	  	boolean updated = false;
	  	
	  	try {
	  		updated = directorService.update(director);
	  	} catch (Exception e) {
			log.error("Director not updated", e);
			
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	  	
	  	if (updated) {
	  		return new ResponseEntity<String>(HttpStatus.OK);
	  	} else {
	  		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
	  	}
	}
}
