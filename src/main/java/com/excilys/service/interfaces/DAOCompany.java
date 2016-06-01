package com.excilys.service.interfaces;

import java.sql.Connection;
import java.util.List;

import com.excilys.bean.Company;
import com.excilys.bean.CompanyDTO;

public interface DAOCompany {
	public List<CompanyDTO> findAll();

	public List<CompanyDTO> findFromTo(int offset, int limit);

	public CompanyDTO findById(Long id);

	public void create(Company obj);

	public void updateById(Company obj);

	public void delete(Long id, Connection con);

	public int getRowNumber();

}
