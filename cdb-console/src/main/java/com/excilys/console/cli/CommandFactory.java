package com.excilys.console.cli;

import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * CLI implementation with command pattern. Singleton class.
 * 
 * @author pqwarlot
 *
 */
public class CommandFactory {
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
		addCommand("Exit", exitCommand);
	}

	public void addCommand(String name, Command command) {
		commands.put(name, command);
	}

	public void executeCommand(String name) {
		if (commands.containsKey(name)) {
			commands.get(name).execute();
		} else {
			System.out.println("Unknow command, please use one of the following");
			this.listCommands();
		}
	}

	public void listCommands() {
		System.out.println("Enabled commands: " + commands.keySet().stream().collect(Collectors.joining(", ")));
	}
}
