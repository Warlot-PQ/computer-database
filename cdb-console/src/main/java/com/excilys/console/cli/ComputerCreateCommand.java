package com.excilys.console.cli;

import java.util.Scanner;

import com.excilys.core.date.DateMapper;
import com.excilys.core.entity.Computer;
import com.excilys.service.service.interfaces.ComputerService;

public class ComputerCreateCommand implements Command {
	@Override
	public void execute() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		Computer computer = new Computer();

		System.out.printf("Enter the computer name:%n>");
		computer.setName(input.nextLine());

		System.out.printf("Enter the computer introduced date: format YYYY-MM-DD (enter to skip)%n>");
		computer.setIntroduced(DateMapper.convertStringToLocalDate(input.nextLine()));

		System.out.printf("Enter the computer discontinued date: format YYYY-MM-DD (enter to skip)%n>");
		computer.setDiscontinued(DateMapper.convertStringToLocalDate(input.nextLine()));

		System.out.printf("Enter the company id:%n>");
		computer.setCompanyId(DateMapper.convertStringToLong(input.nextLine()));

		CDB_launcher.applicationContext.getBean(ComputerService.class).create(computer);

		System.out.println("Computer added with success.");
	}

}
