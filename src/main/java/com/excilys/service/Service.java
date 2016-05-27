package com.excilys.service;

import java.util.List;

import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;

/**
 * Generic services to all entities
 * @author pqwarlot
 *
 * @param <T>
 * @param <E>
 */
public interface Service<T, E> {
	public List<E> getAll() throws DAOException, ConnectionException, DriverException;

	public List<E> getFromTo(int offset, int limit) throws DAOException, ConnectionException, DriverException;

	public E get(Long id) throws DAOException, ConnectionException, DriverException;

	public void create(T obj) throws DAOException, ConnectionException, DriverException;

	public void update(T obj) throws DAOException, ConnectionException, DriverException;

	public void delete(Long id) throws DAOException, ConnectionException, DriverException;

	public int count() throws DAOException, ConnectionException, DriverException;
}