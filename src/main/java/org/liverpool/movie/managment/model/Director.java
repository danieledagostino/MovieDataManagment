package org.liverpool.movie.managment.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
/**
 * The object Director represent the data about the movie director.
 * It has a list of {@link Movie} to the director's movies
 * 
 * @author daniele.dagostino
 *
 */
public class Director implements Serializable {

	private static final long serialVersionUID = 7363759658762898935L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private String name;
	
	@OneToMany(fetch = FetchType.LAZY)
	Set<Movie> movies;
	
	public Director() {
	}
	

	public Director(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Movie> getMovies() {
		return movies;
	}

	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}
	
}
