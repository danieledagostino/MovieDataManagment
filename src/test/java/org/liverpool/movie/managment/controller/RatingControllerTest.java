package org.liverpool.movie.managment.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.liverpool.movie.managment.beanapi.MovieBeanApi;
import org.liverpool.movie.managment.beanapi.RatingBeanApi;
import org.liverpool.movie.managment.model.Rating;
import org.liverpool.movie.managment.repository.RatingRepository;
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
 * RatingController test
 * If you face any problem in executing this test try to switch to "test runner" engine when performing "run configuration" of this junit test
 * 
 * @author daniele.dagostino
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class RatingControllerTest {

	private String ApiBaseUrl = "/api/rating/";

	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private RatingRepository ratingRepository;
	
    private ObjectMapper objectMapper;
	
	Rating rating = null;
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	
	@Before
	public void setUp() throws Exception {
		objectMapper = new ObjectMapper();
	}


	
	@Test
	@Order(1)
	public void newInsert() throws Exception {
		RatingBeanApi ratingBeanApi = new RatingBeanApi();
		ratingBeanApi.setScore(new BigDecimal(3.5));
		MovieBeanApi movie = new MovieBeanApi(1);
		ratingBeanApi.setMovieBeanApi(movie);
		
		String requestJson = objectMapper.writeValueAsString(ratingBeanApi);
		
		this.mockMvc.perform(put(ApiBaseUrl + "new").contentType(APPLICATION_JSON_UTF8)
		        .content(requestJson))
		        .andExpect(status().isCreated());
	}
	
	@Test
	@Order(2)
	public void searchMoviebyRatingAboveGivenScore() throws Exception {
		String name = "Martin Scorzese";
		
		BigDecimal score = new BigDecimal(2.0);	
		
		String requestJson = objectMapper.writeValueAsString(score);
		
		this.mockMvc.perform(get(ApiBaseUrl + "searchMoviebyRatingAboveGivenScore").contentType(APPLICATION_JSON_UTF8)
        .content(requestJson)).andExpect(status().isOk())
				.andExpect(jsonPath("$.length()", is(2)));
		
	}
	
	
}