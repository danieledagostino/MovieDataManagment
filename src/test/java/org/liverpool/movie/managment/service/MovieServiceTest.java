package org.liverpool.movie.managment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.liverpool.movie.managment.beanapi.MovieBeanApi;
import org.liverpool.movie.managment.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class MovieServiceTest {
	
	Movie movie = null;
	Example<Movie> example = null;
	
	@Autowired
	MovieService movieService;
	
	@Before
	public void setUp() {
		movie = new Movie();
		movie.setName("Ready Player One");
		example = Example.of(movie);
		
		
	}

    @Test
    @Order(1)    
    public void searchMoviesByDirectorName() throws Exception {
    	
    	List<MovieBeanApi> list = movieService.searchMoviesByDirector("Steven Spielberg");
    	
    	assertThat(list).size().isGreaterThan(0);
    	assertThat(list, hasItem(
    		    hasProperty("name", is("Duel"))
    		));
    }
    
    @Test
    @Order(2)    
    public void searchByTitle() throws Exception {
    	
    	List<MovieBeanApi> list = movieService.searchByTitle("ll");
    	
    	assertThat(list).size().isEqualTo(2);
    }
    
    @Test
    @Order(3)
    public void searchByRatingScore() throws Exception {
    	
    	List<Movie> list = movieService.
    			searchMoviesWithScoreAbove(new BigDecimal(2.0));
    		
    	assertThat(list).size().isEqualTo(2);
    }
    
}
