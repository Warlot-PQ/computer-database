package com.excilys.console.cli;

import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CLI implementation with command pattern. Singleton class.
 * 
 * @author pqwarlot
 *
 */
public class CommandFactory {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommandFactory.class);
	private final HashMap<String, Command> commands = new HashMap<>();

	public CommandFactory(ComputerListAllCommand computerListAllCommand,
			CompanyListAllCommand companyListAllCommand,
			ComputerListOneCommand computerListOneCommand,
			ComputerCreateCommand computerCreateCommand,
			ComputerUpdateCommand computerUpdateCommand,
			ComputerDeleteCommand computerDeleteCommand,
			ExitCommand exitCommand) {
		addCommand("List_computers", computerListAllCommand);
		addCommand("List_companies", companyListAllCommand);
		addCommand("Show_computer_details", computerListOneCommand);
		addCommand("Create_a_computer", computerCreateCommand);
		addCommand("Update_a_computer", computerUpdateCommand);
		addCommand("Delete_a_computer", computerDeleteCommand);
	}

	public void addCommand(String name, Command command) {
		commands.put(name, command);
	}

	public void executeCommand(String name, Scanner input) {
		LOGGER.info("Command: " + name + " requested.");
		if (commands.containsKey(name)) {
			LOGGER.info("command found.");
			commands.get(name).execute(input);
		} else {
			LOGGER.info("command not found.");
			System.out.println("Unknow command, please use one of the following");
			this.listCommands();
		}
	}

	public void listCommands() {
		System.out.println("Enabled commands: " + commands.keySet().stream().collect(Collectors.joining(", ")));
	}
}
