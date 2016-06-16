package com.excilys.service.interfaces;

import java.util.List;

import com.excilys.bean.Company;
import com.excilys.bean.CompanyDTO;


public interface CompanyDAO {
	public List<CompanyDTO> findAll();

	public void create(Company obj);

	public void updateById(Company obj);

	public CompanyDTO findById(Long id);
	
	public void delete(Long id);

	public int getRowNumber();
}
