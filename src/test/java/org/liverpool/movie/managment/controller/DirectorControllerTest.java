package org.liverpool.movie.managment.controller;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
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
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.liverpool.movie.managment.beanapi.DirectorBeanApi;
import org.liverpool.movie.managment.beanapi.MovieBeanApi;
import org.liverpool.movie.managment.model.Director;
import org.liverpool.movie.managment.repository.DirectorRepository;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class DirectorControllerTest {

	private String domain = "http://localhost:8080";
	private String ApiBaseUrl = "/api/director/";

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private DirectorRepository directorRepository;
	
    private ObjectMapper objectMapper;
	
	Director director = null;
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	
	@Before
	public void setUp() throws Exception {
		objectMapper = new ObjectMapper();
	}

	@Test
	@Order(1)
	public void getDirectorById() throws Exception {
		
		DirectorBeanApi directorBeanApi = new DirectorBeanApi();
		directorBeanApi.setId(1);
		directorBeanApi.setName("Steven Spielberg");
		
		String jsonContent = objectMapper.writeValueAsString(directorBeanApi);
		//when(directorRepository.getOne(id)).thenReturn(director);
		this.mockMvc.perform(get(ApiBaseUrl + "findById/1")).andExpect(status().isOk())
				.andExpect(content().json(jsonContent));
	}

	
	@Test
	@Order(2)
	public void newInsert() throws Exception {
		DirectorBeanApi directorBeanApi = new DirectorBeanApi();
		directorBeanApi.setName("Sergio Leone");
		
		String requestJson = objectMapper.writeValueAsString(directorBeanApi);
		
		this.mockMvc.perform(put(ApiBaseUrl + "new").contentType(APPLICATION_JSON_UTF8)
		        .content(requestJson))
		        .andExpect(status().isCreated())
		        .andExpect(jsonPath("$.id", is(4)));
	}
	
	@Test
	@Order(3)
	public void findDirectorByName() throws Exception {
		String name = "Martin Scorzese";
		
		this.mockMvc.perform(get(ApiBaseUrl + "searchByTitle").contentType(APPLICATION_JSON_UTF8)
        .content(name)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(2)));
		
	}
	
	@Test
	@Order(4)
	public void getDeleteById() throws Exception {
		
		this.mockMvc.perform(delete(ApiBaseUrl + "delete/1")).andExpect(status().isOk());
	}
	
	
	@Test
	@Order(5)
	public void updateAndExistingDirectorData() throws Exception {
		String name = "Quentin Tarantello";
		
		DirectorBeanApi directorBeanApi = new DirectorBeanApi();
		directorBeanApi.setId(3);
		directorBeanApi.setName(name);
		
		String requestJson = objectMapper.writeValueAsString(directorBeanApi);
		
		this.mockMvc.perform(put(ApiBaseUrl + "update").contentType(APPLICATION_JSON_UTF8)
		        .content(requestJson))
		        .andExpect(status().isOk());				
	}
	
}