package com.excilys.cli;
import java.util.List;

import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;
import com.excilys.service.Service;

/**
 * Handle pagination for a list of entities
 * @author pqwarlot
 *
 * @param <T> Computer or Company object
 */
public class Page<T, E> { // Service must implement fetch(from, to)
	private static int eltByPage = 25;
	private int currentOffset = -eltByPage;
	private Service<T, E> service;

	public Page(Service<T, E> service) {
		this.service = service;
	}
	
	public List<E> nextPage() throws DAOException, ConnectionException, DriverException {
		currentOffset += eltByPage;
		return service.getAllFromTo(currentOffset, eltByPage);
	}
	
	/**
	 * Load previous entities page, please current page number before calling this method.
	 * @return List<Computer> or List<Company>, null or current page < 1
	 * @throws DAOException
	 * @throws ConnectionException
	 * @throws DriverException
	 */
	public List<E> previousPage() throws DAOException, ConnectionException, DriverException {
		if (getCurrentPage() < 1) {
			return null;
		}		
		currentOffset -= eltByPage;
		return service.getAllFromTo(currentOffset, eltByPage);
	}
	
	public List<E> refresh() throws DAOException, ConnectionException, DriverException {
		return service.getAllFromTo(currentOffset, eltByPage);
	}
	
	public int getCurrentPage() {
		return currentOffset / eltByPage;
	}
}
