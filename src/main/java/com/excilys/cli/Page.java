package com.excilys.cli;

import java.util.List;

import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;
import com.excilys.service.Service;

/**
 * Handle pagination for a list of entities
 * 
 * @author pqwarlot
 *
 * @param T
 *            Computer or Company class
 * @param E
 *            ComputerExt or CompanyExt class
 */
public class Page<T, E> {
	private int eltByPage = 25;
	private int currentOffset = -eltByPage;
	private Service<T, E> service;

	public Page(Service<T, E> service) {
		this(service, 30);
	}

	public Page(Service<T, E> service, int eltByPage) {
		this.service = service;
		this.eltByPage = eltByPage;
	}

	public List<E> nextPage() throws DAOException, ConnectionException, DriverException {
		currentOffset += eltByPage;
		return service.getFromTo(currentOffset, eltByPage);
	}

	/**
	 * Load previous entities page, please current page number before calling
	 * this method.
	 * 
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
		return service.getFromTo(currentOffset, eltByPage);
	}

	public List<E> refresh() throws DAOException, ConnectionException, DriverException {
		return service.getFromTo(currentOffset, eltByPage);
	}

	public List<E> getPage(int pageNumber) throws DAOException, ConnectionException, DriverException {
		return service.getFromTo((pageNumber - 1) * eltByPage, eltByPage);
	}

	public int getCurrentPage() {
		return currentOffset / eltByPage;
	}

	public int getTotalPages() throws DAOException, ConnectionException, DriverException {
		return (int) Math.ceil(service.count() / (double) eltByPage);
	}
}
