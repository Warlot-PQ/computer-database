package org.excilys.cli;

import java.util.Scanner;

public class CDB_launcher {
	/**
	 * Program entry point
	 * 
	 * @param args
	 *            arguments to parse, only the first one is used
	 */
	public static void main(String[] args) {
		Command command = new Command();
		String firstArg = null;
		
		System.out.println("Welcome to CDB program :)");
		
		if (args.length > 0) {
			firstArg = args[0];

			System.out.println("You have entered: " + firstArg + ".");
			command.dispatch(firstArg);
		} else {
			@SuppressWarnings("resource")
			Scanner input = new Scanner(System.in);
			
			// Infinite loop until the user enter "exit" 
			while (true) {
				System.out.printf("%nPlease enter your command: (enter anything for help)%n>");
				firstArg = input.nextLine();
				
				command.dispatch(firstArg);
			}
		}
		System.out.println("Bye bye.");
	}
}
