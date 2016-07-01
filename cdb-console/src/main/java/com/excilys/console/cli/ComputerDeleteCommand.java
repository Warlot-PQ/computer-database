package com.excilys.console.cli;

import java.util.Scanner;

import com.excilys.console.restClient.ClientRestComputer;
import com.excilys.core.date.DateMapper;
import com.excilys.core.dto.ComputerDTO;

public class ComputerDeleteCommand implements Command {	
	@Override
	public void execute() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String computerId = null;
		Long computerIdInt = null;
		ComputerDTO computer = null;

		System.out.printf("Enter the machine id to delete:%n>");
		computerId = input.nextLine();

		computerIdInt = DateMapper.convertStringToLong(computerId);
		if (computerIdInt == null) {
			return;
		}

		computer = ClientRestComputer.getComputer(computerId);

		if (computer == null) {
			System.out.println("No computer matching this id.");
			return;
		} else {
			System.out.println("You have chosen to delete the following computer:");
			System.out.println(computer.toString());
		}

		boolean success = ClientRestComputer.deleteComputer(computerId);
		
//		CDB_launcher.applicationContext.getBean(ComputerService.class).delete(computerIdInt);

		if (success) {
			System.out.println("Computer deleted with success.");
		} else {
			System.out.println("Computer not deleted!");
		}
	}

}
