package org.liverpool.movie.managment.model;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.sun.istack.NotNull;

@Entity
/**
 * The Movie object represents the set of films 
 * associated to {@link Director}
 * 
 * @author daniele.dagostino
 *
 */
public class Movie implements Serializable {

	private static final long serialVersionUID = 2792584852500747814L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@NotNull
	private String name;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "director_id", referencedColumnName = "id")
	private Director director;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE,
			mappedBy = "movie")
	private Set<Rating> ratings;
	
	public Movie() {
		
	}
	
	public Movie(Integer id) {
		this.id = id;
	}
	
	public Movie(String title) {
		this.name = title;
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

	public Director getDirector() {
		return director;
	}

	public void setDirector(Director director) {
		this.director = director;
	}

	public Set<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(Set<Rating> ratings) {
		this.ratings = ratings;
	}
	
}
