package org.liverpool.movie.managment.repository;

import java.util.List;

import org.liverpool.movie.managment.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectorRepository extends JpaRepository<Director, Integer>{
	
	/**
	 * searchByName provide a search within the movie table where the given name is like the existen one
	 * 
	 * @param {@link String} name
	 * @return a {@link List} a Director have the given string in its name
	 */
	@Query(value = "from Director where name like '%:name%'")
	public List<Director> searchByName(@Param("name") String name);
	
}
