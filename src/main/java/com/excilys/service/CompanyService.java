package com.excilys.service;

import java.util.List;

import com.excilys.beans.Company;
import com.excilys.beans.CompanyDTO;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;

/**
 * Service to manipulate Computer table
 * 
 * @author pqwarlot
 *
 */
public class CompanyService implements ServiceCompany {
	private static CompanyService instance = null;
	private DAOCompany companyDAO = CompanyDAO.getInstance();
	private DAOComputer computerDAO = ComputerDAO.getInstance();

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
	 * 
	 * @return all companies
	 * @throws DriverException
	 * @throws ConnectionException
	 * @throws DAOException
	 */
	@Override
	public List<CompanyDTO> getAll() throws DAOException, ConnectionException, DriverException {
		return companyDAO.findAll();
	}

	@Override
	public List<CompanyDTO> getFromTo(int offset, int limit) throws DAOException, ConnectionException, DriverException {
		if (offset < 0 || limit <= 0) {
			return null;
		}
		return companyDAO.findFromTo(offset, limit);
	}

	/**
	 * Get a companie informations
	 * 
	 * @return all companies
	 * @throws DriverException
	 * @throws ConnectionException
	 * @throws DAOException
	 */
	@Override
	public CompanyDTO get(Long id) throws DAOException, ConnectionException, DriverException {
		if (id == null) {
			return null;
		}
		if (id <= 0) {
			return null;
		}
		return companyDAO.findById(id);
	}

	@Override
	public void create(Company obj) throws DAOException, ConnectionException, DriverException {
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(Long id) throws DAOException, ConnectionException, DriverException {
		computerDAO.deleteByCompany(id);
		companyDAO.delete(id);
	}

	@Override
	public void update(Company obj) throws DAOException, ConnectionException, DriverException {
		// TODO Auto-generated method stub
	}

	@Override
	public int count() throws DAOException, ConnectionException, DriverException {
		return companyDAO.getRowNumber();
	}
}
