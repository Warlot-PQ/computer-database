package com.excilys.cli;

import java.util.Scanner;

import com.excilys.beans.Computer;
import com.excilys.beans.ComputerDTO;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;
import com.excilys.service.ComputerService;
import com.excilys.servlet.MapperUtils;

public class ComputerUpdateCommand implements Command {

	@Override
	public void execute() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		Long computerIdToUpdate = null;
		Computer computerToUpdate = null;
		ComputerDTO computerDTOToUpdate = null;

		System.out.printf("Enter the machine id:%n>");
		computerIdToUpdate = MapperUtils.convertStringToLong(input.nextLine());
		if (computerIdToUpdate == null) {
			return;
		}

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
			System.out.println(computerToUpdate.toString());
		}

		// Change computer to update field

		System.out.printf("Enter the computer name:%n>");
		computerToUpdate.setName(input.nextLine());

		System.out.printf("Enter the computer introduced date: format YYYY-MM-DD HH:MM:SS (enter to skip)%n>");
		computerToUpdate.setIntroduced(MapperUtils.convertStringToLocalDateTime(input.nextLine()));

		System.out.printf("Enter the computer discontinued date: format YYYY-MM-DD HH:MM:SS (enter to skip)%n>");
		computerToUpdate.setDiscontinued(MapperUtils.convertStringToLocalDateTime(input.nextLine()));

		System.out.printf("Enter the computer company id:%n>");
		computerToUpdate.setCompanyId(MapperUtils.convertStringToLong(input.nextLine()));

		// Report changes to the DB

		try {
			ComputerService.getInstance().update(computerToUpdate);
		} catch (DAOException | ConnectionException | DriverException e) {
			System.out.println("BD error!");
			return;
		}

		System.out.println("Computer updated with success.");
	}

}
