package org.liverpool.movie.managment.service;

public interface IGenericCrud<T> {

	public boolean insert(T t);

	public void delete(Integer id) throws IllegalArgumentException;
	
	public T getById(Integer id);
}
