package org.liverpool.movie.managment.repository;

import java.util.List;

import org.liverpool.movie.managment.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DirectorRepository extends JpaRepository<Director, Integer>{
	
	@Query(value = "from Director where name like '%:name%'")
	public List<Director> searchByName(@Param("name") String name);
	
}
