package org.excilys.service;

import java.util.List;

import org.excilys.beans.Company;
import org.excilys.dao.CompanyDAO;
import org.excilys.dao.DAO;
import org.excilys.exceptions.ConnectionException;
import org.excilys.exceptions.DAOException;
import org.excilys.exceptions.DriverException;

/**
 * Service to manipulate Computer table
 * @author pqwarlot
 *
 */
public class CompanyService {
	private static CompanyService instance = null;
	private DAO<Company> companyDAO = CompanyDAO.getInstance();
	
	public static CompanyService getInstance() {
		if (instance == null) {
			synchronized (CompanyService.class) {
				if (instance == null) {
					instance = new CompanyService();
				}
			}
		}
		return instance;
	}
	
	/**
	 * Contruct a ComputerService object to access ComputerDAO
	 */
	private CompanyService() {
	}
	
	/**
	 * Get all companies informations
	 * @return all companies
	 * @throws DriverException 
	 * @throws ConnectionException 
	 * @throws DAOException 
	 */
	public List<Company> allCompany() throws DAOException, ConnectionException, DriverException {
		return companyDAO.findAll();
	}
	
	/**
	 * Get a companie informations
	 * @return all companies
	 * @throws DriverException 
	 * @throws ConnectionException 
	 * @throws DAOException 
	 */
	public Company getCompany(Long id) throws DAOException, ConnectionException, DriverException {
		return companyDAO.findById(id);
	}
}
