package com.excilys.cli;

import java.util.ArrayList;
import java.util.List;

import com.excilys.bean.ComputerDTO;
import com.excilys.service.ComputerService;
import com.excilys.service.PageRequest;

public class ComputerListAllCommand implements Command {

	@Override
	public void execute() {
		List<ComputerDTO> computers = new ArrayList<>();

		computers = ComputerService.getInstance().getAll(PageRequest.create().build());

//		Page<Computer, ComputerDTO> p = new Page<>(ComputerService.getInstance());
//		try {
//			computers = p.nextPage();
//		} catch (DAOException | ConnectionException | DriverException e) {
//			e.printStackTrace();
//		}

		for (ComputerDTO computer : computers) {
			System.out.println(computer.toString());
		}
	}

}
