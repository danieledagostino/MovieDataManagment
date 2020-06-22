package org.liverpool.movie.managment.controller;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
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

/**
 * MovieController test
 * If you face any problem in executing this test try to switch to "test runner" engine when performing "run configuration" of this junit test
 * 
 * @author daniele.dagostino
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
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
	@Order(1)
	public void getMovieById() throws Exception {
		
		MovieBeanApi movieBeanApi = new MovieBeanApi();
		movieBeanApi.setId(1);
		movieBeanApi.setName("Duel");
		movieBeanApi.setDirector(new DirectorBeanApi(1, "Steven Spielberg"));
		
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
	@Order(2)
	public void getRealMovieById() throws Exception {
		TestRestTemplate restTemplate = new TestRestTemplate();
		
	    URI uri = new URI(domain + ApiBaseUrl + "findById/2");
	    
	    ResponseEntity<MovieBeanApi> result = restTemplate.getForEntity(uri, MovieBeanApi.class);
	    
	    assertEquals(200, result.getStatusCodeValue());
	    assertEquals(result.getBody().getName(), "Jaws");
	}
	
	@Test
	@Order(3)
	public void newInsert() throws Exception {
		MovieBeanApi movieBeanApi = new MovieBeanApi();
		movieBeanApi.setName("E.T.");
		movieBeanApi.setDirector(new DirectorBeanApi(1));
		
		String requestJson = objectMapper.writeValueAsString(movieBeanApi);
		
		//MvcResult response = 
		this.mockMvc.perform(put(ApiBaseUrl + "new").contentType(APPLICATION_JSON_UTF8)
		        .content(requestJson))
		        .andExpect(status().isCreated())
		        .andExpect(jsonPath("$.id", is(11)));
		
		//assertThat(response.getResponse().getContentAsString()).c
	}
	
	@Test
	@Order(4)
	public void findMovieByName() throws Exception {
		String title = "Close Encounters of the Third Kind";
		
		this.mockMvc.perform(get(ApiBaseUrl + "searchByTitle").contentType(APPLICATION_JSON_UTF8)
        .content(title)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(3)));
		
	}
	
	@Test
	@Order(5)
	public void deleteById() throws Exception {
		
		this.mockMvc.perform(delete(ApiBaseUrl + "delete/2")).andExpect(status().isOk());
	}
	
	@Test
	@Order(6)
	public void getDeleteByIdZero() throws Exception {
		
		this.mockMvc.perform(delete(ApiBaseUrl + "delete/0")).andExpect(status().isBadRequest());
	}
	
	@Test
	@Order(7)
	public void updateAndExistingMovieData() throws Exception {
		String title = "Indiana Jones";
		
		MovieBeanApi movieBeanApi = new MovieBeanApi();
		movieBeanApi.setId(10);
		movieBeanApi.setName(title);
		movieBeanApi.setDirector(new DirectorBeanApi(1));
		
		String requestJson = objectMapper.writeValueAsString(movieBeanApi);
		
		this.mockMvc.perform(put(ApiBaseUrl + "update").contentType(APPLICATION_JSON_UTF8)
		        .content(requestJson))
		        .andExpect(status().isOk());				
	}
	
	@Test
	@Order(8)
	public void searchMoviesByDirector() throws Exception {
		String title = "Martin Scorzese";
		
		this.mockMvc.perform(get(ApiBaseUrl + "searchMoviesByDirectorName").contentType(APPLICATION_JSON_UTF8)
		        .content(title))
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$[*].name", containsInAnyOrder("Taxi Driver", "New York, New York", "Raging Bull")));
	}
	
}