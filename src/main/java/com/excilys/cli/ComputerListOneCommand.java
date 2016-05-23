package com.excilys.cli;

import java.util.Scanner;

import com.excilys.beans.ComputerExt;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;
import com.excilys.service.ComputerService;

public class ComputerListOneCommand implements Command {

	@Override
	public void execute() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String computerId = null;
		Long computerIdInt = null;
		ComputerExt computer = null;
		
		System.out.printf("Enter the computer id wanted:%n>");
		computerId = input.nextLine();
		
		computerIdInt = Mapper.convertStringToLong(computerId);
		if (computerIdInt == null) return;
		
		try {
			computer = ComputerService.getInstance().get(computerIdInt);
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
