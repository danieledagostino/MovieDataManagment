package org.liverpool.movie.managment.controller;

import java.util.List;

import org.liverpool.movie.managment.beanapi.MovieBeanApi;
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

@RestController
@RequestMapping("/api/movie")
public class MovieController {
    
    @Autowired
    MovieService movieService;

    @GetMapping("/findById/{id}")
    @ResponseBody
    public MovieBeanApi findById(@PathVariable(name = "id") Integer id) throws Exception
    {
    	MovieBeanApi beanApi = movieService.getById(id);
    	
    	return beanApi; 
    }
    
    @PutMapping(value = "/new", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> newMovie(@RequestBody(required = true) MovieBeanApi movie) throws Exception
    {
    	boolean inserted = movieService.insert(movie);
    	
    	if (inserted) {
    		return new ResponseEntity<String>(HttpStatus.CREATED);
    	} else {
    		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
    	}
    }
    
    @GetMapping(value = "/searchByTitle", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<MovieBeanApi> findByTitle(@RequestBody String title) throws Exception	 
    {
    	List<MovieBeanApi> list = movieService.searchByTitle(title);
    	
    	return list; 
    }
    
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteMovie(@PathVariable(name = "id") Integer id) throws Exception
    {
    	try {
    		movieService.delete(id);
    		
    		return new ResponseEntity<String>(HttpStatus.OK); 
    	} catch (Exception e) {
    		return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
    }
}
