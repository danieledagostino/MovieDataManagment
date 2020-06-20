package org.liverpool.movie.managment.repository;

import java.util.List;

import org.liverpool.movie.managment.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
	
	@Query(value = "from Movie where name like %:title%")
	List<Movie> searchByTitle(@Param("title") String title);
	
}
