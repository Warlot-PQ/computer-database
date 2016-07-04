package com.excilys.console.cli;

import java.util.Scanner;

public class ExitCommand implements Command {

	@Override
	public void execute(Scanner input) {
		System.out.println("Bye bye.");
		System.exit(0);
	}

}
