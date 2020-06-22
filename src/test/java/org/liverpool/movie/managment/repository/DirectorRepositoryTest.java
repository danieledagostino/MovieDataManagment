package org.liverpool.movie.managment.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
@Transactional
public class DirectorRepositoryTest {
	
	Director director = null;
	Example<Director> example = null;
	
	@Autowired
	DirectorRepository directorRepository;
	
	@Before
	public void setUp() {
		director = new Director();
		director.setName("Quentin Tarantino");
		example = Example.of(director);
	}

    @Test
    @Order(1)    
    public void testSearchByDirectorName() throws Exception {
    	
    	Optional<Director> found = directorRepository.findOne(example);
    	assertTrue(found.isPresent());
    }
    
    @Test
    @Order(2)    
    public void A_testInsertNewDirector() throws Exception {
    	
    	Director d = new Director();
    	d.setName("Federico Fellini");
    	d = directorRepository.save(d);
    	
    	assertThat(d).isNotNull();
    	assertTrue(d.getId().intValue() > 0);
    }
    
    @Test
    @Order(3)    
    public void B_testInsertNewDirector() throws Exception {
    	
    	Director d = new Director();
    	d.setName("Federico Fellini");
    	Set<Movie> movies = new HashSet<Movie>();
    	movies.add(new Movie("La dolce vita"));
    	movies.add(new Movie("Roma"));
    	d.setMovies(movies);
    	
    	d = directorRepository.save(d);
    	
    	assertNotNull(d);
    	assertNotNull(d.getId());
    	
//    	d.getMovies().forEach( movie -> {
//    		assertTrue(movie.getId().intValue() > 0);
//    	});
    }
    
}
