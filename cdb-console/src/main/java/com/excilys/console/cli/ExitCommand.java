package com.excilys.console.cli;

public class ExitCommand implements Command {

	@Override
	public void execute() {
		System.out.println("Bye bye.");
		System.exit(0);
	}

}