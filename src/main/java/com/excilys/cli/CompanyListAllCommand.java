package com.excilys.cli;

import java.util.ArrayList;
import java.util.List;

import com.excilys.beans.Company;
import com.excilys.beans.CompanyDTO;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;
import com.excilys.service.CompanyService;

public class CompanyListAllCommand implements Command {

	@Override
	public void execute() {
		List<CompanyDTO> companies = new ArrayList<>();
		try {
			companies = CompanyService.getInstance().getAll();
		} catch (DAOException | ConnectionException | DriverException e) {
			System.out.println("DB error!");
			return;
		}

		Page<Company, CompanyDTO> p = new Page<>(CompanyService.getInstance());
		try {
			companies = p.nextPage();
		} catch (DAOException | ConnectionException | DriverException e) {
			e.printStackTrace();
		}

		for (CompanyDTO company : companies) {
			System.out.println(company.toString());
		}
	}
}
