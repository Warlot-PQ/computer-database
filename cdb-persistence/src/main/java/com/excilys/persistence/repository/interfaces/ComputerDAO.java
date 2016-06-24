package com.excilys.persistence.repository.interfaces;

import java.util.List;

import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.entity.Computer;
import com.excilys.persistence.pagination.PageRequest;

public interface ComputerDAO {
	public List<ComputerDTO> findAll(PageRequest pageRequest);

	/**
	 * Create a computer
	 * @param obj Computer object to create
	 */
	public void create(Computer obj);

	public void updateById(Computer obj);

	/**
	 * Delete a computer
	 * @param id Id of the computer to delete
	 */
	public void delete(Long id);

	/**
	 * Get number of computers using A PageRequest object as filter.
	 * @param pageRequest PageRequest object containing filter. LIMIT and ORDER BY are ignored
	 * @param con Connection object to use (e.g. for a transaction). Null if a new connection is needed.
	 * @return total number of computer
	 */
	public int count(PageRequest pageRequest);
	
	/**
	 * Delete a set of computers having the given company id
	 * @param id company id of computers to delete
	 * @return number of computer deleted
	 */
	public int deleteByCompany(Long id);
}
