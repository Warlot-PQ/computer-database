package com.excilys.cli;

import java.util.Scanner;

import com.excilys.beans.Computer;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;
import com.excilys.service.ComputerService;

public class ComputerDeleteCommand implements Command {

	@Override
	public void execute() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String comupterId = null;
		Long computerIdInt = null;
		boolean success = false;
		Computer computer = null;
		
		System.out.printf("Enter the machine id to delete:%n>");
		comupterId = input.nextLine();
		
		computerIdInt = Command.convertStringToLong(comupterId);
		if (computerIdInt == null) return;

		try {
			computer = ComputerService.getInstance().getComputer(computerIdInt);
		} catch (DAOException | ConnectionException | DriverException e) {
			System.out.println("DB error!");
			return;
		}

		if (computer == null) {
			System.out.println("No computer matching this id.");
			return;
		} else {
			System.out.println("You have chosen to delete the following computer:");
			System.out.println(computer.toString());
		}
		
		try {
			success = ComputerService.getInstance().deleteComputer(computerIdInt);
		} catch (DAOException | ConnectionException | DriverException e) {
			System.out.println("DB error!");
			return;
		}

		if (success == true) {
			System.out.println("Computer deleted with success.");
		} else {
			System.out.println("Computer not deleted from the database!");
		}
	}

}
