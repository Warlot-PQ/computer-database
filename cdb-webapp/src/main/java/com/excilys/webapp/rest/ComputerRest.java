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

import com.excilys.binding.ComputerMapper;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.persistence.pagination.PageRequest;
import com.excilys.service.pagination.Page;
import com.excilys.service.service.ComputerService;

@RestController
public class ComputerRest {
	@Autowired
	ComputerService computerService;
	
	@RequestMapping(value = URIConstants.GET_COMPUTER_PAGE, method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<ComputerDTO> listComputerPage(@PathVariable int pageNumber, @PathVariable int limit) {
		return computerService.getPage(PageRequest.create().page(pageNumber).eltByPage(limit).build());		
	}
	
	@RequestMapping(value = URIConstants.GET_COMPUTER_ALL, method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ComputerDTO> listComputer() {
		return computerService.getAll();
	}
	
	@RequestMapping(value = URIConstants.GET_COMPUTER_BY_ID, method = RequestMethod.GET, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ComputerDTO getComputer(@PathVariable long id) {
		return computerService.get(id);
	}
	
	@RequestMapping(value = URIConstants.CREATE_COMPUTER, method = RequestMethod.POST, 
			produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String createComputer(@RequestBody ComputerDTO computerDTO) {
		computerService.create(ComputerMapper.toEntity(computerDTO));
		return "request completed.";
	}
		
	@RequestMapping(value = URIConstants.DELETE_COMPUTER_BY_ID, method = RequestMethod.DELETE,
			produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody String deleteComputer(@PathVariable long id) {
		computerService.delete(id);
		return "request completed.";
	}
	
	@RequestMapping(value = URIConstants.UPDATE_COMPUTER, method = RequestMethod.PUT, 
			produces = MediaType.TEXT_PLAIN_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public String updateComputer(@RequestBody ComputerDTO computerDTO) {
		computerService.update(ComputerMapper.toEntity(computerDTO));
		return "request completed.";
	}	
}
