package com.excilys.cli;

import java.util.Scanner;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.spring.AppConfig;

public class CDB_launcher {
	public static ConfigurableApplicationContext applicationContext;
	/**
	 * Program entry point
	 * 
	 * @param args
	 *            arguments to parse, only the first one is used
	 */
	public static void main(String[] args) {
		applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);	
		CommandFactory cf = applicationContext.getBean(CommandFactory.class);
		String firstArg = null;
		
		System.out.println("Welcome to CDB program :)");

		if (args.length > 0) {
			firstArg = args[0];

			System.out.println("You have entered: " + firstArg + ".");
			cf.executeCommand(firstArg);
		} else {
			@SuppressWarnings("resource")
			Scanner input = new Scanner(System.in);

			// Infinite loop until the user enter "exit"
			while (true) {
				System.out.printf("%nPlease enter your command: (enter anything for help)%n>");
				firstArg = input.nextLine();

				cf.executeCommand(firstArg);
			}
		}
		System.out.println("Bye bye.");
		applicationContext.close();
	}
}
