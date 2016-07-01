package com.excilys.service.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.entity.Computer;
import com.excilys.persistence.pagination.PageRequest;
import com.excilys.persistence.repository.interfaces.ComputerDAO;
import com.excilys.service.cache.Cache;
import com.excilys.service.pagination.Page;
import com.excilys.service.service.interfaces.ComputerService;
import com.excilys.service.validation.ComputerValidation;

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
	@Transactional(readOnly = true, propagation=Propagation.REQUIRES_NEW)
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
	@Transactional(readOnly = true)
	public List<ComputerDTO> getAll(PageRequest pageRequest) {
		if (pageRequest == null) return null;
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
	@Transactional(readOnly = true)
	public ComputerDTO get(Long id) {
		if (id == null) return null;
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
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public ErrorMessage create(Computer computer) {
		if (computer == null) return null;
		if (ComputerValidation.date(computer) == false) {
			return ErrorMessage.COMPUTER_DATE_ERROR;
		}
		
		computerDAO.create(computer);
		cache.incrementCount(1);
		return ErrorMessage.NONE;
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
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public ErrorMessage update(Computer computer) {
		if (computer == null) return null;
		if (ComputerValidation.date(computer) == false) {
			return ErrorMessage.COMPUTER_DATE_ERROR;
		}
		
		computerDAO.updateById(computer);
		return ErrorMessage.NONE;
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
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void delete(Long id) {
		if (id == null) return;
		computerDAO.delete(id);
		cache.decrementCount(1);
	}

	@Override
	@Transactional(readOnly = true)
	public int count() {
		Integer computersNumber = cache.getCountComputer(); 
		
		if (computersNumber == null) {
			// If value not already in cache
			computersNumber = computerDAO.count(PageRequest.create().build());
		}		
		return computersNumber;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void deleteByCompany(Long id) {
		if (id == null) return;
		throw new UnsupportedClassVersionError();
		// update cache once implemented
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public Page<ComputerDTO> getPage(PageRequest pageRequest) {
		if (pageRequest == null) return null;
		List<ComputerDTO> computers = null;
		Integer computersNumber = 0;
		
		if (pageRequest.getSearchedName() == null || pageRequest.getSearchedName().isEmpty()) {
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
