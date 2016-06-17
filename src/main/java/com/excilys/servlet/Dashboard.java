package com.excilys.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.Pagination.Page;
import com.excilys.Pagination.PageRequest;
import com.excilys.bean.ComputerDTO;
import com.excilys.bean.mapper.DateMapper;
import com.excilys.service.interfaces.ComputerService;
import com.excilys.servlet.mapper.RequestParamExtractor;

/**
 * Servlet implementation class Dashboard
 */
@Controller
@RequestMapping("/Dashboard")
public class Dashboard {   
	@Autowired
	private ComputerService computerService;

	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(HttpServletRequest request) throws ServletException, IOException {
		String searchedElt = new RequestParamExtractor(request).getSearchElt();
		if (searchedElt != null) {
			setSearch(request);
		} else {
			setDashboard(request);
		}		
		return "dashboard";
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(HttpServletRequest request) throws ServletException, IOException {
		deleteComputer(request);
		setDashboard(request);
		return "dashboard";
	}

	private void setDashboard(HttpServletRequest request) throws ServletException, IOException {
		RequestParamExtractor requestParameter = new RequestParamExtractor(request);
		Page<ComputerDTO> page = null;
		
		page = computerService.getPage(PageRequest.create()
				.page(requestParameter.getCurrentPage())
				.eltByPage(requestParameter.getLimit())
				.orderBy(requestParameter.getOrderBy())
				.orderAlphaNumerical(requestParameter.getOrderAlphaNumerical())
				.build());
		requestParameter.setPage(page);
		requestParameter.setOrderBy(requestParameter.getOrderBy());
		requestParameter.setOrderAlphaNumerical(requestParameter.getOrderAlphaNumerical().toString());
	}

	private void setSearch(HttpServletRequest request) throws ServletException, IOException {
		RequestParamExtractor requestParameter = new RequestParamExtractor(request);
		Page<ComputerDTO> page = null;
		
		page = computerService.getPage(PageRequest.create()
				.computerSeachedName(new RequestParamExtractor(request).getSearchElt())
				.page(requestParameter.getCurrentPage())
				.eltByPage(requestParameter.getLimit())
				.orderBy(requestParameter.getOrderBy())
				.orderAlphaNumerical(requestParameter.getOrderAlphaNumerical())
				.build());
		
		requestParameter.setPage(page);
		requestParameter.setComputerSearched(new RequestParamExtractor(request).getSearchElt());
	}
	
	private void deleteComputer(HttpServletRequest request) {
		String computersToDelete = new RequestParamExtractor(request).getSelection();
		String[] computersId;
		
		if (computersToDelete != null) {
			computersId = computersToDelete.split(",");
			
			for (String computerId: computersId) {
				Long id = DateMapper.convertStringToLong(computerId);
				if (id != null) {
					computerService.delete(id);
				}
			}
		}
	}
}
