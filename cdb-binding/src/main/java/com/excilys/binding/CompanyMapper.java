package com.excilys.binding;

import com.excilys.core.entity.Company;
import com.excilys.core.dto.CompanyDTO;

public class CompanyMapper {
	public static CompanyDTO toDTO(Company company){
		return new CompanyDTO(
				(company.getId() == null) ? "" : company.getId().toString(),
						company.getName());
	}
}
