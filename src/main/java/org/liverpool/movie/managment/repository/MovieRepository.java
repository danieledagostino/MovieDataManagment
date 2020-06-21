package org.liverpool.movie.managment.repository;

import java.util.List;

import org.liverpool.movie.managment.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
	
	/**
	 * searchByTitle provide a search within the movie table searching by its title
	 * 
	 * 
	 * @param title
	 * @return a {@link List} a movies have the given string in its title
	 */
	@Query(value = "from Movie where name like %:title%")
	List<Movie> searchByTitle(@Param("title") String title);
	
}
