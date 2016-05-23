package com.excilys.cli;

import java.util.ArrayList;
import java.util.List;

import com.excilys.beans.Company;
import com.excilys.beans.CompanyExt;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;
import com.excilys.service.CompanyService;

public class CompanyListAllCommand implements Command {

	@Override
	public void execute() {
		List<CompanyExt> companies = new ArrayList<>();
		try {
			companies = CompanyService.getInstance().getAll();
		} catch (DAOException | ConnectionException | DriverException e) {
			System.out.println("DB error!");
			return;
		}

		Page<Company, CompanyExt> p = new Page<>(CompanyService.getInstance());
		try {
			companies = p.nextPage();
		} catch (DAOException | ConnectionException | DriverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (CompanyExt company: companies) {
			System.out.println(company.toString());
		}
	}
}
