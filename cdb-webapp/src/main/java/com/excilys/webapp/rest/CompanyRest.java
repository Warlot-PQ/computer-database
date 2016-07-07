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

import com.excilys.core.dto.CompanyDTO;
import com.excilys.core.entity.Company;
import com.excilys.persistence.pagination.PageRequest;
import com.excilys.service.pagination.Page;
import com.excilys.service.service.CompanyService;

@RestController
public class CompanyRest {
	@Autowired
	CompanyService companyService;
	
	@RequestMapping(value = URIConstants.GET_COMPANY_PAGE, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public Page<CompanyDTO> listComputerPage(@PathVariable int pageNumber, @PathVariable int limit) {
		return companyService.getPage(PageRequest.create().page(pageNumber).eltByPage(limit).build());	
	}
	
	@RequestMapping(value = URIConstants.GET_COMPANY_ALL, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<CompanyDTO> listComputer() {
		return companyService.getAll();
	}
	
	@RequestMapping(value = URIConstants.GET_COMPANY_BY_ID, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public CompanyDTO getComputer(@PathVariable long id) {
		return companyService.get(id);
	}
	
	@RequestMapping(value = URIConstants.CREATE_COMPANY, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String createComputer(@RequestBody Company company) {
		companyService.create(company);
		return "request completed.";
	}
		
	@RequestMapping(value = URIConstants.DELETE_COMPANY_BY_ID, method = RequestMethod.DELETE)
	public @ResponseBody String deleteComputer(@PathVariable long id) {
		companyService.delete(id);
		return "request completed.";
	}
	
	@RequestMapping(value = URIConstants.UPDATE_COMPANY, method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody String updateComputer(@RequestBody Company company) {
		companyService.update(company);
		return "request completed.";
	}	
}
