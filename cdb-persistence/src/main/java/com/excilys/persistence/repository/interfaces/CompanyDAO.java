package com.excilys.persistence.repository.interfaces;

import java.util.List;

import com.excilys.core.dto.CompanyDTO;
import com.excilys.core.entity.Company;
import com.excilys.persistence.pagination.PageRequest;

public interface CompanyDAO {
	public List<CompanyDTO> findAll(PageRequest pageRequest);

	public void create(Company obj);

	public void updateById(Company obj);

	public CompanyDTO findById(Long id);
	
	public void delete(Long id);

	public int count(PageRequest pageRequest);
	
	public int getRowNumber();
}
