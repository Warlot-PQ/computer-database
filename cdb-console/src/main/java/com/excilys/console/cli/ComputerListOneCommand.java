package com.excilys.console.cli;

import java.util.Scanner;

import com.excilys.core.date.DateMapper;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.service.service.interfaces.ComputerService;

public class ComputerListOneCommand implements Command {	
	@Override
	public void execute() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String computerId = null;
		Long computerIdInt = null;
		ComputerDTO computer = null;

		System.out.printf("Enter the computer id wanted:%n>");
		computerId = input.nextLine();

		computerIdInt = DateMapper.convertStringToLong(computerId);
		if (computerIdInt == null) {
			return;
		}
		
		computer = CDB_launcher.applicationContext.getBean(ComputerService.class).get(computerIdInt);

		if (computer == null) {
			System.out.println("No computer found.");
		} else {
			System.out.println(computer.toString());
		}
	}
}
