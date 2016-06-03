package com.excilys.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.bean.CompanyDTO;
import com.excilys.db.ConnectionLocal;
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
	private static Logger LOGGER = null;
	private static CompanyService INSTANCE = new CompanyService();
	private static DAOCompany COMPANY_DAO = CompanyDAO.getInstance();
	private static DAOComputer COMPUTER_DAO = ComputerDAO.getInstance();
	private static ConnectionLocal LOCAL_CONNECTION = ConnectionLocal.getInstance();

	private CompanyService() {
		LOGGER = LoggerFactory.getLogger(this.getClass());
	}

	public static CompanyService getInstance() {
		return INSTANCE;
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
		List<CompanyDTO> companies = null;
		LOCAL_CONNECTION.newConnection();
		try {
			companies = COMPANY_DAO.findAll();
		} catch (DAOException e) {
			LOGGER.error("FindAll error", e);
		} finally {
			LOCAL_CONNECTION.cleanup();
		}
		return companies;
	}

	@Override
	public List<CompanyDTO> getFromTo(int offset, int limit) {
		List<CompanyDTO> companies = null;
		LOCAL_CONNECTION.newConnection();
		if (offset < 0 || limit <= 0) {
			return null;
		}
		try {
			companies = COMPANY_DAO.findFromTo(offset, limit);
		} catch (DAOException e) {
			LOGGER.error("getFromTo error", e);
		} finally {
			LOCAL_CONNECTION.cleanup();
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
		LOCAL_CONNECTION.newConnection();
		try {
			company = COMPANY_DAO.findById(id);
		} catch (DAOException e) {
			LOGGER.error("Get error", e);
		} finally {
			LOCAL_CONNECTION.cleanup();
		}
		return company;
	}

	@Override
	public void delete(Long id) {
		LOCAL_CONNECTION.beginTransaction();
		
		try {
			COMPUTER_DAO.deleteByCompany(id);
			COMPANY_DAO.delete(id);
		} catch (DAOException e) {
			LOGGER.error("DAO error", e);
			LOCAL_CONNECTION.rollBack();
			LOCAL_CONNECTION.endTransaction();
		}
		
		LOCAL_CONNECTION.endTransaction();
	}

	@Override
	public int count() {
		int count = 0;
		LOCAL_CONNECTION.beginTransaction();
		try {
			count = COMPANY_DAO.getRowNumber();
		} catch (DAOException e) {
			LOGGER.error("Count error", e);
		} finally {
			LOCAL_CONNECTION.cleanup();
		}
		return count;
	}
}
