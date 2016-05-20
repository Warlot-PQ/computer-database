package org.excilys.cli;

import java.util.ArrayList;
import java.util.List;

import org.excilys.beans.Computer;
import org.excilys.exceptions.ConnectionException;
import org.excilys.exceptions.DAOException;
import org.excilys.exceptions.DriverException;
import org.excilys.service.ComputerService;

public class ComputerListAllCommand implements Command {

	@Override
	public void execute() {
		List<Computer> computers = new ArrayList<>();
				
		try {
			computers = ComputerService.getInstance().allComputer();
		} catch (DAOException | ConnectionException | DriverException e) {
			System.out.println("DB error!");
			return;
		}

		for (Computer computer : computers) {
			System.out.println(computer.toString());
		}
		
		//new Page<Computer>(computers).startPagination();
	}
	
}
