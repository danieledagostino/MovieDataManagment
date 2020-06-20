package org.liverpool.movie.managment.repository;

import org.liverpool.movie.managment.model.Director;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectorRepository extends JpaRepository<Director, Integer>{
	
	
}
