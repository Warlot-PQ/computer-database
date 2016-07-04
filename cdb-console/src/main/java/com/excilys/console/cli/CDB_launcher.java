package com.excilys.console.cli;

import java.util.Scanner;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.console.config.ConsoleConfig;

public class CDB_launcher {
	public static ConfigurableApplicationContext applicationContext;
	/**
	 * Program entry point
	 * 
	 * @param args
	 *            arguments to parse, only the first one is used
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		applicationContext = new AnnotationConfigApplicationContext(ConsoleConfig.class);	
		CommandFactory cf = applicationContext.getBean(CommandFactory.class);
		String firstArg = null;
		
		System.out.println("Welcome to CDB program :)");

		if (args.length > 0) {
			firstArg = args[0];

			System.out.println("You have entered: " + firstArg + ".");
			cf.executeCommand(firstArg, input);
		} else {
			// Infinite loop until the user enter "exit"
			do {
				System.out.printf("%nPlease enter your command: (enter anything for help)%n>");
				firstArg = input.nextLine();
				
				if (firstArg.equals("Exit") == false) {
					cf.executeCommand(firstArg, input);
				}
			}
			while (firstArg.equals("Exit") == false);
		}
		System.out.println("Bye bye.");
		input.close();
		applicationContext.close();
	}
}
