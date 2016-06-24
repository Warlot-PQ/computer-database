package com.excilys.service.service.interfaces;

import java.util.List;

import com.excilys.core.dto.CompanyDTO;

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
	 * Delete a company
	 * @param id id of the company to delete
	 */
	public void delete(Long id);

	/**
	 * Count the number of company object available
	 * @return company number as int
	 */
	public int count();
}
