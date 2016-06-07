package com.excilys.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.bean.CompanyDTO;
import com.excilys.cache.Cache;
import com.excilys.db.ConnectionLocal;
import com.excilys.exception.ConnectionException;
import com.excilys.exception.DAOException;
import com.excilys.exception.DriverException;
import com.excilys.service.interfaces.DAOCompany;
import com.excilys.service.interfaces.DAOComputer;
import com.excilys.service.interfaces.ServiceCompany;

/**
 * Service to manipulate Computer table.  Use cache data if possible.
 * Singleton class.
 * 
 * @author pqwarlot
 *
 */
public class CompanyService implements ServiceCompany {
	private static Logger logger = null;
	private static CompanyService instance = new CompanyService();
	private static DAOCompany companyDAO = CompanyDAO.getInstance();
	private static DAOComputer computerDAO = ComputerDAO.getInstance();
	private static ConnectionLocal localConnection = ConnectionLocal.getInstance();
	private static Cache cache = Cache.getInstance();

	private CompanyService() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	public static CompanyService getInstance() {
		return instance;
	}

	/**
	 * Get all companies informations, use cached values if possible
	 * 
	 * @return all companies as list of CompanyDTO
	 * @throws DriverException
	 * @throws ConnectionException
	 * @throws DAOException
	 */
	@Override
	public List<CompanyDTO> getAll() {
		List<CompanyDTO> companies = null;
		
		companies = cache.getCompanies();
		if (companies == null) {
			// If cached value not available
			localConnection.newConnection();
			try {
				companies = companyDAO.findAll();
			} catch (DAOException e) {
				logger.error("FindAll error", e);
			} finally {
				localConnection.cleanup();
			}
			cache.setCompanies(companies);
		}
		return companies;
	}

	@Override
	public List<CompanyDTO> getFromTo(int offset, int limit) {
		List<CompanyDTO> companies = null;
		localConnection.newConnection();
		if (offset < 0 || limit <= 0) {
			return null;
		}
		try {
			companies = companyDAO.findFromTo(offset, limit);
		} catch (DAOException e) {
			logger.error("getFromTo error", e);
		} finally {
			localConnection.cleanup();
		}
		return companies;
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
		CompanyDTO company = null;
		localConnection.newConnection();
		try {
			company = companyDAO.findById(id);
		} catch (DAOException e) {
			logger.error("Get error", e);
		} finally {
			localConnection.cleanup();
		}
		return company;
	}

	@Override
	public void delete(Long id) {
		localConnection.beginTransaction();
		
		try {
			computerDAO.deleteByCompany(id);
			companyDAO.delete(id);
		} catch (DAOException e) {
			logger.error("DAO error", e);
			localConnection.rollBack();
			localConnection.endTransaction();
		}
		
		cache.flushCompanies();
		
		localConnection.endTransaction();
	}

	@Override
	public int count() {
		int count = 0;
		localConnection.beginTransaction();
		try {
			count = companyDAO.getRowNumber();
		} catch (DAOException e) {
			logger.error("Count error", e);
		} finally {
			localConnection.cleanup();
		}
		return count;
	}
}
