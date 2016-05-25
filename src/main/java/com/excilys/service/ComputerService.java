package com.excilys.service;

import java.time.LocalDate;
import java.util.List;

import com.excilys.beans.Computer;
import com.excilys.beans.ComputerDTO;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;

/**
 * Service to manipulate Campany table
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
	public List<ComputerDTO> getFromTo(int offset, int limit) throws DAOException, ConnectionException, DriverException {
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
	 * Create a new computer in DB from computer object in parameter. No computer object field required.
	 * 
	 * @param computer
	 *            Computer object to store in DB
	 * @return boolean true if introduced > discontinued or both null, false, otherwise
	 * @throws DAOException 
	 * @throws DriverException 
	 * @throws ConnectionException 
	 */
	@Override
	public boolean create(Computer computer) throws DAOException, ConnectionException, DriverException {
		if (checkIntroducedAndDiscontinuedDate(computer.getIntroduced(), computer.getDiscontinued()) == false){
			return false;
		}
		computerDAO.create(computer);
		return true;
	}

	/**
	 * Update a new computer in DB from computer object in parameter. No computer object field required.
	 * 
	 * @param computer
	 *            Computer object to update in DB
	 * @return boolean true if introduced > discontinued or both null, false, otherwise
	 * @throws DAOException 
	 * @throws DriverException 
	 * @throws ConnectionException 
	 */
	@Override
	public boolean update(Computer computer) throws DAOException, ConnectionException, DriverException {
		if (checkIntroducedAndDiscontinuedDate(computer.getIntroduced(), computer.getDiscontinued()) == false){
			return false;
		}
		computerDAO.updateById(computer);
		return true;
	}

	/**
	 * Delete computer from DB by id
	 * 
	 * @param computer
	 *            Computer object to delete from DB
	 * @return boolean true if id < 0, false otherwise
	 * @throws DAOException 
	 * @throws DriverException 
	 * @throws ConnectionException 
	 */
	@Override
	public boolean delete(Long id) throws DAOException, ConnectionException, DriverException {
		if (id == null) {
			return false;
		}
		if (id <= 0) {
			return false;
		}
		computerDAO.delete(id);
		return true;
	}
	
	private boolean checkIntroducedAndDiscontinuedDate(LocalDate introduced, LocalDate discontinued){
		if (introduced != null || discontinued != null) {
			// introduced or discontinued not null, validation needed
			if (introduced != null && discontinued.toString().matches("\\d{4}-\\d{2}-\\d{2}") == false) {
				// Check discontinued date pattern YYYY-MM-dd
				return false;
			}
			if (introduced != null && introduced.toString().matches("\\d{4}-\\d{2}-\\d{2}") == false) {
				// Check introduced date pattern YYYY-MM-dd
				return false;
			}
			if ( (introduced != null && discontinued != null)		
					&& (introduced.isAfter(discontinued)) ) {
				// Test introduced > discontinued if introduced and discontinued are setted
				return false;
			}
		} 
		return true;
	}

	@Override
	public int count() throws DAOException, ConnectionException, DriverException {
		return computerDAO.getRowNumber();
	}
}
