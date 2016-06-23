package com.excilys.cli;

import java.util.ArrayList;
import java.util.List;

import com.excilys.DTO.CompanyDTO;
import com.excilys.service.interfaces.CompanyService;

public class CompanyListAllCommand implements Command {
	@Override
	public void execute() {
		List<CompanyDTO> companies = new ArrayList<>();

		CompanyService s1 = CDB_launcher.applicationContext.getBean("companyService", CompanyService.class);
		
		companies = s1.getAll();
		
		for (CompanyDTO company : companies) {
			System.out.println(company.toString());
		}
	}
}
