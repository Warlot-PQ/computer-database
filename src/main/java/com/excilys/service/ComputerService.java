package com.excilys.service;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.Pagination.Page;
import com.excilys.bean.Computer;
import com.excilys.bean.ComputerDTO;
import com.excilys.cache.Cache;
import com.excilys.db.ConnectionLocal;
import com.excilys.exception.ConnectionException;
import com.excilys.exception.DAOException;
import com.excilys.exception.DriverException;
import com.excilys.service.interfaces.DAOComputer;
import com.excilys.service.interfaces.ServiceComputer;

/**
 * Service to manipulate Company table. Use cache data if possible.
 * Singleton class.
 * 
 * @author pqwarlot
 *
 */
public class ComputerService implements ServiceComputer {
	private static Logger logger = null;
	private static ComputerService instance = new ComputerService();
	private static DAOComputer computerDAO = ComputerDAO.getInstance();
	private static ConnectionLocal localConnection = ConnectionLocal.getInstance();
	private static Cache cache = Cache.getInstance();

	/**
	 * Contruct a ComputerService object to access ComputerDAO
	 */
	private ComputerService() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	public static ComputerService getInstance() {
		return instance;
	}

	@Override
	public List<ComputerDTO> getAll() {
		List<ComputerDTO> computers = null;
		localConnection.newConnection();
		try {
			computers = computerDAO.findAll(PageRequest.create().build());
		} catch (DAOException e) {
			logger.error("GetAll with request error", e);
		} finally {
			localConnection.cleanup();
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
		localConnection.newConnection();
		try {
			computers = computerDAO.findAll(pageRequest);
		} catch (DAOException e) {
			logger.error("GetAll error", e);
		} finally {
			localConnection.cleanup();
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
		localConnection.newConnection();
		try {
			computers = computerDAO.findAll(PageRequest.create().computerId(id).build());
		} catch (DAOException e) {
			logger.error("Get error", e);
		} finally {
			localConnection.cleanup();
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
		int computerAdded = 0;
		
		localConnection.newConnection();
		try {
			computerDAO.create(computer);
			cache.incrementCount(1);
		} catch (DAOException e) {
			logger.error("Create error", e);
		} finally {
			localConnection.cleanup();
		}
		if (computerAdded > 0) {
			cache.incrementCount(computerAdded);
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
		localConnection.newConnection();
		try {
			computerDAO.updateById(computer);
		} catch (DAOException e) {
			logger.error("Update error", e);
		} finally {
			localConnection.cleanup();
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
		localConnection.newConnection();
		try {
			computerDAO.delete(id);
			cache.decrementCount(1);
		} catch (DAOException e) {
			logger.error("Delete error", e);
		} finally {
			localConnection.cleanup();
		}
	}

	@Override
	public int count() {
		Integer computersNumber = cache.getCountComputer(); 
		boolean connectionAlreadyStarted = false;
		
		if (computersNumber == null) {
			// If cached value not available
			if (localConnection.getConnection() != null) {
				// If transaction in progress
				connectionAlreadyStarted = true;
			} else {
				localConnection.newConnection();
			}
			
			try {
				computersNumber = computerDAO.count(PageRequest.create().build());
			} catch (DAOException e) {
				logger.error("Count error", e);
			}
			finally {
				if (connectionAlreadyStarted == false) {
					localConnection.cleanup();
				}
			}
		}		
		return computersNumber;
	}

	@Override
	public void deleteByCompany(Long id) {
		throw new UnsupportedClassVersionError();
		// update cache once implemented
	}

	@Override
	public Page<ComputerDTO> getPage(PageRequest pageRequest) {
		List<ComputerDTO> computers = null;
		Integer computersNumber = 0;
		
		localConnection.beginTransaction();
		
		try {
			if (pageRequest.getComputerSearchedName() == null || pageRequest.getComputerSearchedName().isEmpty()) {
				// If not in search mode
				computersNumber = count();
			} else {
				// Cound the searched elements
				computersNumber = computerDAO.count(pageRequest);			
			}
			computers = computerDAO.findAll(pageRequest);
		} catch (DAOException e) {
			logger.error("DAO error", e);
			localConnection.rollBack();
			localConnection.endTransaction();
		}
		
		localConnection.endTransaction();

		return new Page<ComputerDTO>(computers, computersNumber, pageRequest.getPage().intValue(), pageRequest.getEltByPage().intValue());
	}
}
