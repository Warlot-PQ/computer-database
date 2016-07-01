package com.excilys.console.cli;

import java.util.Scanner;

import com.excilys.console.restClient.ClientRestComputer;
import com.excilys.console.restClient.ReturnRest;
import com.excilys.core.date.DateMapper;
import com.excilys.core.entity.Computer;

public class ComputerCreateCommand implements Command {
	@Override
	public void execute() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		Computer computer = new Computer();

		System.out.printf("Enter the computer name:%n>");
		computer.setName(input.nextLine());

		System.out.printf("Enter the computer introduced date: format dd/MM/yyyy (enter to skip)%n>");
		computer.setIntroduced(DateMapper.convertStringToLocalDate(input.nextLine()));

		System.out.printf("Enter the computer discontinued date: format dd/MM/yyyy (enter to skip)%n>");
		computer.setDiscontinued(DateMapper.convertStringToLocalDate(input.nextLine()));

		System.out.printf("Enter the company id:%n>");
		Long companyId = DateMapper.convertStringToLong(input.nextLine());
		if (companyId == null) return;
		computer.setCompanyId(companyId);

		System.out.println("Computer to create: " + computer.toString());
		
		System.out.println("Sending request to server....");
		ReturnRest<String> returnElt = ClientRestComputer.createComputer(computer);
		System.out.println("server answer " + returnElt.getStatusCode());
		System.out.println("server answer " + returnElt.getEntity().toString());
		
//		CDB_launcher.applicationContext.getBean(ComputerService.class).create(computer);

		System.out.println("Computer added with success.");
	}

}
