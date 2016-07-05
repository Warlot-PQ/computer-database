package com.excilys.console.cli;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.console.restClient.ClientRestComputer;
import com.excilys.console.restClient.ReturnRest;
import com.excilys.core.date.DateMapper;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.validator.DateValidator;

public class ComputerUpdateCommand implements Command {
	@Autowired
	private ClientRestComputer clientRest;
	
	@Override
	public void execute(Scanner input) {
		String computerIdToUpdateStr = null;
		Long computerIdToUpdate = null;

		System.out.printf("Enter the machine id:%n>");
		computerIdToUpdateStr = input.nextLine();
		computerIdToUpdate = DateMapper.convertStringToLong(computerIdToUpdateStr);
		if (computerIdToUpdate == null) {
			return;
		}

		// Fetch the computer to update
		System.out.println("Sending deletion request to server....");
		ReturnRest<ComputerDTO> returnEltGet = clientRest.getComputer(computerIdToUpdateStr);
		System.out.println("server: " + returnEltGet.getStatusCode() + " code");

		System.out.println("You have chosen to update the following computer:");
		ComputerDTO computerDTOToUpdate = returnEltGet.getEntity();
		if (computerDTOToUpdate != null) {
			System.out.println(computerDTOToUpdate.toString());
		} else {
			System.out.println("No computer found!");
			return;
		}

		// Locally update computer to update
		System.out.printf("Enter the computer name:%n>");
		computerDTOToUpdate.setName(input.nextLine());

		System.out.printf("Enter the computer introduced date: format " + DateValidator.DATE_PATTERN + " (enter to skip)%n>");
		String dateStr = input.nextLine();
		if (DateValidator.isValid(dateStr) == false) { 
			System.out.println("Incorrect date format! Stopping process.");
			return; 
		}
		computerDTOToUpdate.setIntroduced(dateStr);

		System.out.printf("Enter the computer discontinued date: format " + DateValidator.DATE_PATTERN + " (enter to skip)%n>");
		dateStr = input.nextLine();
		if (DateValidator.isValid(dateStr) == false) { 
			System.out.println("Incorrect date format! Stopping process.");
			return; 
		}
		computerDTOToUpdate.setDiscontinued(dateStr);

		System.out.printf("Enter the computer company id:%n>");
		computerDTOToUpdate.setCompanyId(input.nextLine());

		// Report changes to the DB
		System.out.println("Sending deletion request to server....");
		ReturnRest<String> returnEltUpdate = clientRest.updateComputer(computerDTOToUpdate);
		System.out.println("server: " + returnEltGet.getStatusCode() + " code");

		String msg = returnEltUpdate.getEntity();
		if (msg != null) {
			System.out.println("server: " + msg);
		} else {
			System.out.println("No message from server.");
		}
	}
}
