package com.excilys.console.cli;

import java.util.List;

import com.excilys.console.restClient.ClientRestCompany;
import com.excilys.console.restClient.ReturnRest;
import com.excilys.core.dto.CompanyDTO;

public class CompanyListAllCommand implements Command {
	@Override
	public void execute() {
		int responseCode = 0;

		System.out.println("Sending get request to server....");
		ReturnRest<List<CompanyDTO>> returnElt = ClientRestCompany.getAllCompany();
		responseCode = returnElt.getStatusCode();
		System.out.println("server: " + responseCode + " code");
		
		List<CompanyDTO> companies = returnElt.getEntity();
		if (companies != null) {
			for (CompanyDTO company: companies) {
				System.out.println(company.toString());
			}
		} else {
			System.out.println("No company list received.");
		}
	}
}
