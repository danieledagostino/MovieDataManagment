package org.liverpool.movie.managment.beanapi;

import java.math.BigDecimal;

import org.springframework.boot.jackson.JsonComponent;

import lombok.Data;

@JsonComponent
@Data
public class RatingBeanApi {

	
	private Integer id;
	private BigDecimal score;
}
