package com.excilys.service.interfaces;

import java.util.List;

import com.excilys.bean.Computer;
import com.excilys.bean.ComputerDTO;
import com.excilys.exception.DAOException;
import com.excilys.service.PageRequest;

public interface DAOComputer {
	public List<ComputerDTO> findAll(PageRequest pageRequest) throws DAOException;

	public void create(Computer obj) throws DAOException;

	public void updateById(Computer obj) throws DAOException;

	public void delete(Long id) throws DAOException;

	/**
	 * Get number of total computers. Use cached value is possible.
	 * @param con Connection object to use (e.g. for a transaction). Null if a new connection is needed.
	 * @return total number of computer
	 */
	public int countAll() throws DAOException;

	/**
	 * Get number of computers using A PageRequest object as filter
	 * @param pageRequest PageRequest object containg filter. LIMIT and ORDER BY are ignored
	 * @param con Connection object to use (e.g. for a transaction). Null if a new connection is needed.
	 * @return total number of computer
	 */
	public int count(PageRequest pageRequest) throws DAOException;
	
	public void deleteByCompany(Long id) throws DAOException;
}
