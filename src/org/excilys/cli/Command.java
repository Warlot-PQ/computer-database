package org.excilys.cli;

import java.sql.Timestamp;
import java.util.List;
import java.util.Scanner;

import org.excilys.beans.Company;
import org.excilys.beans.Computer;
import org.excilys.services.CompanyService;
import org.excilys.services.ComputerService;

/**
 * CLI implementation
 * 
 * @author pqwarlot
 *
 */
public class Command {
	private static String FORMAT_ERROR = "Incorrect format!";
	
	private ComputerService computerService;
	private CompanyService companyService;

	public Command() {
		computerService = new ComputerService();
		companyService = new CompanyService();
	}
	
	public void dispatch(String arg) {
		switch (arg) {
		case "List_computers":
			this.displayAllComputers();
			break;
		case "List_companies":
			this.displayAllCompanies();
			break;
		case "Show_computer_details":
			this.displayComputerDetails();
			break;
		case "Create_a_computer":
			this.createComputer();
			break;
		case "Update_a_computer":
			this.updateComputer();
			break;
		case "Delete_a_computer":
			this.deleteComputer();
			break;
		case "Exit":
			System.out.println("Bye bye.");
			System.exit(0);
			break;
		default:
			System.out.printf("Unknow command, please use one of the following : " + "%n List_computers"
					+ "%n List_companies" + "%n Show_computer_details" + "%n Create_a_computer" + "%n Update_a_computer"
					+ "%n Delete_a_computer" + "%n Exit%n%n");
			break;
		}
	}
	
	private void displayAllComputers() {
		List<Computer> computers = computerService.allComputer();

		new Page<Computer>(computers).startPagination();
	}
	
	private void displayComputerDetails(){
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String computerId = null;
		int computerIdInt = 0;
		
		System.out.printf("Enter the computer id wanted:%n>");
		computerId = input.nextLine();
		
		try {
			computerIdInt = Integer.valueOf(computerId);
		} catch (IllegalArgumentException e) {
			System.out.println(FORMAT_ERROR);
			return;
		}
		
		Computer computer = computerService.getComputer(computerIdInt);
		
		if (computer.isEmpty() == true) {
			System.out.println("No computer found.");
		} else {
			System.out.println(computer.toString());
		}
	}

	private void displayAllCompanies() {
		List<Company> companies = companyService.allCompany();

		new Page<Company>(companies).startPagination();
	}

	private void createComputer() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String computerName = null;
		String computerIntroduced = null;
		String computerDiscontinued = null;
		Timestamp computerIntroducedTimestamp = null;
		Timestamp computerDiscontinuedTimestamp = null;
		String computerCompany_id = null;
		int computerCompany_idInt = 0;
		Computer computer = null;
		
		System.out.printf("Enter the computer name:%n>");
		computerName = input.nextLine();
		
		System.out.printf("Enter the computer introduced date: format YYYY-MM-DD HH:MM:SS%n>");
		computerIntroduced = input.nextLine();
		
		try {
			computerIntroducedTimestamp = Timestamp.valueOf(computerIntroduced);
		} catch (IllegalArgumentException e) {
			System.out.println(FORMAT_ERROR);
			return;
		}
		
		System.out.printf("Enter the computer discontinued date: format YYYY-MM-DD HH:MM:SS%n>");
		computerDiscontinued = input.nextLine();

		try {
			computerDiscontinuedTimestamp = Timestamp.valueOf(computerDiscontinued);
		} catch (IllegalArgumentException e) {
			System.out.println(FORMAT_ERROR);
			return;
		}
		
		System.out.printf("Enter the company id:%n>");
		computerCompany_id = input.nextLine();
		
		try {
			computerCompany_idInt = Integer.valueOf(computerCompany_id);
		} catch (IllegalArgumentException e) {
			System.out.println(FORMAT_ERROR);
			return;
		}
		
		computer = computerService.createComputer(new Computer(computerName, computerIntroducedTimestamp,
				computerDiscontinuedTimestamp, computerCompany_idInt));

		if (computer.isEmpty() == false) {
			System.out.println("Computer added with success.");
		} else {
			System.out.println("Computer not added to the database!");
		}
	}

	private void updateComputer() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String computerId = null;
		int computerIdInt = 0;
		String computerName = null;
		String computerIntroduced = null;
		String computerDiscontinued = null;
		Timestamp computerIntroducedTimestamp = null;
		Timestamp computerDiscontinuedTimestamp = null;
		String computerCompany_id = null;
		int computerCompany_idInt = 0;
		Computer computer = null;

		System.out.printf("Enter the machine id:%n>");
		computerId = input.nextLine();
		
		try {
			computerIdInt = Integer.valueOf(computerId);
		} catch (IllegalArgumentException e) {
			System.out.println(FORMAT_ERROR);
			return;
		}

		System.out.println("You have chosen to update the following computer:");
		displayComputerDetails(computerIdInt);

		System.out.printf("Enter the computer name:%n>");
		computerName = input.nextLine();

		System.out.printf("Enter the computer introduced date: format YYYY-MM-DD HH:MM:SS%n>");
		computerIntroduced = input.nextLine();

		try {
			computerIntroducedTimestamp = Timestamp.valueOf(computerIntroduced);
		} catch (IllegalArgumentException e) {
			System.out.println(FORMAT_ERROR);
			return;
		}
		
		System.out.printf("Enter the computer discontinued date: format YYYY-MM-DD HH:MM:SS%n>");
		computerDiscontinued = input.nextLine();
		
		try {
			computerDiscontinuedTimestamp = Timestamp.valueOf(computerDiscontinued);
		} catch (IllegalArgumentException e) {
			System.out.println(FORMAT_ERROR);
			return;
		}
		
		System.out.printf("Enter the computer company id:%n>");
		computerCompany_id = input.nextLine();
		
		try {
			computerCompany_idInt = Integer.valueOf(computerCompany_id);
		} catch (IllegalArgumentException e) {
			System.out.println(FORMAT_ERROR);
			return;
		}
		
		computer = computerService.updateComputer(new Computer(computerIdInt, computerName, computerIntroducedTimestamp,
				computerDiscontinuedTimestamp, computerCompany_idInt));

		if (computer.isEmpty() == false) {
			System.out.println("Computer updated with success.");
		} else {
			System.out.println("Computer not updated from the database!");
		}
	}

	private void displayComputerDetails(int id) {
		Computer computer = computerService.getComputer(id);

		if (computer.isEmpty() == true) {
			System.out.println("No computer found.");
		} else {
			System.out.println(computer.toString());
		}
	}

	private void deleteComputer() {
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String comupterId = null;
		int comupterIdInt = 0;
		boolean success = false;

		System.out.printf("Enter the machine id to delete:%n>");
		comupterId = input.nextLine();
		
		try {
			comupterIdInt = Integer.valueOf(comupterId);
		} catch (IllegalArgumentException e) {
			System.out.println(FORMAT_ERROR);
			return;
		}

		success = computerService.deleteComputer(computerService.getComputer(comupterIdInt));

		if (success == true) {
			System.out.println("Computer deleted with success.");
		} else {
			System.out.println("Computer not deleted from the database!");
		}
	}
}
