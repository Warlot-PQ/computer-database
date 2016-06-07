package com.excilys.service.interfaces;

import java.util.List;

import com.excilys.bean.Company;
import com.excilys.bean.CompanyDTO;
import com.excilys.exception.DAOException;

public interface DAOCompany {
	public List<CompanyDTO> findAll() throws DAOException;

	public List<CompanyDTO> findFromTo(int offset, int limit) throws DAOException;

	public CompanyDTO findById(Long id) throws DAOException;

	public void create(Company obj) throws DAOException;

	public void updateById(Company obj) throws DAOException;

	public void delete(Long id) throws DAOException;

	public int getRowNumber() throws DAOException;

}
