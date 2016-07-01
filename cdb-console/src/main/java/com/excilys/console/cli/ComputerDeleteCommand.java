package com.excilys.console.cli;

import java.util.Scanner;

import com.excilys.console.restClient.ClientRestComputer;
import com.excilys.console.restClient.ReturnRest;
import com.excilys.core.date.DateMapper;
import com.excilys.core.dto.ComputerDTO;

public class ComputerDeleteCommand implements Command {	
	@Override
	public void execute() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String computerId = null;
		Long computerIdInt = null;
		ComputerDTO computerDTOToCreate = null;

		System.out.printf("Enter the machine id to delete:%n>");
		computerId = input.nextLine();

		computerIdInt = DateMapper.convertStringToLong(computerId);
		if (computerIdInt == null) {
			return;
		}

		System.out.println("Sending get request to server....");
		ReturnRest<ComputerDTO> returnEltGet = ClientRestComputer.getComputer(computerId);
		System.out.println("server: " + returnEltGet.getStatusCode() + " code");

		System.out.println("You have chosen to delete the following computer:");
		ComputerDTO computer = returnEltGet.getEntity();
		if (computer != null) {
			System.out.println(computer.toString());
		} else {
			System.out.println("No computer matching this id.");
			return;
		}

		System.out.println("Sending deletion request to server....");
		ReturnRest<String> returnEltDelete = ClientRestComputer.deleteComputer(computerId);
		System.out.println("server: " + returnEltGet.getStatusCode() + " code");
		
		String msg = returnEltDelete.getEntity();
		if (msg != null) {
			System.out.println("server: " + msg);
		} else {
			System.out.println("No message from server.");
		}
	}

}
