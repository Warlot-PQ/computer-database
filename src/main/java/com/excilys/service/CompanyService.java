package com.excilys.service;

import java.sql.Connection;
import java.util.List;

import com.excilys.bean.CompanyDTO;
import com.excilys.db.CoManager;
import com.excilys.exception.ConnectionException;
import com.excilys.exception.DAOException;
import com.excilys.exception.DriverException;
import com.excilys.service.interfaces.DAOCompany;
import com.excilys.service.interfaces.DAOComputer;
import com.excilys.service.interfaces.ServiceCompany;

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
	public List<CompanyDTO> getAll() {
		return companyDAO.findAll();
	}

	@Override
	public List<CompanyDTO> getFromTo(int offset, int limit) {
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
	public CompanyDTO get(Long id) {
		if (id == null) {
			return null;
		}
		if (id <= 0) {
			return null;
		}
		return companyDAO.findById(id);
	}

	@Override
	public void delete(Long id) {
		Connection con = CoManager.getInstance().getConnection();
		CoManager.beginTransaction(con);
		
		computerDAO.deleteByCompany(id, con);
		companyDAO.delete(id, con);
		
		CoManager.endTransaction(con);
		CoManager.cleanup(con, null, null);
	}

	@Override
	public int count() {
		return companyDAO.getRowNumber();
	}
}
