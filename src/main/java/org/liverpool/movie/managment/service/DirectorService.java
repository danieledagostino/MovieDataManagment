package org.liverpool.movie.managment.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.criterion.Restrictions;
import org.liverpool.movie.managment.model.Director;
import org.liverpool.movie.managment.repository.DirectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DirectorService implements IGenericCrud<Director> {

	@Autowired
	DirectorRepository repository;
	
	@PersistenceContext
    private EntityManager em;

	public boolean insert(Director director) {
		Director d = repository.save(director);

		return (d != null);
	}

	public void delete(Director director) {
		repository.delete(director);
	}
	
	public List<Director> searchByName(String name){
		return repository.searchByName(name);
	}
}
