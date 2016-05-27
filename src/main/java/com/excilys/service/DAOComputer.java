package com.excilys.service;

import com.excilys.beans.Computer;
import com.excilys.beans.ComputerDTO;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;

public interface DAOComputer extends DAO<Computer, ComputerDTO> {
	public void deleteByCompany(Long id) throws DAOException, ConnectionException, DriverException;
}
