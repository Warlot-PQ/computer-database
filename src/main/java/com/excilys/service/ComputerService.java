package com.excilys.service;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.Pagination.Page;
import com.excilys.bean.Computer;
import com.excilys.bean.ComputerDTO;
import com.excilys.db.ConnectionLocal;
import com.excilys.exception.ConnectionException;
import com.excilys.exception.DAOException;
import com.excilys.exception.DriverException;
import com.excilys.service.interfaces.DAOComputer;
import com.excilys.service.interfaces.ServiceComputer;

/**
 * Service to manipulate Campany table.
 * 
 * @author pqwarlot
 *
 */
public class ComputerService implements ServiceComputer {
	private static Logger LOGGER = null;
	private static ComputerService INSTANCE = new ComputerService();
	private static DAOComputer COMPUTER_DAO = ComputerDAO.getInstance();
	private static ConnectionLocal LOCAL_CONNECTION = ConnectionLocal.getInstance();

	/**
	 * Contruct a ComputerService object to access ComputerDAO
	 */
	private ComputerService() {
		LOGGER = LoggerFactory.getLogger(this.getClass());
	}

	public static ComputerService getInstance() {
		return INSTANCE;
	}

	@Override
	public List<ComputerDTO> getAll() {
		List<ComputerDTO> computers = null;
		LOCAL_CONNECTION.newConnection();
		try {
			computers = COMPUTER_DAO.findAll(PageRequest.create().build());
		} catch (DAOException e) {
			LOGGER.error("GetAll with request error", e);
		} finally {
			LOCAL_CONNECTION.cleanup();
		}
		return computers;
	}
	
	/**
	 * Get all computers informations
	 * 
	 * @return all companies
	 * @throws DAOException
	 * @throws DriverException
	 * @throws ConnectionException
	 */
	@Override
	public List<ComputerDTO> getAll(PageRequest pageRequest) {
		List<ComputerDTO> computers = null;
		LOCAL_CONNECTION.newConnection();
		try {
			computers = COMPUTER_DAO.findAll(pageRequest);
		} catch (DAOException e) {
			LOGGER.error("GetAll error", e);
		} finally {
			LOCAL_CONNECTION.cleanup();
		}
		return computers;
	}
	
	/**
	 * Get informations from a specific computer
	 * 
	 * @param id
	 *            id of the requested computer
	 * @return computer wanted, empty computer object if error
	 * @throws DAOException
	 * @throws DriverException
	 * @throws ConnectionException
	 */
	@Override
	public ComputerDTO get(Long id) {
		List<ComputerDTO> computers = null;
		LOCAL_CONNECTION.newConnection();
		try {
			computers = COMPUTER_DAO.findAll(PageRequest.create().computerId(id).build());
		} catch (DAOException e) {
			LOGGER.error("Get error", e);
		} finally {
			LOCAL_CONNECTION.cleanup();
		}
		if (computers != null && computers.size() > 0) {
			return computers.get(0);
		}
		return null;
	}

	/**
	 * Create a new computer in DB from computer object in parameter. No
	 * computer object field required.
	 * 
	 * @param computer
	 *            Computer object to store in DB
	 * @return boolean true if introduced > discontinued or both null, false,
	 *         otherwise
	 * @throws DAOException
	 * @throws DriverException
	 * @throws ConnectionException
	 */
	@Override
	public void create(Computer computer) {
		LOCAL_CONNECTION.newConnection();
		try {
			COMPUTER_DAO.create(computer);
		} catch (DAOException e) {
			LOGGER.error("Create error", e);
		} finally {
			LOCAL_CONNECTION.cleanup();
		}
	}

	/**
	 * Update a new computer in DB from computer object in parameter. No
	 * computer object field required.
	 * 
	 * @param computer
	 *            Computer object to update in DB
	 * @return boolean true if introduced > discontinued or both null, false,
	 *         otherwise
	 * @throws DAOException
	 * @throws DriverException
	 * @throws ConnectionException
	 */
	@Override
	public void update(Computer computer) {
		LOCAL_CONNECTION.newConnection();
		try {
			COMPUTER_DAO.updateById(computer);
		} catch (DAOException e) {
			LOGGER.error("Update error", e);
		} finally {
			LOCAL_CONNECTION.cleanup();
		}
	}

	/**
	 * dd-MM-yyyy Delete computer from DB by id
	 * 
	 * @param computer
	 *            Computer object to delete from DB
	 * @return boolean true if id < 0, false otherwise
	 * @throws DAOException
	 * @throws DriverException
	 * @throws ConnectionException
	 */
	@Override
	public void delete(Long id) {
		LOCAL_CONNECTION.newConnection();
		try {
			COMPUTER_DAO.delete(id);
		} catch (DAOException e) {
			LOGGER.error("Delete error", e);
		} finally {
			LOCAL_CONNECTION.cleanup();
		}
	}

	@Override
	public int count() {
		int count = 0;
		LOCAL_CONNECTION.newConnection();
		try {
			count = COMPUTER_DAO.countAll();
		} catch (DAOException e) {
			LOGGER.error("Count error", e);
		}
		finally {
			LOCAL_CONNECTION.cleanup();
		}
		return count;
	}

	@Override
	public void deleteByCompany(Long id) {
		throw new UnsupportedClassVersionError();
	}

	@Override
	public Page<ComputerDTO> getPage(PageRequest pageRequest) {
		List<ComputerDTO> computers = null;
		int computersNumber = 0;
		
		LOCAL_CONNECTION.beginTransaction();
		
		try {
			if (pageRequest.getComputerSearchedName() == null || pageRequest.getComputerSearchedName().isEmpty()) {
				computersNumber = COMPUTER_DAO.countAll();
			} else {
				computersNumber = COMPUTER_DAO.count(pageRequest);			
			}
			computers = COMPUTER_DAO.findAll(pageRequest);
		} catch (DAOException e) {
			LOGGER.error("DAO error", e);
			LOCAL_CONNECTION.rollBack();
			LOCAL_CONNECTION.endTransaction();
		}
		
		LOCAL_CONNECTION.endTransaction();

		return new Page<ComputerDTO>(computers, computersNumber, pageRequest.getPage().intValue(), pageRequest.getEltByPage().intValue());
	}
}
