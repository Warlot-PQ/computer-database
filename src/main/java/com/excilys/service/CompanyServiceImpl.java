package com.excilys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.bean.CompanyDTO;
import com.excilys.cache.Cache;
import com.excilys.exception.ConnectionException;
import com.excilys.exception.DriverException;
import com.excilys.service.interfaces.CompanyDAO;
import com.excilys.service.interfaces.CompanyService;
import com.excilys.service.interfaces.ComputerDAO;

/**
 * Service to manipulate Computer table.  Use cache data if possible.
 * Singleton class.
 * 
 * @author pqwarlot
 *
 */
@Service("companyService")
@Scope("singleton")
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	private ComputerDAO computerDAO;
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private Cache cache;

	public CompanyServiceImpl() {
	}
	
	@Override
	public int count() {
		return companyDAO.getRowNumber();
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void delete(Long id) {			
		computerDAO.deleteByCompany(id);	
		companyDAO.delete(id);
		
		cache.flushCompanies();
	}

	/**
	 * Get a company informations
	 * 
	 * @return all companies
	 * @throws DriverException
	 * @throws ConnectionException
	 * @throws DAOException
	 */
	@Override
	public CompanyDTO get(Long id) {
		return companyDAO.findById(id);
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
			companies = companyDAO.findAll();
			cache.setCompanies(companies);
		}
		return companies;
	}
}