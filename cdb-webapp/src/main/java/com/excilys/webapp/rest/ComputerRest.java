package com.excilys.webapp.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.entity.Computer;
import com.excilys.persistence.pagination.PageRequest;
import com.excilys.service.pagination.Page;
import com.excilys.service.service.interfaces.ComputerService;

@RestController
public class ComputerRest {
	@Autowired
	ComputerService computerService;
	
	@RequestMapping(value = URIConstants.GET_COMPUTER_PAGE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<ComputerDTO> ListComputerPage(@PathVariable int pageNumber, @PathVariable int limit) {
		return computerService.getPage(PageRequest.create().page(pageNumber).eltByPage(limit).build());		
	}
	
	@RequestMapping(value = URIConstants.GET_COMPUTER_ALL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ComputerDTO> ListComputer() {
		return computerService.getAll();
	}
	
	@RequestMapping(value = URIConstants.GET_COMPUTER_BY_ID, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ComputerDTO GetComputer(@PathVariable long id) {
		return computerService.get(id);
	}
	
	@RequestMapping(value = URIConstants.CREATE_COMPUTER, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String createComputer(@RequestBody Computer computer) {
		computerService.create(computer);
		return "request completed.";
	}
		
	@RequestMapping(value = URIConstants.DELETE_COMPUTER_BY_ID, method = RequestMethod.DELETE)
	public @ResponseBody String DeleteComputer(@PathVariable long id) {
		computerService.delete(id);
		return "request completed.";
	}
	
	@RequestMapping(value = URIConstants.UPDATE_COMPUTER, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String UpdateComputer(@RequestBody Computer computer) {
		computerService.update(computer);
		return "request completed.";
	}	
}
