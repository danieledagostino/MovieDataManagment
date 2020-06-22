package org.liverpool.movie.managment.service;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.liverpool.movie.managment.beanapi.DirectorBeanApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class DirectorServiceTest {
	
	@Autowired
	DirectorService service;
	
	@Before
	public void setUp() {
		
		
	}

    @Test
    public void searchDirectorsByName() throws Exception {
    	
    	List<DirectorBeanApi> list = service.searchByName("Steven Spielberg");
    	
    	assertThat(list.size(), is(1));
    	assertThat(list.get(0).getMovies().size(), is(4));
    }
    
}
