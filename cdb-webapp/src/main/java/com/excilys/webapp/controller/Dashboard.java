package com.excilys.webapp.controller;

import java.util.Collection;
import java.util.Locale;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.excilys.core.date.DateMapper;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.persistence.pagination.PageRequest;
import com.excilys.service.pagination.Page;
import com.excilys.service.service.ComputerService;
import com.excilys.webapp.controller.mapper.RequestModel;
import com.excilys.webapp.message.Messages;

/**
 * Servlet implementation class Dashboard
 */
@Controller
@RequestMapping(value = {"/dashboard", "/"})
public class Dashboard {
	@Autowired
	private ComputerService computerService;
	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;

	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(@Valid @ModelAttribute RequestModel requestModel, BindingResult result, Model model, 
			Locale locale) {
		if (result.hasErrors()) {
			requestModel.reset();
		}
		
		if (requestModel.getSearch() != null) {
			prepareSearchJSP(requestModel, model);
		} else {
			prepareSimpleJSP(requestModel, model);
		}		
		
		model.addAttribute("langMsg", new Messages(locale.getLanguage()).getMessages());
		
		String roles = "";		
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>) SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		for (SimpleGrantedAuthority authoritie: authorities) {
			roles += authoritie.getAuthority();
		}
		
		model.addAttribute("role", roles);
		model.addAttribute("user", SecurityContextHolder.getContext().getAuthentication().getName());
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
				.seachedName(requestModel.getSearch())
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
