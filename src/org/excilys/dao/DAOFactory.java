package org.excilys.dao;

import org.excilys.beans.Company;
import org.excilys.beans.Computer;

/**
 * Factory generating DAO to access different tables DB
 * 
 * @author pqwarlot
 *
 */
public class DAOFactory {
	/**
	 * Get ComputerDAO
	 * 
	 * @return ComputerDAO
	 */
	public static DAO<Computer> getComputerDAO() {
		return new ComputerDAO();
	}

	/**
	 * Get CompanyDAO
	 * 
	 * @return CompanyDAO
	 */
	public static DAO<Company> getCompanyDAO() {
		return new CompanyDAO();
	}
}
