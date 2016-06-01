package com.excilys.cli;

import java.util.Scanner;

import com.excilys.bean.ComputerDTO;
import com.excilys.service.ComputerService;
import com.excilys.validation.MapperUtils;

public class ComputerDeleteCommand implements Command {

	@Override
	public void execute() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String comupterId = null;
		Long computerIdInt = null;
		boolean success = false;
		ComputerDTO computer = null;

		System.out.printf("Enter the machine id to delete:%n>");
		comupterId = input.nextLine();

		computerIdInt = MapperUtils.convertStringToLong(comupterId);
		if (computerIdInt == null) {
			return;
		}

		computer = ComputerService.getInstance().get(computerIdInt);

		if (computer == null) {
			System.out.println("No computer matching this id.");
			return;
		} else {
			System.out.println("You have chosen to delete the following computer:");
			System.out.println(computer.toString());
		}

		ComputerService.getInstance().delete(computerIdInt);

		System.out.println("Computer deleted with success.");
	}

}
