package org.liverpool.movie.managment.service;

import org.liverpool.movie.managment.component.Messages;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Provide CRUD function for T object
 * 
 * <pre>
 * {@code
 * public class ServiceClass implements IGenericCrud&lt;T&gt; {
 * 
 * }
 * </pre>
 * 
 * @author daniele.dagostino
 *
 * @param generic bean object
 */
public interface IGenericCrud<T> {
	
	/**
	 * This method insert a new object of type T
	 * 
	 *  @param beanApi the object
	 *  @return It returns true if the returned Movie object will successfully inserted
	 */
	public boolean insert(T beanApi);

	/**
	 * This method delete an object of type T
	 * 
	 *  @param id the identifier of this object
	 *  @throws It throws an {@link IllegalArgumentException} in case the given id is null or zero
	 */
	public void delete(Integer id) throws IllegalArgumentException;
	
	/**
	 * This method search a T through its identifier
	 * 
	 *  @param id the identifier of this object
	 *  @return It returns an object of type T
	 */
	public T getById(Integer id);
}
