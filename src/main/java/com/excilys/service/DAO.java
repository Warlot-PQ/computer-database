package com.excilys.service;

import java.util.List;

import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;

/**
 * Generic DAO, main features to access DB
 * 
 * @author pqwarlot
 *
 * @param <T,
 *            E>
 */
public interface DAO<T, E> {
	public List<E> findAll() throws DAOException, ConnectionException, DriverException;

	public List<E> findFromTo(int offset, int limit) throws DAOException, ConnectionException, DriverException;

	public E findById(Long id) throws DAOException, ConnectionException, DriverException;

	public void create(T obj) throws DAOException, ConnectionException, DriverException;

	public void updateById(T obj) throws DAOException, ConnectionException, DriverException;

	public void delete(Long id) throws DAOException, ConnectionException, DriverException;

	public int getRowNumber() throws DAOException, ConnectionException, DriverException;
}
