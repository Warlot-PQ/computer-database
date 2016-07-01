package com.excilys.service.service.interfaces;

import java.util.List;

import com.excilys.core.dto.CompanyDTO;
import com.excilys.core.entity.Company;
import com.excilys.persistence.pagination.PageRequest;
import com.excilys.service.pagination.Page;

/**
 * Specific services to company entities
 * @author pqwarlot
 *
 */
public interface CompanyService {
	/**
	 * Get all company
	 * @return a list of CompanyDTO object
	 */
	public List<CompanyDTO> getAll();

	/**
	 * Get a specific company
	 * @param id id of the company wanted
	 * @return CompanyDTO object
	 */
	public CompanyDTO get(Long id);

	/**
	 * Create a company
	 * @param obj Computer object to create
	 */
	public void create(Company obj);

	/**
	 * Delete a company
	 * @param id id of the company to delete
	 */
	public void delete(Long id);

	/**
	 * Update a company
	 * @param obj company to update as entity object
	 */
	public void update(Company obj);

	/**
	 * Get a page of DTO object
	 * @param pageRequest options to create the query to get DTO
	 * @return Page<CompanyDTO> page containing all informations
	 */
	public Page<CompanyDTO> getPage(PageRequest pageRequest);
	
	/**
	 * Count the number of company object available
	 * @return company number as int
	 */
	public int count();
}
