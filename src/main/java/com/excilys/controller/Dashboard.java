package com.excilys.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.DTO.ComputerDTO;
import com.excilys.controller.mapper.RequestModel;
import com.excilys.pagination.Page;
import com.excilys.pagination.PageRequest;
import com.excilys.service.interfaces.ComputerService;
import com.excilys.utils.DateMapper;

/**
 * Servlet implementation class Dashboard
 */
@Controller
@RequestMapping("/Dashboard")
public class Dashboard {   
	@Autowired
	private ComputerService computerService;
	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(@Valid @ModelAttribute RequestModel requestModel, BindingResult result, Model model) {
		if (result.hasErrors()) {
			requestModel.reset();
		}
		
		if (requestModel.getSearch() != null) {
			prepareSearchJSP(requestModel, model);
		} else {
			prepareSimpleJSP(requestModel, model);
		}		
		return "dashboard";
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(@ModelAttribute RequestModel requestModel, Model model) {
		deleteComputer(requestModel);
		model.addAttribute("deleteStatus", messageSource.getMessage("computer.delete.success", null, LocaleContextHolder.getLocale()));
		
		if (requestModel.getSearch() != null) {
			prepareSearchJSP(requestModel, model);
		} else {
			prepareSimpleJSP(requestModel, model);
		}		
		return "dashboard";
	}

	private void prepareSimpleJSP(RequestModel requestModel, Model model) {
		Page<ComputerDTO> page = computerService.getPage(PageRequest.create()
				.page(requestModel.getPage())
				.eltByPage(requestModel.getLimit())
				.orderBy(requestModel.getOrderBy())
				.orderAlphaNumerical(requestModel.isOrderAlphaNum())
				.build());
		model.addAttribute("page", page);
	}

	private void prepareSearchJSP(RequestModel requestModel, Model model) {
		Page<ComputerDTO> page = computerService.getPage(PageRequest.create()
				.computerSeachedName(requestModel.getSearch())
				.page(requestModel.getPage())
				.eltByPage(requestModel.getLimit())
				.orderBy(requestModel.getOrderBy())
				.orderAlphaNumerical(requestModel.isOrderAlphaNum())
				.build());
		model.addAttribute("page", page);
	}
	
	private void deleteComputer(RequestModel requestModel) {
		String computersToDelete = requestModel.getSelection();
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
