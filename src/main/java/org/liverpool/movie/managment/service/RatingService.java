package org.liverpool.movie.managment.service;

import org.liverpool.movie.managment.beanapi.DirectorBeanApi;
import org.liverpool.movie.managment.beanapi.MovieBeanApi;
import org.liverpool.movie.managment.beanapi.RatingBeanApi;
import org.liverpool.movie.managment.model.Director;
import org.liverpool.movie.managment.model.Movie;
import org.liverpool.movie.managment.model.Rating;
import org.liverpool.movie.managment.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Data transfer object used to pass throught the api the data tha represent the Rating table
 * 
 * @author daniele.dagostino
 *
 */
@Service
public class RatingService implements IGenericCrud<RatingBeanApi> {

	@Autowired
	RatingRepository repository;

	public boolean insert(RatingBeanApi ratingBeanApi) {
		Rating r = toRating(ratingBeanApi);
		r = repository.save(r);

		return (r != null);
	}

	public void delete(Integer id) {
		if (id == null || new Integer(0).equals(id)) {
			throw new IllegalArgumentException("The passed id is null or not correct");
		}
		
		Rating r = toRating(new RatingBeanApi(id));
		repository.delete(r);
	}

	@Override
	public RatingBeanApi getById(Integer id) {
		Rating rating = repository.getOne(id);
		
		return toDTO(rating);
	}
	
	private RatingBeanApi toDTO(Rating rating) {
		RatingBeanApi beanApi = new RatingBeanApi();
		
		beanApi.setId(rating.getId());
		beanApi.setScore(rating.getScore());
		Movie m = null;
		if ((m = rating.getMovie()) != null) {
			MovieBeanApi movieBeanApi = new MovieBeanApi();
			movieBeanApi.setId(m.getId());
			movieBeanApi.setName(m.getName());
			beanApi.setMovieBeanApi(movieBeanApi);
		}
		
		return beanApi;
	}
	
	private Rating toRating(RatingBeanApi dto) {
		Rating rating = new Rating();
		rating.setId(dto.getId());
		rating.setScore(dto.getScore());
		if (dto.getMovieBeanApi() != null) {
			Movie m = new Movie(dto.getMovieBeanApi().getId());
			rating.setMovie(m);
		}
		
		return rating;
	}
}
