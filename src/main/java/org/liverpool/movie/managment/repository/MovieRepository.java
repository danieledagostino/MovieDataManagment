package org.liverpool.movie.managment.repository;

import java.math.BigDecimal;
import java.util.List;

import org.liverpool.movie.managment.model.Director;
import org.liverpool.movie.managment.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends JpaRepository<Movie, Integer> {
	
	@Query(value = "from Rating where score = :score")
	List<Movie> searchMoviesHasRatingGreaterThan(@Param("score") BigDecimal score);

	@Query(value = "from Movie where name like %:title%")
	List<Movie> searchByTitle(@Param("title") String title);
}
