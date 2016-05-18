package org.excilys.services;

import java.util.List;

import org.excilys.beans.*;
import org.excilys.dao.*;

/**
 * Service to manipulate Campany table
 * 
 * @author pqwarlot
 *
 */
public class ComputerService {
	private DAO<Computer> computerDAO = null;

	/**
	 * Contruct a ComputerService object to access ComputerDAO
	 */
	public ComputerService() {
		this.computerDAO = DAOFactory.getComputerDAO();
	}

	/**
	 * Get all computers informations
	 * 
	 * @return all companies
	 */
	public List<Computer> allComputer() {
		return computerDAO.findAll();
	}

	/**
	 * Get informations from a specific computer
	 * 
	 * @param id
	 *            id of the requested computer
	 * @return computer wanted, empty computer object if error
	 */
	public Computer getComputer(int id) {
		return computerDAO.find(id);
	}

	/**
	 * Create a new computer in DB
	 * 
	 * @param computer
	 *            Computer object to store in DB
	 * @return computer wanted, empty computer object if error
	 */
	public Computer createComputer(Computer computer) {
		return computerDAO.create(computer);
	}

	/**
	 * Update computer from DB by id
	 * 
	 * @param computer
	 *            Computer object to update in DB
	 * @return computer wanted, empty computer object if error
	 */
	public Computer updateComputer(Computer computer) {
		return computerDAO.update(computer);
	}

	/**
	 * Delete computer from DB by id
	 * 
	 * @param computer
	 *            Computer object to delete from DB
	 * @return true if deletion successful, false otherwise
	 */
	public boolean deleteComputer(Computer computer) {
		return computerDAO.delete(computer);
	}
}
