package org.liverpool.movie.managment.beanapi;

import java.math.BigDecimal;

import org.springframework.boot.jackson.JsonComponent;

import lombok.Data;

@JsonComponent
@Data
public class RatingBeanApi {

	
	private Integer id;
	private BigDecimal score;
	private MovieBeanApi movieBeanApi;
	
	public RatingBeanApi() {

	}
	
	public RatingBeanApi(Integer id) {
		this.id = id;
	}
}
