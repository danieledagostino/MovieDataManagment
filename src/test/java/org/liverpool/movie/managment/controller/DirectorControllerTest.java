package org.liverpool.movie.managment.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.liverpool.movie.managment.beanapi.DirectorBeanApi;
import org.liverpool.movie.managment.model.Director;
import org.liverpool.movie.managment.repository.DirectorRepository;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * DirectorController test
 * If you face any problem in executing this test try to switch to "test runner" engine when performing "run configuration" of this junit test
 * 
 * @author daniele.dagostino
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class DirectorControllerTest {

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
		
		//when(directorRepository.getOne(id)).thenReturn(director);
		this.mockMvc.perform(get(ApiBaseUrl + "findById/1")).andExpect(status().isOk())
				.andExpect(jsonPath("$.name", is("Steven Spielberg")));
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
		
		this.mockMvc.perform(get(ApiBaseUrl + "searchByName").contentType(APPLICATION_JSON_UTF8)
        .content(name)).andDo(print()).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(2)));
		
	}
	
	
	@Test
	@Order(4)
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
	
	@Test
	@Order(5)
	public void deleteById() throws Exception {
		
		this.mockMvc.perform(delete(ApiBaseUrl + "delete/10")).andExpect(status().isOk());
	}
	
}