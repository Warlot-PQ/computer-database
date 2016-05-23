package com.excilys.service;

import java.util.List;

import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;

public interface Service<T, E> {
	public List<E> getAll() throws DAOException, ConnectionException, DriverException;
	
	public List<E> getAllFromTo(int offset, int limit) throws DAOException, ConnectionException, DriverException;
	
	public E get(Long id) throws DAOException, ConnectionException, DriverException;
	
	public boolean create(T obj) throws DAOException, ConnectionException, DriverException;
	
	public boolean update(T obj) throws DAOException, ConnectionException, DriverException;
	
	public boolean delete(Long id) throws DAOException, ConnectionException, DriverException;
}