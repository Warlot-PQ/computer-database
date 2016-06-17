package com.excilys.service;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.Pagination.Page;
import com.excilys.Pagination.PageRequest;
import com.excilys.bean.Computer;
import com.excilys.bean.ComputerDTO;
import com.excilys.cache.Cache;
import com.excilys.exception.ConnectionException;
import com.excilys.exception.DriverException;
import com.excilys.service.interfaces.ComputerDAO;
import com.excilys.service.interfaces.ComputerService;

/**
 * Service to manipulate Company table. Use cache data if possible.
 * Singleton class.
 * 
 * @author pqwarlot
 *
 */
@Service("computerService")
@Scope("singleton")
public class ComputerServiceImpl implements ComputerService {
	private static Logger logger = LoggerFactory.getLogger(ComputerServiceImpl.class);
	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private Cache cache;

	/**
	 * Contruct a ComputerService object to access ComputerDAO
	 */
	public ComputerServiceImpl() {
	}

	@Override
	public List<ComputerDTO> getAll() {
		return computerDAO.findAll(PageRequest.create().build());
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
		return computerDAO.findAll(pageRequest);
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
		List<ComputerDTO> computers = computerDAO.findAll(PageRequest.create().computerId(id).build());

		if (computers != null && computers.size() > 0) {
			// If one or multiple matche
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
		computerDAO.create(computer);
		cache.incrementCount(1);
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
		computerDAO.updateById(computer);
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
		computerDAO.delete(id);
		cache.decrementCount(1);
	}

	@Override
	public int count() {
		Integer computersNumber = cache.getCountComputer(); 
		
		if (computersNumber == null) {
			// If value not already in cache
			computersNumber = computerDAO.count(PageRequest.create().build());
		}		
		return computersNumber;
	}

	@Override
	public void deleteByCompany(Long id) {
		throw new UnsupportedClassVersionError();
		// update cache once implemented
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Page<ComputerDTO> getPage(PageRequest pageRequest) {
		List<ComputerDTO> computers = null;
		Integer computersNumber = 0;
		
		if (pageRequest.getComputerSearchedName() == null || pageRequest.getComputerSearchedName().isEmpty()) {
			// If not in search mode
			computersNumber = count();
		} else {
			// Count the searched elements founded
			computersNumber = computerDAO.count(pageRequest);			
		}
		computers = computerDAO.findAll(pageRequest);

		return new Page<ComputerDTO>(computers, computersNumber, pageRequest.getPage().intValue(), pageRequest.getEltByPage().intValue());
	}
}
