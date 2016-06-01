package com.excilys.cli;

import java.util.ArrayList;
import java.util.List;

import com.excilys.bean.CompanyDTO;
import com.excilys.service.CompanyService;

public class CompanyListAllCommand implements Command {

	@Override
	public void execute() {
		List<CompanyDTO> companies = new ArrayList<>();

		companies = CompanyService.getInstance().getAll();


//		Page<CompanyDTO> p = new Page<>(CompanyService.getInstance());
//		try {
//			companies = p.nextPage();
//		} catch (DAOException | ConnectionException | DriverException e) {
//			e.printStackTrace();
//		}

		for (CompanyDTO company : companies) {
			System.out.println(company.toString());
		}
	}
}
