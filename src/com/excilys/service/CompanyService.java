package com.excilys.service;

import java.util.List;

import com.excilys.beans.Company;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;

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
