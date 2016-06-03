package com.excilys.service.interfaces;

import java.util.List;

import com.excilys.bean.Computer;
import com.excilys.bean.ComputerDTO;
import com.excilys.exception.DAOException;
import com.excilys.service.PageRequest;

public interface DAOComputer {
	public List<ComputerDTO> findAll(PageRequest pageRequest) throws DAOException;

	/**
	 * Create a computer
	 * @param obj Computer object to create
	 */
	public void create(Computer obj) throws DAOException;

	public void updateById(Computer obj) throws DAOException;

	/**
	 * Delete a computer
	 * @param id Id of the computer to delete
	 */
	public void delete(Long id) throws DAOException;

	/**
	 * Get number of computers using A PageRequest object as filter.
	 * @param pageRequest PageRequest object containing filter. LIMIT and ORDER BY are ignored
	 * @param con Connection object to use (e.g. for a transaction). Null if a new connection is needed.
	 * @return total number of computer
	 */
	public int count(PageRequest pageRequest) throws DAOException;
	
	public int deleteByCompany(Long id) throws DAOException;
}
