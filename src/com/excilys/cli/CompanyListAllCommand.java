package com.excilys.cli;

import java.util.ArrayList;
import java.util.List;

import com.excilys.beans.Company;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;
import com.excilys.service.CompanyService;

public class CompanyListAllCommand implements Command {

	@Override
	public void execute() {
		List<Company> companies = new ArrayList<>();
		try {
			companies = CompanyService.getInstance().allCompany();
		} catch (DAOException | ConnectionException | DriverException e) {
			System.out.println("DB error!");
			return;
		}

		for (Company company: companies) {
			System.out.println(company.toString());
		}
//		new Page<Company>(companies).startPagination();
	}
	
}
