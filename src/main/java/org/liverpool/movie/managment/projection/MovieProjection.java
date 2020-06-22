package org.liverpool.movie.managment.projection;

public interface MovieProjection {
	
	//@Value("#{target.movie.id}")
	Integer getId();
	String getName();
	
}
