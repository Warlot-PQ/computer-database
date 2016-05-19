package org.excilys.dao;

import java.util.List;

/**
 * Generic DAO, main features to access DB
 * @author pqwarlot
 *
 * @param <T>
 */
public interface DAO<T> {
	public List<T> findAll();
	
	public T findById(long id);
	
	public T create(T obj);
	
	public T updateById(T obj);
	
	public boolean delete(long id);
}
