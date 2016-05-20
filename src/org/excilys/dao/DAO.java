package org.excilys.dao;

import java.util.List;

import org.excilys.exceptions.ConnectionException;
import org.excilys.exceptions.DAOException;
import org.excilys.exceptions.DriverException;

/**
 * Generic DAO, main features to access DB
 * @author pqwarlot
 *
 * @param <T>
 */
public interface DAO<T> {	
	public List<T> findAll() throws DAOException, ConnectionException, DriverException;
	
	public T findById(Long id) throws DAOException, ConnectionException, DriverException;
	
	public void create(T obj) throws DAOException, ConnectionException, DriverException;
	
	public void updateById(T obj) throws DAOException, ConnectionException, DriverException;
	
	public void delete(Long id) throws DAOException, ConnectionException, DriverException;
}
