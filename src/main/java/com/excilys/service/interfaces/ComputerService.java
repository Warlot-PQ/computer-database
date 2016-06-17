package com.excilys.service.interfaces;

import java.util.List;

import com.excilys.Pagination.Page;
import com.excilys.Pagination.PageRequest;
import com.excilys.bean.Computer;
import com.excilys.bean.ComputerDTO;
import com.excilys.exception.ConnectionException;
import com.excilys.exception.DriverException;

/**
 * Specific services to computer entities
 * @author pqwarlot
 *
 */
public interface ComputerService {
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
	public void create(Computer obj);

	/**
	 * Update a computer
	 * @param obj computer to update as entity object
	 */
	public void update(Computer obj);

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
	 * @throws DAOException exception from DAO
	 * @throws ConnectionException exception from CoManager concerning Connection
	 * @throws DriverException exception from CoManager concerning Driver
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
