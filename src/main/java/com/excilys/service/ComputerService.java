package com.excilys.service;

import java.util.List;

import com.excilys.beans.Computer;
import com.excilys.beans.ComputerDTO;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;

/**
 * Service to manipulate Campany table.
 * 
 * @author pqwarlot
 *
 */
public class ComputerService implements Service<Computer, ComputerDTO> {
	private static ComputerService instance = null;
	private DAO<Computer, ComputerDTO> computerDAO = ComputerDAO.getInstance();

	public static ComputerService getInstance() {
		if (instance == null) {
			synchronized (ComputerService.class) {
				if (instance == null) {
					instance = new ComputerService();
				}
			}
		}
		return instance;
	}

	/**
	 * Contruct a ComputerService object to access ComputerDAO
	 */
	private ComputerService() {
	}

	/**
	 * Get all computers informations
	 * 
	 * @return all companies
	 * @throws DAOException
	 * @throws DriverException
	 * @throws ConnectionException
	 */
	@Override
	public List<ComputerDTO> getAll() throws DAOException, ConnectionException, DriverException {
		return computerDAO.findAll();
	}

	@Override
	public List<ComputerDTO> getFromTo(int offset, int limit)
			throws DAOException, ConnectionException, DriverException {
		if (offset < 0 || limit <= 0) {
			return null;
		}
		return computerDAO.findFromTo(offset, limit);
	}

	/**
	 * Get informations from a specific computer
	 * 
	 * @param id
	 *            id of the requested computer
	 * @return computer wanted, empty computer object if error
	 * @throws DAOException
	 * @throws DriverException
	 * @throws ConnectionException
	 */
	@Override
	public ComputerDTO get(Long id) throws DAOException, ConnectionException, DriverException {
		if (id == null) {
			return null;
		}
		if (id <= 0) {
			return null;
		}
		return computerDAO.findById(id);
	}

	/**
	 * Create a new computer in DB from computer object in parameter. No
	 * computer object field required.
	 * 
	 * @param computer
	 *            Computer object to store in DB
	 * @return boolean true if introduced > discontinued or both null, false,
	 *         otherwise
	 * @throws DAOException
	 * @throws DriverException
	 * @throws ConnectionException
	 */
	@Override
	public void create(Computer computer) throws DAOException, ConnectionException, DriverException {
		computerDAO.create(computer);
	}

	/**
	 * Update a new computer in DB from computer object in parameter. No
	 * computer object field required.
	 * 
	 * @param computer
	 *            Computer object to update in DB
	 * @return boolean true if introduced > discontinued or both null, false,
	 *         otherwise
	 * @throws DAOException
	 * @throws DriverException
	 * @throws ConnectionException
	 */
	@Override
	public void update(Computer computer) throws DAOException, ConnectionException, DriverException {
		computerDAO.updateById(computer);
	}

	/**
	 * dd-MM-yyyy Delete computer from DB by id
	 * 
	 * @param computer
	 *            Computer object to delete from DB
	 * @return boolean true if id < 0, false otherwise
	 * @throws DAOException
	 * @throws DriverException
	 * @throws ConnectionException
	 */
	@Override
	public void delete(Long id) throws DAOException, ConnectionException, DriverException {
		computerDAO.delete(id);
	}

	@Override
	public int count() throws DAOException, ConnectionException, DriverException {
		return computerDAO.getRowNumber();
	}
}
