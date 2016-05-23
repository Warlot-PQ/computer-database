package com.excilys.cli;

import java.util.HashMap;
import java.util.stream.Collectors;

/**
 * CLI implementation
 * 
 * @author pqwarlot
 *
 */
public class CommandFactory {
	private final HashMap<String, Command>	commands = new HashMap<>();
	
	private CommandFactory() {		
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
	
	/* Factory pattern */
	public static CommandFactory init() {
		CommandFactory cf = new CommandFactory();

		cf.addCommand("List_computers", new ComputerListAllCommand());
		cf.addCommand("List_companies", new CompanyListAllCommand());
		cf.addCommand("Show_computer_details", new ComputerListOneCommand());
		cf.addCommand("Create_a_computer", new ComputerCreateCommand());
		cf.addCommand("Update_a_computer", new ComputerUpdateCommand());
		cf.addCommand("Delete_a_computer", new ComputerDeleteCommand());		
		
		return cf;
	}
}
