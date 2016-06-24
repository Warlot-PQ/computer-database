package com.excilys.persistence.repository.interfaces;

import java.util.List;

import com.excilys.core.dto.CompanyDTO;
import com.excilys.core.entity.Company;

public interface CompanyDAO {
	public List<CompanyDTO> findAll();

	public void create(Company obj);

	public void updateById(Company obj);

	public CompanyDTO findById(Long id);
	
	public void delete(Long id);

	public int getRowNumber();
}
