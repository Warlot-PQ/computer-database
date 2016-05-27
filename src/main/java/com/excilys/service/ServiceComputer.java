package com.excilys.service;

import com.excilys.beans.Computer;
import com.excilys.beans.ComputerDTO;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;

/**
 * Specific services to computer entities
 * @author pqwarlot
 *
 */
public interface ServiceComputer extends Service<Computer, ComputerDTO> {
	public void deleteByCompany(Long id) throws DAOException, ConnectionException, DriverException;
}
