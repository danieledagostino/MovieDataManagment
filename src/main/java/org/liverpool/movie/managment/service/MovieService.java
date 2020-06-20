package org.liverpool.movie.managment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.liverpool.movie.managment.model.Director;
import org.liverpool.movie.managment.model.Movie;
import org.liverpool.movie.managment.repository.DirectorRepository;
import org.liverpool.movie.managment.repository.MovieRepository;
import org.liverpool.movie.managment.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MovieService implements IGenericCrud<Movie> {
	
	@Autowired
	MovieRepository repository;
	
	@Autowired
	DirectorRepository directorRepository;
	
	@Autowired
	RatingRepository ratingRepository;

	public List<Movie> searchMoviesByDirector(String directorName) {
		
		List<Movie> movieList = new ArrayList<Movie>();
		
		Director director = new Director();
		director.setName(directorName);
		
		Example<Director> example = Example.of(director);
		Optional<Director> optional = directorRepository.findOne(example);
		
		if (optional.isPresent()) {
			director = optional.get();
			for (Movie movie : director.getMovies()) {
				movieList.add(movie);
			}
			return movieList;
		} else {
			log.warn("No director found under the name {}", directorName);
		}
		
		return movieList;
	}

	@Override
	public boolean insert(Movie movie) {
		Movie m = repository.save(movie);

		return (m != null);
	}

	@Override
	public void delete(Movie movie) {
		repository.delete(movie);
	}
	
	public List<Movie> searchByTitle(String title){
		return repository.searchByTitle(title);
	}
}
