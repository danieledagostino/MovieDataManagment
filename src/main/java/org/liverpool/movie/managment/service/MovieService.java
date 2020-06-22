package org.liverpool.movie.managment.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.liverpool.movie.managment.beanapi.DirectorBeanApi;
import org.liverpool.movie.managment.beanapi.MovieBeanApi;
import org.liverpool.movie.managment.component.Messages;
import org.liverpool.movie.managment.model.Director;
import org.liverpool.movie.managment.model.Movie;
import org.liverpool.movie.managment.projection.MovieProjection;
import org.liverpool.movie.managment.repository.DirectorRepository;
import org.liverpool.movie.managment.repository.MovieRepository;
import org.liverpool.movie.managment.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@Transactional
public class MovieService implements IGenericCrud<MovieBeanApi> {
	
	@Autowired
	MovieRepository repository;
	
	@Autowired
	DirectorRepository directorRepository;
	
	@Autowired
	RatingRepository ratingRepository;
	
	@Autowired
	Messages message;

	public List<MovieBeanApi> searchMoviesByDirector(String directorName) {
		
		List<MovieBeanApi> movieList = new ArrayList<MovieBeanApi>();
		
		Director director = new Director();
		director.setName(directorName);
		
		Example<Director> example = Example.of(director);
		Optional<Director> optional = directorRepository.findOne(example);
		
		if (optional.isPresent()) {
			director = optional.get();
			for (Movie movie : director.getMovies()) {
				movieList.add(toDTO(movie));
			}
			return movieList;
		} else {
			log.warn("No director found under the name {}", directorName);
		}
		
		return movieList;
	}
	
	public List<MovieBeanApi> searchMoviesWithScoreAbove(BigDecimal score) {
		List<MovieBeanApi> moviesList = new ArrayList<MovieBeanApi>();
		
		List<MovieProjection> movies = ratingRepository.searchMoviesWithScoreAbove(score);
		if (movies.size() > 0) {
			for (MovieProjection m : movies) {
				moviesList.add(new MovieBeanApi(m.getId(), m.getName()));
			}
		}
		
		
		return moviesList;
	}

	@Override
	public MovieBeanApi insert(MovieBeanApi movieBeanApi) throws Exception {
		Movie m = toMovie(movieBeanApi);
		m = repository.save(m);

		return toDTO(m);
	}

	@Override
	public void delete(Integer id) throws IllegalArgumentException {
		
		if (new Integer(0).equals(id)) {
			throw new IllegalArgumentException(message.get("app.message.illegalarg.id.error"));
		}
		
		Movie m = toMovie(new MovieBeanApi(id));
		repository.delete(m);
	}
	
	public List<MovieBeanApi> searchByTitle(String title){
		List<Movie> list = repository.searchByTitle(title);
		List<MovieBeanApi> dtoList = new ArrayList<MovieBeanApi>();
		
		for (Movie m : list) {
			dtoList.add(toDTO(m));
		}
		return dtoList;
	}
	
	@Override
	public MovieBeanApi getById(Integer id) {
		Movie movie = repository.getOne(id);
		
		return toDTO(movie);
	}
	
	private MovieBeanApi toDTO(Movie movie) {
		MovieBeanApi beanApi = new MovieBeanApi();
		
		beanApi.setId(movie.getId());
		beanApi.setName(movie.getName());
		beanApi.setDirector(new DirectorBeanApi(movie.getDirector().getId(), movie.getDirector().getName()));
		
		return beanApi;
	}
	
	private Movie toMovie(MovieBeanApi dto) {
		Movie movie = new Movie();
		movie.setId(dto.getId());
		movie.setName(dto.getName());
		if (dto.getDirector() != null) {
			Director d =new Director(dto.getDirector().getId(), dto.getDirector().getName());
			movie.setDirector(d);
		}
		
		return movie;
	}
	
	@Override
	public boolean update(MovieBeanApi beanApi) throws Exception {
		Movie m = toMovie(beanApi);
		m = repository.save(m);

		return (m != null);
	}
}
