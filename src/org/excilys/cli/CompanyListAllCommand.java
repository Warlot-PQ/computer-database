package org.excilys.cli;

import java.util.ArrayList;
import java.util.List;

import org.excilys.beans.Company;
import org.excilys.exceptions.ConnectionException;
import org.excilys.exceptions.DAOException;
import org.excilys.exceptions.DriverException;
import org.excilys.service.CompanyService;

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
