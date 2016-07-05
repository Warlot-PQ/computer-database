package com.excilys.console.cli;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.console.restClient.ClientRestComputer;
import com.excilys.console.restClient.ReturnRest;
import com.excilys.core.date.DateMapper;
import com.excilys.core.dto.ComputerDTO;

public class ComputerListOneCommand implements Command {
	@Autowired
	private ClientRestComputer clientRest;
		
	@Override
	public void execute(Scanner input) {
		String computerId = null;
		Long computerIdInt = null;

		System.out.printf("Enter the computer id wanted:%n>");
		computerId = input.nextLine();

		computerIdInt = DateMapper.convertStringToLong(computerId);
		if (computerIdInt == null) {
			return;
		}
		
		System.out.println("Sending deletion request to server....");
		ReturnRest<ComputerDTO> returnElt = clientRest.getComputer(computerId);
		System.out.println("server: " + returnElt.getStatusCode() + " code");
		
		ComputerDTO computer = returnElt.getEntity();
		if (computer != null) {
			System.out.println(computer.toString());
		} else {
			System.out.println("No computer found.");
		}
	}
}
