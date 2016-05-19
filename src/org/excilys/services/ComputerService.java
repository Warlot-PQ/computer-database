package org.excilys.services;

import java.sql.Timestamp;
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
	public Computer getComputer(long id) {
		return computerDAO.findById(id);
	}

	/**
	 * Create a new computer in DB, company id 0 means no company id.
	 * 
	 * @param computer
	 *            Computer object to store in DB
	 * @return computer wanted, empty computer object if error
	 */
	public Computer createComputer(Computer computer) {
		// Test introduced > discontinued if introduced and discontinued are setted
		if ( (computer.getIntroduced().equals(new Timestamp(0)) == false && computer.getDiscontinued().equals(new Timestamp(0)) == false)		
				&& (computer.getIntroduced().after(computer.getDiscontinued())) ){
			System.out.println("Date error, introduced greater than discontinued");
			return new Computer();
		}
		
		return computerDAO.create(computer);
	}

	/**
	 * Update computer from DB by id, company id 0 means no company id.
	 * 
	 * @param computer
	 *            Computer object to update in DB
	 * @return computer wanted, empty computer object if error
	 */
	public Computer updateComputer(Computer computer) {
		if (computer.getIntroduced().after(computer.getDiscontinued())){
			System.out.println("Date error, introduced greater than discontinued");
			return new Computer();
		}
		
		return computerDAO.updateById(computer);
	}

	/**
	 * Delete computer from DB by id
	 * 
	 * @param computer
	 *            Computer object to delete from DB
	 * @return true if deletion successful, false otherwise
	 */
	public boolean deleteComputer(long id) {
		return computerDAO.delete(id);
	}
}
