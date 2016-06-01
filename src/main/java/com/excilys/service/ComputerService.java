package com.excilys.service;
import java.sql.Connection;
import java.util.List;

import com.excilys.Pagination.Page;
import com.excilys.bean.Computer;
import com.excilys.bean.ComputerDTO;
import com.excilys.db.CoManager;
import com.excilys.exception.ConnectionException;
import com.excilys.exception.DAOException;
import com.excilys.exception.DriverException;
import com.excilys.service.interfaces.DAOComputer;
import com.excilys.service.interfaces.ServiceComputer;

/**
 * Service to manipulate Campany table.
 * 
 * @author pqwarlot
 *
 */
public class ComputerService implements ServiceComputer {
	private static ComputerService instance = null;
	private DAOComputer computerDAO = ComputerDAO.getInstance();

	public static ComputerService getInstance() {
		if (instance == null) {
			synchronized (ComputerService.class) {
				if (instance == null) {
					instance = new ComputerService();
				}
			}
		}
		return instance;
	}

	/**
	 * Contruct a ComputerService object to access ComputerDAO
	 */
	private ComputerService() {
	}

	@Override
	public List<ComputerDTO> getAll() {
		return computerDAO.findAll(PageRequest.create().build(), null);
	}
	
	/**
	 * Get all computers informations
	 * 
	 * @return all companies
	 * @throws DAOException
	 * @throws DriverException
	 * @throws ConnectionException
	 */
	@Override
	public List<ComputerDTO> getAll(PageRequest pageRequest) {
		return computerDAO.findAll(pageRequest, null);
	}
	
	/**
	 * Get informations from a specific computer
	 * 
	 * @param id
	 *            id of the requested computer
	 * @return computer wanted, empty computer object if error
	 * @throws DAOException
	 * @throws DriverException
	 * @throws ConnectionException
	 */
	@Override
	public ComputerDTO get(Long id) {
		List<ComputerDTO> computers = computerDAO.findAll(PageRequest.create().computerId(id).build(), null);
		if (computers.size() > 0) {
			return computers.get(0);
		}
		return null;
	}

	/**
	 * Create a new computer in DB from computer object in parameter. No
	 * computer object field required.
	 * 
	 * @param computer
	 *            Computer object to store in DB
	 * @return boolean true if introduced > discontinued or both null, false,
	 *         otherwise
	 * @throws DAOException
	 * @throws DriverException
	 * @throws ConnectionException
	 */
	@Override
	public void create(Computer computer) {
		computerDAO.create(computer);
	}

	/**
	 * Update a new computer in DB from computer object in parameter. No
	 * computer object field required.
	 * 
	 * @param computer
	 *            Computer object to update in DB
	 * @return boolean true if introduced > discontinued or both null, false,
	 *         otherwise
	 * @throws DAOException
	 * @throws DriverException
	 * @throws ConnectionException
	 */
	@Override
	public void update(Computer computer) {
		computerDAO.updateById(computer);
	}

	/**
	 * dd-MM-yyyy Delete computer from DB by id
	 * 
	 * @param computer
	 *            Computer object to delete from DB
	 * @return boolean true if id < 0, false otherwise
	 * @throws DAOException
	 * @throws DriverException
	 * @throws ConnectionException
	 */
	@Override
	public void delete(Long id) {
		computerDAO.delete(id);
	}

	@Override
	public int count() {
		return computerDAO.getRowNumber(PageRequest.create().build(), null);
	}
//	
//	private int count(PageRequest pageRequest) {
//		return computerDAO.getRowNumber(pageRequest, null);
//	}
//	
	@Override
	public void deleteByCompany(Long id) {
		throw new UnsupportedClassVersionError();
	}

	@Override
	public Page<ComputerDTO> getPage(PageRequest pageRequest) {
		List<ComputerDTO> computers = null;
		int computersNumber = 0;
		
		Connection con = CoManager.getInstance().getConnection();
		CoManager.beginTransaction(con);
		
		computersNumber = computerDAO.getRowNumber(pageRequest, con);
		computers = computerDAO.findAll(pageRequest, con);
		
		CoManager.endTransaction(con);
		CoManager.cleanup(con, null, null);

		return new Page<ComputerDTO>(computers, computersNumber, pageRequest.getPage().intValue(), pageRequest.getEltByPage().intValue());
	}
}
