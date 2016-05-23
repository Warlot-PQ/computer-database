package com.excilys.service;

import java.util.List;

import com.excilys.beans.Company;
import com.excilys.beans.CompanyExt;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;

/**
 * Service to manipulate Computer table
 * @author pqwarlot
 *
 */
public class CompanyService implements Service<Company, CompanyExt> {
	private static CompanyService instance = null;
	private DAO<Company, CompanyExt> companyDAO = CompanyDAO.getInstance();
	
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
	@Override
	public List<CompanyExt> getAll() throws DAOException, ConnectionException, DriverException {
		return companyDAO.findAll();
	}
	
	@Override
	public List<CompanyExt> getAllFromTo(int offset, int limit) throws DAOException, ConnectionException, DriverException {
		if (offset < 0 || limit <= 0) {
			return null;
		}
		return companyDAO.findAllFromTo(offset, limit);
	}
	
	/**
	 * Get a companie informations
	 * @return all companies
	 * @throws DriverException 
	 * @throws ConnectionException 
	 * @throws DAOException 
	 */
	@Override
	public CompanyExt get(Long id) throws DAOException, ConnectionException, DriverException {
		if (id == null) {
			return null;
		}
		if (id <= 0) {
			return null;
		}
		return companyDAO.findById(id);
	}
	
	@Override
	public boolean create(Company obj) throws DAOException, ConnectionException, DriverException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean update(Company obj) throws DAOException, ConnectionException, DriverException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean delete(Long id) throws DAOException, ConnectionException, DriverException {
		// TODO Auto-generated method stub
		return false;
	}
}
