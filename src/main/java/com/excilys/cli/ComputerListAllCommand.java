package com.excilys.cli;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.Pagination.PageRequest;
import com.excilys.bean.ComputerDTO;
import com.excilys.service.interfaces.ComputerService;

public class ComputerListAllCommand implements Command {
	@Autowired
	private ComputerService computerService;
	
	@Override
	public void execute() {
		List<ComputerDTO> computers = new ArrayList<>();

		computers = computerService.getAll(PageRequest.create().build());

		for (ComputerDTO computer : computers) {
			System.out.println(computer.toString());
		}
	}

}
