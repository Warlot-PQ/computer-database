package com.excilys.cli;

import java.util.ArrayList;
import java.util.List;

import com.excilys.beans.Computer;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;
import com.excilys.service.ComputerService;

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
