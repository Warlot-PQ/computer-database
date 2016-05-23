package com.excilys.cli;

import java.util.Scanner;

import com.excilys.beans.Computer;
import com.excilys.beans.ComputerExt;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;
import com.excilys.service.ComputerService;

public class ComputerUpdateCommand implements Command {

	@Override
	public void execute() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		Long computerIdToUpdate = null;
		Computer computerToUpdate = null;
		ComputerExt computerDTOToUpdate = null;
		boolean success = false;
		
		System.out.printf("Enter the machine id:%n>");
		computerIdToUpdate = Mapper.convertStringToLong( input.nextLine() );
		if (computerIdToUpdate == null) return;

		// Fetch the computer to update
		
		System.out.println("You have chosen to update the following computer:");
		try {
			computerDTOToUpdate = ComputerService.getInstance().get(computerIdToUpdate);
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
		computerToUpdate.setIntroduced( Mapper.convertStringToLocalDateTime( input.nextLine() ) );
				
		System.out.printf("Enter the computer discontinued date: format YYYY-MM-DD HH:MM:SS (enter to skip)%n>");
		computerToUpdate.setDiscontinued( Mapper.convertStringToLocalDateTime( input.nextLine() ) );
		
		System.out.printf("Enter the computer company id:%n>");
		computerToUpdate.setCompanyId( Mapper.convertStringToLong( input.nextLine() ) );
		
		
		// Report changes to the DB
		
		try {
			success = ComputerService.getInstance().update(computerToUpdate);
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
