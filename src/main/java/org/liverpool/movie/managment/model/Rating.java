package org.liverpool.movie.managment.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
/**
 * The Rating object represents a set of scores entered by insertion date.
 * It is possible to use the data to carry out arithmetic averages and analyze 
 * the trend of change in the popularity of the {@link Movie}.
 * 
 * @author daniele.dagostino
 *
 */
public class Rating implements Serializable {

	private static final long serialVersionUID = -4423858878126015670L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	private BigDecimal score;
	
	@DateTimeFormat
	@Column(name="insertDate", 
		//columnDefinition="SET DEFAULT CURRENT_TIMESTAMP",
		insertable = true,
		updatable = false)
	private Date insertDate;
	
	@OneToOne(fetch = FetchType.LAZY, targetEntity = Movie.class)
	private Movie movie;

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

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

}
