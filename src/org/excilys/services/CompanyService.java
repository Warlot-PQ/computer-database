package org.excilys.services;

import java.util.List;

import org.excilys.beans.Company;
import org.excilys.dao.DAO;
import org.excilys.dao.DAOFactory;

/**
 * Service to manipulate Computer table
 * @author pqwarlot
 *
 */
public class CompanyService {
private DAO<Company> companyDAO = null;

	/**
	 * Contruct a ComputerService object to access ComputerDAO
	 */
	public CompanyService() {
		this.companyDAO = DAOFactory.getCompanyDAO();
	}
	
	/**
	 * Get all companies informations
	 * @return all companies
	 */
	public List<Company> allCompany() {
		return companyDAO.findAll();
	}
}
