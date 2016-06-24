package com.excilys.console.cli;

import java.util.Scanner;

import com.excilys.core.date.DateMapper;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.service.service.interfaces.ComputerService;

public class ComputerDeleteCommand implements Command {	
	@Override
	public void execute() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String comupterId = null;
		Long computerIdInt = null;
		ComputerDTO computer = null;

		System.out.printf("Enter the machine id to delete:%n>");
		comupterId = input.nextLine();

		computerIdInt = DateMapper.convertStringToLong(comupterId);
		if (computerIdInt == null) {
			return;
		}

		computer = CDB_launcher.applicationContext.getBean(ComputerService.class).get(computerIdInt);

		if (computer == null) {
			System.out.println("No computer matching this id.");
			return;
		} else {
			System.out.println("You have chosen to delete the following computer:");
			System.out.println(computer.toString());
		}

		CDB_launcher.applicationContext.getBean(ComputerService.class).delete(computerIdInt);

		System.out.println("Computer deleted with success.");
	}

}
