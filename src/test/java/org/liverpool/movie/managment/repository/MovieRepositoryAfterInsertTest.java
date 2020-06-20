package org.liverpool.movie.managment.repository;

import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.liverpool.movie.managment.model.Director;
import org.liverpool.movie.managment.model.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@Transactional
@TestMethodOrder(OrderAnnotation.class)
public class MovieRepositoryAfterInsertTest {
	
	Director director = null;
	Example<Director> example = null;
	
	@Autowired
	DirectorRepository directorRepository;
	
	@Before
	public void setUp() {
		Director d = new Director();
    	d.setName("Federico Fellini");
    	d = directorRepository.save(d);
    	
    	assertTrue(d.getId().intValue() > 0);
	}

    @Test
    @Order(1)    
    public void D_testInsertNewDirector() throws Exception {
    	
    	Director d = new Director();
    	d.setName("Federico Fellini");
    	Example<Director> example = Example.of(d);
    	Optional<Director> found = directorRepository.findOne(example);

    	d = found.get();
    	
    	Set<Movie> movies = new HashSet<Movie>();
    	movies.add(new Movie("La dolce vita"));
    	movies.add(new Movie("Roma"));
    	
    	d.setMovies(movies);
    	
    	d = directorRepository.save(d);
    	
    	d.getMovies().forEach( movie -> {
    		assertTrue(movie.getId().intValue() > 0);
    	});
    }
}
