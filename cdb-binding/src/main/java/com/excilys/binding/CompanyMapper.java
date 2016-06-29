package com.excilys.binding;

import com.excilys.core.dto.CompanyDTO;
import com.excilys.core.entity.Company;

/**
 * Mapper around Company and CompanyDTO object
 * @author pqwarlot
 *
 */
public class CompanyMapper {
	public static CompanyDTO toDTO(Company company){
		return new CompanyDTO(
				(company.getId() == null) ? "" : company.getId().toString(),
						company.getName());
	}
}
