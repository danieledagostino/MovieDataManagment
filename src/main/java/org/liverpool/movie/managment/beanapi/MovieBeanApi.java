package org.liverpool.movie.managment.beanapi;

import org.springframework.boot.jackson.JsonComponent;

import lombok.Data;
import lombok.core.PublicApiCreatorApp;

@JsonComponent
@Data
public class MovieBeanApi {

	
	private Integer id;
	private String name;
	private DirectorBeanApi director;
	private RatingBeanApi rating;
	
	private void MovieBeanApi() {

	}
	
	private void MovieBeanApi(Integer id) {
		this.id = id;
	}
}
