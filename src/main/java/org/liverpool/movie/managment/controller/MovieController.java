package org.liverpool.movie.managment.controller;

import org.liverpool.movie.managment.beanapi.MovieBeanApi;
import org.liverpool.movie.managment.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public MovieBeanApi findById(@PathVariable(name = "id") Integer id)
    {
    	MovieBeanApi beanApi = movieService.getById(id);
    	
    	return beanApi; 
    }
}
