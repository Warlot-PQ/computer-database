package com.excilys.console.cli;

import java.util.Scanner;

import com.excilys.console.restClient.ClientRestComputer;
import com.excilys.console.restClient.ReturnRest;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.validator.DateValidator;

public class ComputerCreateCommand implements Command {
	@Override
	public void execute() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		ComputerDTO computerDTOToCreate = new ComputerDTO();

		System.out.printf("Enter the computer name:%n>");
		computerDTOToCreate.setName(input.nextLine());

		
		System.out.printf("Enter the computer introduced date: format DD/MM/YYYY (enter to skip)%n>");
		String dateStr = input.nextLine();
		if (DateValidator.isValid(dateStr) == false) { 
			System.out.println("Incorrect date format! Stopping process.");
			return; 
		}
		computerDTOToCreate.setIntroduced(dateStr);		
		
		System.out.printf("Enter the computer introduced date: format DD/MM/YYYY (enter to skip)%n>");
		dateStr = input.nextLine();
		if (DateValidator.isValid(dateStr) == false) { 
			System.out.println("Incorrect date format! Stopping process.");
			return; 
		}
		computerDTOToCreate.setDiscontinued(dateStr);
		
		System.out.printf("Enter the company id:%n>");
		computerDTOToCreate.setCompanyId(input.nextLine());

		System.out.println("Computer to create: " + computerDTOToCreate.toString());
		
		System.out.println("Sending request to server....");
		ReturnRest<String> returnElt = ClientRestComputer.createComputer(computerDTOToCreate);
		System.out.println("server answer " + returnElt.getStatusCode());
		System.out.println("server answer " + returnElt.getEntity().toString());
		
		System.out.println("Computer added with success.");
	}

}
