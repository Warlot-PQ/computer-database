package com.excilys.console.cli;

import java.util.ArrayList;
import java.util.List;

import com.excilys.console.restClient.ClientRestComputer;
import com.excilys.console.restClient.ReturnRest;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.service.service.interfaces.ComputerService;

public class ComputerListAllCommand implements Command {
	private ComputerService computerService;
	
	@Override
	public void execute() {
		computerService = CDB_launcher.applicationContext.getBean(ComputerService.class);
		
		List<ComputerDTO> computers = new ArrayList<>();

		System.out.println("Sending request to server....");
		ReturnRest<List<ComputerDTO>> returnElt = ClientRestComputer.getAllComputer();
		System.out.println("server answer " + returnElt.getStatusCode());
		
//		computers = computerService.getAll(PageRequest.create().build());
		
		if (returnElt.getStatusCode() == 200) {
			for (ComputerDTO computer : computers) {
				System.out.println(computer.toString());
			}
		}
	}

}
