package com.excilys.cli;

import java.util.ArrayList;
import java.util.List;

import com.excilys.beans.Computer;
import com.excilys.beans.ComputerExt;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;
import com.excilys.service.ComputerService;

public class ComputerListAllCommand implements Command {

	@Override
	public void execute() {
		List<ComputerExt> computers = new ArrayList<>();
				
		try {
			computers = ComputerService.getInstance().getAll();
		} catch (DAOException | ConnectionException | DriverException e) {
			System.out.println("DB error!");
			return;
		}
		
		Page<Computer, ComputerExt> p = new Page<>(ComputerService.getInstance());
		try {
			computers = p.nextPage();
		} catch (DAOException | ConnectionException | DriverException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (ComputerExt computer : computers) {
			System.out.println(computer.toString());
		}
	}
	
}
