package org.liverpool.movie.managment.service;

public interface IGenericCrud<T> {

	public boolean insert(T t);

	public void delete(T t);
}
