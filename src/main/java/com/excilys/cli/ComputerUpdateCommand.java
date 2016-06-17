package com.excilys.cli;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.bean.Computer;
import com.excilys.bean.ComputerDTO;
import com.excilys.bean.mapper.DateMapper;
import com.excilys.service.interfaces.ComputerService;

public class ComputerUpdateCommand implements Command {
	@Autowired
	private ComputerService computerService;

	@Override
	public void execute() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		Long computerIdToUpdate = null;
		Computer computerToUpdate = null;
		ComputerDTO computerDTOToUpdate = null;

		System.out.printf("Enter the machine id:%n>");
		computerIdToUpdate = DateMapper.convertStringToLong(input.nextLine());
		if (computerIdToUpdate == null) {
			return;
		}

		// Fetch the computer to update

		System.out.println("You have chosen to update the following computer:");
		computerDTOToUpdate = computerService.get(computerIdToUpdate);

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
		computerToUpdate.setIntroduced(DateMapper.convertStringToLocalDate(input.nextLine()));

		System.out.printf("Enter the computer discontinued date: format YYYY-MM-DD HH:MM:SS (enter to skip)%n>");
		computerToUpdate.setDiscontinued(DateMapper.convertStringToLocalDate(input.nextLine()));

		System.out.printf("Enter the computer company id:%n>");
		computerToUpdate.setCompanyId(DateMapper.convertStringToLong(input.nextLine()));

		// Report changes to the DB
		computerService.update(computerToUpdate);

		System.out.println("Computer updated with success.");
	}

}
