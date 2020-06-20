package org.liverpool.movie.managment.service;

import org.liverpool.movie.managment.model.Rating;
import org.liverpool.movie.managment.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RatingService implements IGenericCrud<Rating> {

	@Autowired
	RatingRepository repository;

	public boolean insert(Rating rating) {
		Rating r = repository.save(rating);

		return (r != null);
	}

	public void delete(Rating rating) {
		repository.delete(rating);

	}
}
