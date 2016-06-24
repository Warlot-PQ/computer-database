package com.excilys.console.cli;

import java.util.ArrayList;
import java.util.List;

import com.excilys.core.dto.ComputerDTO;
import com.excilys.persistence.pagination.PageRequest;
import com.excilys.service.service.interfaces.ComputerService;

public class ComputerListAllCommand implements Command {
	private ComputerService computerService;
	
	@Override
	public void execute() {
		computerService = CDB_launcher.applicationContext.getBean(ComputerService.class);
		
		List<ComputerDTO> computers = new ArrayList<>();

		computers = computerService.getAll(PageRequest.create().build());

		for (ComputerDTO computer : computers) {
			System.out.println(computer.toString());
		}
	}

}
