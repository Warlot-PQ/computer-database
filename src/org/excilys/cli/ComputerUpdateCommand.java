package org.excilys.cli;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Scanner;

import org.excilys.beans.Computer;
import org.excilys.exceptions.ConnectionException;
import org.excilys.exceptions.DAOException;
import org.excilys.exceptions.DriverException;
import org.excilys.service.ComputerService;

public class ComputerUpdateCommand implements Command {

	@Override
	public void execute() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		Long computerIdToUpdate = null;
		Computer computerToUpdate = null;
		boolean success = false;
		
		System.out.printf("Enter the machine id:%n>");
		computerIdToUpdate = Command.convertStringToLong( input.nextLine() );
		if (computerIdToUpdate == null) return;

		// Fetch the computer to update
		
		System.out.println("You have chosen to update the following computer:");
		try {
			computerToUpdate = ComputerService.getInstance().getComputer(computerIdToUpdate);
		} catch (DAOException | ConnectionException | DriverException e) {
			System.out.println("BD error!");
			return;
		}

		if (computerToUpdate == null) {
			System.out.println("No computer found!");
			return;
		} else {
			System.out.println( computerToUpdate.toString() );
			
		}
		
		// Change computer to update field
		
		System.out.printf("Enter the computer name:%n>");
		computerToUpdate.setName( input.nextLine() );

		System.out.printf("Enter the computer introduced date: format YYYY-MM-DD HH:MM:SS (enter to skip)%n>");
		computerToUpdate.setIntroduced( Command.convertStringToLocalDateTime( input.nextLine() ) );
				
		System.out.printf("Enter the computer discontinued date: format YYYY-MM-DD HH:MM:SS (enter to skip)%n>");
		computerToUpdate.setDiscontinued( Command.convertStringToLocalDateTime( input.nextLine() ) );
		
		System.out.printf("Enter the computer company id:%n>");
		computerToUpdate.setCompanyId( Command.convertStringToLong( input.nextLine() ) );
		
		
		// Report changes to the DB
		
		try {
			success = ComputerService.getInstance().updateComputer(computerToUpdate);
		} catch (DAOException | ConnectionException | DriverException e) {
			System.out.println("BD error!");
			return;
		}
		
		if (success == true) {
			System.out.println("Computer updated with success.");
		} else {
			System.out.println("Computer not updated from the database!");
		}
	}

}
