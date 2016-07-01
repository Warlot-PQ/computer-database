package com.excilys.console.cli;

import java.util.List;

import com.excilys.console.restClient.ClientRestComputer;
import com.excilys.console.restClient.ReturnRest;
import com.excilys.core.dto.ComputerDTO;

public class ComputerListAllCommand implements Command {	
	@Override
	public void execute() {
		int responseCode = 0;
		
		System.out.println("Sending get request to server....");
		ReturnRest<List<ComputerDTO>> returnElt = ClientRestComputer.getAllComputer();
		responseCode = returnElt.getStatusCode();
		System.out.println("server: " + responseCode + " code");
		
		List<ComputerDTO> computers = returnElt.getEntity();
		if (computers != null) {
			for (ComputerDTO computer: computers) {
				System.out.println(computer.toString());
			}
		} else {
			System.out.println("No computer list received.");
		}
	}

}
