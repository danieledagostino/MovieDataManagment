package org.liverpool.movie.managment.repository;

import static org.assertj.core.api.Assertions.assertThat;
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
//@Sql(executionPhase=ExecutionPhase.BEFORE_TEST_METHOD,scripts="classpath:/test-sql/insert.sql")
@Transactional
@TestMethodOrder(OrderAnnotation.class)
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
    public void A_testSearchByDirectorName() throws Exception {
    	
    	Optional<Director> found = directorRepository.findOne(example);
    	assertTrue(found.isPresent());
    }
    
    @Test
    @Order(2)    
    public void B_testAssertNumberOfMovies() throws Exception {
    	
    	Optional<Director> found = directorRepository.findOne(example);
    	Director d = found.get();
    	
    	assertTrue(d.getMovies().size() == 3);
    }
    
    @Test
    @Order(3)    
    public void C_testInsertNewDirector() throws Exception {
    	
    	Director d = new Director();
    	d.setName("Federico Fellini");
    	d = directorRepository.save(d);
    	
    	assertThat(d).isNotNull();
    }
    
}
