package com.excilys.console.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.excilys.console.cli.CommandFactory;
import com.excilys.console.cli.CompanyListAllCommand;
import com.excilys.console.cli.ComputerCreateCommand;
import com.excilys.console.cli.ComputerDeleteCommand;
import com.excilys.console.cli.ComputerListAllCommand;
import com.excilys.console.cli.ComputerListOneCommand;
import com.excilys.console.cli.ComputerUpdateCommand;
import com.excilys.console.cli.ExitCommand;

@Configuration
@ComponentScan(basePackages = { "com.excilys" })
public class ConsoleConfig {

	@Bean
	@Scope("singleton")
	public CommandFactory getCommandFactory() {
		return new CommandFactory(new ComputerListAllCommand(), new CompanyListAllCommand(), 
				new ComputerListOneCommand(), new ComputerCreateCommand(), 
				new ComputerUpdateCommand(), new ComputerDeleteCommand(),
				new ExitCommand());
	}
}