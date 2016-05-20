package org.excilys.cli;

import java.util.Scanner;

import org.excilys.beans.Computer;
import org.excilys.exceptions.ConnectionException;
import org.excilys.exceptions.DAOException;
import org.excilys.exceptions.DriverException;
import org.excilys.service.ComputerService;

public class ComputerListOneCommand implements Command {

	@Override
	public void execute() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String computerId = null;
		Long computerIdInt = null;
		Computer computer = null;
		
		System.out.printf("Enter the computer id wanted:%n>");
		computerId = input.nextLine();
		
		computerIdInt = Command.convertStringToLong(computerId);
		if (computerIdInt == null) return;
		
		try {
			computer = ComputerService.getInstance().getComputer(computerIdInt);
		} catch (DAOException | ConnectionException | DriverException e) {
			System.out.println("DB error!");
			return;
		}
		
		if (computer == null) {
			System.out.println("No computer found.");
		} else {
			System.out.println(computer.toString());
		}
	}
}
