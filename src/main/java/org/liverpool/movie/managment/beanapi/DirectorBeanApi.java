package org.liverpool.movie.managment.beanapi;

import java.util.List;

import org.springframework.boot.jackson.JsonComponent;

import lombok.Data;

@JsonComponent
@Data
public class DirectorBeanApi {

	
	private Integer id;
	private String name;
	private List<MovieBeanApi> movies;
	
	public DirectorBeanApi() {
		
	}
	
	public DirectorBeanApi(Integer id) {
		this.id = id;
	}
	
	public DirectorBeanApi(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
}
