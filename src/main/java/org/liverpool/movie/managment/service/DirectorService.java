package org.liverpool.movie.managment.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.criterion.Restrictions;
import org.liverpool.movie.managment.beanapi.DirectorBeanApi;
import org.liverpool.movie.managment.beanapi.MovieBeanApi;
import org.liverpool.movie.managment.model.Director;
import org.liverpool.movie.managment.model.Movie;
import org.liverpool.movie.managment.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectorService implements IGenericCrud<DirectorBeanApi> {

	@Autowired
	DirectorRepository repository;
	
	public boolean insert(DirectorBeanApi director) {
		Director d = toDirector(director);
		d = repository.save(d);

		return (d != null);
	}

	public void delete(Integer id) {
		if (id == null || new Integer(0).equals(id)) {
			throw new IllegalArgumentException("The passed id is null or not correct");
		}
		
		Director d = toDirector(new DirectorBeanApi(id));
		repository.delete(d);
	}
	
	public List<Director> searchByName(String name){
		return repository.searchByName(name);
	}
	
	@Override
	public DirectorBeanApi getById(Integer id) {
		Director director = repository.getOne(id);
		
		return toDTO(director);
	}
	
	private DirectorBeanApi toDTO(Director director) {
		DirectorBeanApi beanApi = new DirectorBeanApi();
		
		beanApi.setId(director.getId());
		beanApi.setName(director.getName());
		
		return beanApi;
	}
	
	private Director toDirector(DirectorBeanApi dto) {
		Director director = new Director();
		director.setId(dto.getId());
		director.setName(dto.getName());
		
		return director;
	}
}
