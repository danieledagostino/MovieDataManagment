package org.liverpool.movie.managment.repository;

import org.liverpool.movie.managment.model.Movie;
import org.liverpool.movie.managment.model.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface RatingRepository extends CrudRepository<Rating, Integer> {

}
