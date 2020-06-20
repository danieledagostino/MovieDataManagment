package org.liverpool.movie.managment.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
public class Rating implements Serializable {

	private static final long serialVersionUID = -4423858878126015670L;

	@Id
	private Integer id;
	
	private BigDecimal score;
	
	@DateTimeFormat
	@Column(name="insertDate", 
		//columnDefinition="SET DEFAULT CURRENT_TIMESTAMP",
		insertable = true,
		updatable = false)
	private Date insertDate;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity = Movie.class)
	private Set<Movie> movies;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getScore() {
		return score;
	}

	public void setScore(BigDecimal score) {
		this.score = score;
	}

	public Date getInsertDate() {
		return insertDate;
	}

	public void setInsertDate(Date insertDate) {
		this.insertDate = insertDate;
	}

	public Set<Movie> getMovies() {
		return movies;
	}

	public void setMovies(Set<Movie> movies) {
		this.movies = movies;
	}
	
}
