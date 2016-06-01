package com.excilys.service.interfaces;

import java.util.List;

import com.excilys.Pagination.Page;
import com.excilys.bean.Computer;
import com.excilys.bean.ComputerDTO;
import com.excilys.exception.ConnectionException;
import com.excilys.exception.DAOException;
import com.excilys.exception.DriverException;
import com.excilys.service.PageRequest;

/**
 * Specific services to computer entities
 * @author pqwarlot
 *
 */
public interface ServiceComputer {
	public List<ComputerDTO> getAll();
	
	public List<ComputerDTO> getAll(PageRequest pageRequest);
	
	public ComputerDTO get(Long id);

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
	 * Count the number of computer object available
	 * @return computer number as int
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
	public Page<ComputerDTO> getPage(PageRequest pageRequest) throws DAOException, ConnectionException, DriverException;
	
	/**
	 * Delete computers having the given company id
	 * @param id company id
	 * @throws DAOException
	 * @throws ConnectionException
	 * @throws DriverException
	 */
	public void deleteByCompany(Long id) throws DAOException, ConnectionException, DriverException;
}
