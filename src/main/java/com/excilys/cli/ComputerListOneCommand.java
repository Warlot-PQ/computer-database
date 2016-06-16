package com.excilys.cli;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.bean.ComputerDTO;
import com.excilys.service.interfaces.ComputerService;
import com.excilys.validation.MapperUtils;

public class ComputerListOneCommand implements Command {
	@Autowired
	private ComputerService computerService;
	
	@Override
	public void execute() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String computerId = null;
		Long computerIdInt = null;
		ComputerDTO computer = null;

		System.out.printf("Enter the computer id wanted:%n>");
		computerId = input.nextLine();

		computerIdInt = MapperUtils.convertStringToLong(computerId);
		if (computerIdInt == null) {
			return;
		}
		
		computer = computerService.get(computerIdInt);

		if (computer == null) {
			System.out.println("No computer found.");
		} else {
			System.out.println(computer.toString());
		}
	}
}
