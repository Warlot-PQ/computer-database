package com.excilys.service.service.interfaces;

import java.util.List;

import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.entity.Computer;
import com.excilys.persistence.pagination.PageRequest;
import com.excilys.service.pagination.Page;

/**
 * Specific services to computer entities
 * @author pqwarlot
 *
 */
public interface ComputerService {
	public enum ErrorMessage {
		COMPUTER_DATE_ERROR, NONE
	};
	/**
	 * Get all computers
	 * @return list of ComputerDTO
	 */
	public List<ComputerDTO> getAll();
	
	/**
	 * Get all computers matching filter from the PageRequest object.
	 * @param pageRequest Filter apply to the query.
	 * @return list of ComputerDTO
	 */
	public List<ComputerDTO> getAll(PageRequest pageRequest);
	
	public ComputerDTO get(Long id);

	/**
	 * Create a computer
	 * @param obj Computer object to create
	 */
	public ErrorMessage create(Computer obj);

	/**
	 * Update a computer
	 * @param obj computer to update as entity object
	 */
	public ErrorMessage update(Computer obj);

	/**
	 * Delete a computer
	 * @param id id of the computer to delete
	 */
	public void delete(Long id);

	/**
	 * Count the number of computer object available. Use cached value if possible.
	 * @return number of total computers as int
	 */
	public int count();
	
	/**
	 * Get a page of DTO object
	 * @param pageRequest options to create the query to get DTO
	 * @return Page<ComputerDTO> page containing all informations
	 */
	public Page<ComputerDTO> getPage(PageRequest pageRequest);
	
	/**
	 * Delete computers having the given company id
	 * @param id company id
	 * @throws DAOException
	 * @throws ConnectionException
	 * @throws DriverException
	 */
	public void deleteByCompany(Long id);
}
