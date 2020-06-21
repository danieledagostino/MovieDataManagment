package org.liverpool.movie.managment.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.nio.charset.Charset;

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
import org.springframework.http.MediaType;
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
	
	Movie movie = null;
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	
	@Before
	public void setUp() throws Exception {
		objectMapper = new ObjectMapper();
	}

	@Test
	public void getMovieById() throws Exception {
		
		MovieBeanApi movieBeanApi = new MovieBeanApi();
		movieBeanApi.setId(1);
		movieBeanApi.setName("Duel");
		movieBeanApi.setDirector(new DirectorBeanApi(1, "Stephen Spielberg"));
		
		movie = new Movie();
		movie.setId(1);
		movie.setName("Duel");
		movie.setDirector(new Director());
		
		String jsonContent = objectMapper.writeValueAsString(movieBeanApi);
		//when(movieRepository.getOne(id)).thenReturn(movie);
		this.mockMvc.perform(get(ApiBaseUrl + "findById/1")).andExpect(status().isOk())
				.andExpect(content().json(jsonContent));
	}

	@Test
	public void getRealMovieById() throws Exception {
		TestRestTemplate restTemplate = new TestRestTemplate();
		
	    URI uri = new URI(domain + ApiBaseUrl + "findById/2");
	    
	    ResponseEntity<MovieBeanApi> result = restTemplate.getForEntity(uri, MovieBeanApi.class);
	    
	    assertEquals(200, result.getStatusCodeValue());
	    assertEquals(result.getBody().getName(), "The Shark");
	}
	
	@Test
	public void newInsert() throws Exception {
		MovieBeanApi movieBeanApi = new MovieBeanApi();
		movieBeanApi.setName("E.T.");
		movieBeanApi.setDirector(new DirectorBeanApi(1));
		
		String requestJson = objectMapper.writeValueAsString(movieBeanApi);
		
		this.mockMvc.perform(post(ApiBaseUrl + "new").contentType(APPLICATION_JSON_UTF8)
		        .content(requestJson))
		        .andExpect(status().isCreated());
		
	}
	
	@Test
	public void findMovieByName() throws Exception {
		String title = "Close Encounters of the Third Kind";
		
		this.mockMvc.perform(get(ApiBaseUrl + "searchByTitle").contentType(APPLICATION_JSON_UTF8)
        .content(title)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(3)));
		
	}
	
}