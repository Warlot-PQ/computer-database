package com.excilys.cli;

import java.util.Scanner;

import com.excilys.bean.Computer;
import com.excilys.service.ComputerService;
import com.excilys.validation.MapperUtils;

public class ComputerCreateCommand implements Command {

	@Override
	public void execute() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		Computer computer = new Computer();

		System.out.printf("Enter the computer name:%n>");
		computer.setName(input.nextLine());

		System.out.printf("Enter the computer introduced date: format YYYY-MM-DD (enter to skip)%n>");
		computer.setIntroduced(MapperUtils.convertStringToLocalDate(input.nextLine()));

		System.out.printf("Enter the computer discontinued date: format YYYY-MM-DD (enter to skip)%n>");
		computer.setDiscontinued(MapperUtils.convertStringToLocalDate(input.nextLine()));

		System.out.printf("Enter the company id:%n>");
		computer.setCompanyId(MapperUtils.convertStringToLong(input.nextLine()));

		ComputerService.getInstance().create(computer);

		System.out.println("Computer added with success.");
	}

}
