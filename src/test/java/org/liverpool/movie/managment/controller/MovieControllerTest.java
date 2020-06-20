package org.liverpool.movie.managment.controller;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.liverpool.movie.managment.beanapi.DirectorBeanApi;
import org.liverpool.movie.managment.beanapi.MovieBeanApi;
import org.liverpool.movie.managment.model.Director;
import org.liverpool.movie.managment.model.Movie;
import org.liverpool.movie.managment.repository.MovieRepository;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class MovieControllerTest {

	private String domain = "http://localhost:8080";
	private String ApiBaseUrl = "/api/movie/";

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private MovieRepository movieRepository;
	
    private ObjectMapper objectMapper;
	
	private String movieDuelJson = "{'id': 1, 'name' : 'Duel', director : {'id': 1, 'name' : 'Stephen Spielberg'}, ratings : []}";

	MovieBeanApi movieBeanApi = null;
	Movie movie = null;
	
	private final String baseUrl = domain + ApiBaseUrl + "findById/2";
	
	@Before
	public void setUp() throws Exception {
		movieBeanApi = new MovieBeanApi();
		movieBeanApi.setId(1);
		movieBeanApi.setName("Duel");
		movieBeanApi.setDirector(new DirectorBeanApi(1, "Stephen Spielberg"));
		
		movie = new Movie();
		movie.setId(1);
		movie.setName("Duel");
		movie.setDirector(new Director());
		
		objectMapper = new ObjectMapper();
	}

	@Test
	public void getDirectorById() throws Exception {
		Integer id = 1;
		
		String jsonContent = objectMapper.writeValueAsString(movieBeanApi);
		when(movieRepository.getOne(id)).thenReturn(movie);
		this.mockMvc.perform(get(ApiBaseUrl + "findById/1")).andDo(print()).andExpect(status().isOk())
				.andExpect(content().json(jsonContent));
	}

	@Test
	public void getRealDirectorById() throws Exception {
		TestRestTemplate restTemplate = new TestRestTemplate();
		
	    URI uri = new URI(baseUrl);
	    
	    ResponseEntity<MovieBeanApi> result = restTemplate.getForEntity(uri, MovieBeanApi.class);
	    
	    assertEquals(200, result.getStatusCodeValue());
	    assertEquals(result.getBody().getName(), "The Shark");
	}
	
}