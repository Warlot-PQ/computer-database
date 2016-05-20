package org.excilys.service;

import java.time.LocalDate;
import java.util.List;

import org.excilys.beans.Computer;
import org.excilys.dao.ComputerDAO;
import org.excilys.dao.DAO;
import org.excilys.exceptions.ConnectionException;
import org.excilys.exceptions.DAOException;
import org.excilys.exceptions.DriverException;

/**
 * Service to manipulate Campany table
 * 
 * @author pqwarlot
 *
 */
public class ComputerService {
	private static ComputerService instance = null;
	private DAO<Computer> computerDAO = ComputerDAO.getInstance();
	
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
	public List<Computer> allComputer() throws DAOException, ConnectionException, DriverException {
		return computerDAO.findAll();
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
	public Computer getComputer(Long id) throws DAOException, ConnectionException, DriverException {
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
	public boolean createComputer(Computer computer) throws DAOException, ConnectionException, DriverException {
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
	public boolean updateComputer(Computer computer) throws DAOException, ConnectionException, DriverException {
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
	public boolean deleteComputer(Long id) throws DAOException, ConnectionException, DriverException {
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
}
