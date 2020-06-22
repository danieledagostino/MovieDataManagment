package org.liverpool.movie.managment.repository;

import java.math.BigDecimal;
import java.util.List;

import org.liverpool.movie.managment.model.Rating;
import org.liverpool.movie.managment.projection.MovieProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

	/**
	 * searchMoviesWithScoreAbove provide a search within the rating table where the given score is above the exist one
	 * 
	 * @param {@link BigDecimal} score
	 * @return a {@link List} of Rating with only its identifier
	 */
	@Query(value = "select movie.id, movie.name, max(insertDate) from Rating where score > :score "
			+ "group by movie.id, movie.name")
	List<MovieProjection> searchMoviesWithScoreAbove(@Param("score") BigDecimal score);
}
