package com.excilys.utils;

import com.excilys.DTO.CompanyDTO;
import com.excilys.entity.Company;

public class CompanyMapper {
	public static CompanyDTO toDTO(Company company){
		return new CompanyDTO(
				(company.getId() == null) ? "" : company.getId().toString(),
						company.getName());
	}
}
