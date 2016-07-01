package com.excilys.service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.core.dto.CompanyDTO;
import com.excilys.core.entity.Company;
import com.excilys.persistence.pagination.PageRequest;
import com.excilys.persistence.repository.interfaces.CompanyDAO;
import com.excilys.persistence.repository.interfaces.ComputerDAO;
import com.excilys.service.cache.Cache;
import com.excilys.service.pagination.Page;
import com.excilys.service.service.interfaces.CompanyService;

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
	@Transactional(readOnly = true, propagation=Propagation.REQUIRES_NEW)
	public int count() {
		return companyDAO.getRowNumber();
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
	public void delete(Long id) {	
		if (id == null) return;		
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
	@Transactional(readOnly = true, propagation=Propagation.REQUIRES_NEW)
	public CompanyDTO get(Long id) {
		if (id == null) return null;
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
	@Transactional(readOnly = true, propagation=Propagation.REQUIRES_NEW)
	public List<CompanyDTO> getAll() {
		List<CompanyDTO> companies = null;
		
		companies = cache.getCompanies();
		if (companies == null) {
			// If cached value not available
			companies = companyDAO.findAll(PageRequest.create().build());
			cache.setCompanies(companies);
		}
		return companies;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void create(Company obj) {
		if (obj == null) return;
		companyDAO.create(obj);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void update(Company obj) {
		if (obj == null) return;
		companyDAO.updateById(obj);
	}

	@Override
	@Transactional(rollbackFor = Exception.class, propagation=Propagation.REQUIRES_NEW)
	public Page<CompanyDTO> getPage(PageRequest pageRequest) {
		if (pageRequest == null) return null;
		List<CompanyDTO> companies = null;
		Integer companiesNumber = 0;
		
		if (pageRequest.getSearchedName() == null || pageRequest.getSearchedName().isEmpty()) {
			// If not in search mode
			companiesNumber = count();
		} else {
			// Count the searched elements founded
			companiesNumber = companyDAO.count(pageRequest);			
		}
		companies = companyDAO.findAll(pageRequest);

		return new Page<CompanyDTO>(companies, companiesNumber, pageRequest.getPage().intValue(), pageRequest.getEltByPage().intValue());
	}
}