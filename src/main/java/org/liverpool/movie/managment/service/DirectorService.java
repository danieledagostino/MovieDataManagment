package org.liverpool.movie.managment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.criterion.Restrictions;
import org.liverpool.movie.managment.beanapi.DirectorBeanApi;
import org.liverpool.movie.managment.beanapi.MovieBeanApi;
import org.liverpool.movie.managment.beanapi.RatingBeanApi;
import org.liverpool.movie.managment.component.Messages;
import org.liverpool.movie.managment.model.Director;
import org.liverpool.movie.managment.model.Movie;
import org.liverpool.movie.managment.model.Rating;
import org.liverpool.movie.managment.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class DirectorService implements IGenericCrud<DirectorBeanApi> {

	@Autowired
	DirectorRepository repository;
	
	@Autowired
	Messages message;
	
	@Override
	public DirectorBeanApi insert(DirectorBeanApi director) throws Exception {
		Director d = toDirector(director);
		d = repository.save(d);

		return toDTO(d);
	}

	@Override
	public void delete(Integer id) {
		if (id == null || new Integer(0).equals(id)) {
			throw new IllegalArgumentException(message.get("app.message.illegalarg.id.error"));
		}
		
		Director d = toDirector(new DirectorBeanApi(id));
		repository.delete(d);
	}
	
	public List<DirectorBeanApi> searchByName(String name){
		List<DirectorBeanApi> list = new ArrayList<DirectorBeanApi>();
		
		List<Director> directors = repository.searchByName(name);
		for (Director d : directors) {
			list.add(toDTO(d));
		}
		
		return list;
	}
	
	@Override
	public DirectorBeanApi getById(Integer id) {
		Director director = repository.getOne(id);
		
		return toDTO(director);
	}
	
	@Override
	public boolean update(DirectorBeanApi beanApi) throws Exception {
		Director d = toDirector(beanApi);
		d = repository.save(d);

		return (d != null);
	}
	
	private DirectorBeanApi toDTO(Director director) {
		DirectorBeanApi beanApi = new DirectorBeanApi();
		
		beanApi.setId(director.getId());
		beanApi.setName(director.getName());
		beanApi.setMovies(new ArrayList<MovieBeanApi>());
		Set<Movie> movies = null;
		if ((movies = director.getMovies()) != null) {
			for (Movie m : movies) {
				beanApi.getMovies()
					.add(new MovieBeanApi(m.getId(), m.getName()));
			}
		}
		
		return beanApi;
	}
	
	private Director toDirector(DirectorBeanApi dto) {
		Director director = new Director();
		director.setId(dto.getId());
		director.setName(dto.getName());
		
		return director;
	}
}
