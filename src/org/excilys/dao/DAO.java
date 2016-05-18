package org.excilys.dao;

import java.util.List;

/**
 * Generic DAO, main features to access DB
 * @author pqwarlot
 *
 * @param <T>
 */
public interface DAO<T> {
	public abstract List<T> findAll();
	
	public abstract T find(int id);
	
	public abstract T create(T obj);
	
	public abstract T update(T obj);
	
	public abstract boolean delete(T obj);
}
