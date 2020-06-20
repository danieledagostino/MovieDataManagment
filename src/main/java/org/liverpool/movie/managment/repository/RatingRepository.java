package org.liverpool.movie.managment.repository;

import java.math.BigDecimal;
import java.util.List;

import org.liverpool.movie.managment.model.Movie;
import org.liverpool.movie.managment.model.Rating;
import org.liverpool.movie.managment.projection.RatingProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Integer> {

	@Query(value = "select movie.id, max(insertDate) from Rating where score > :score "
			+ "group by movie.id")
	List<RatingProjection> searchMoviesWithScoreAbove(@Param("score") BigDecimal score);
}
