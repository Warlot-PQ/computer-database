package com.excilys.webapp.controller;

import javax.servlet.http.HttpServlet;
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

import com.excilys.binding.ComputerMapper;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.service.service.CompanyService;
import com.excilys.service.service.ComputerService;
import com.excilys.service.service.ComputerService.ErrorMessage;

/**
 * Servlet implementation class AddComputer
 */
@Controller
@RequestMapping("/add_computer")
public class AddComputer extends HttpServlet {
	@Autowired  
	private ComputerService computerService;  
	@Autowired  
	private CompanyService companyService;  
	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;
	
	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(Model model) {
		model.addAttribute(new ComputerDTO());
		model.addAttribute("companies", companyService.getAll());
		return "addComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(@Valid @ModelAttribute("computerDTO") ComputerDTO computerTosave, BindingResult result, Model model) {
		if (result.hasErrors() == false) {
			if (computerService.create(ComputerMapper.toEntity(computerTosave)) == ErrorMessage.NONE) {
				model.addAttribute("saveStatus", messageSource.getMessage("computer.save.success", null, LocaleContextHolder.getLocale()));
			} else {
				result.rejectValue("introduced", "user.error", messageSource.getMessage("computer.date.error", null, LocaleContextHolder.getLocale()));
				model.addAttribute("saveStatus", messageSource.getMessage("computer.save.fail", null, LocaleContextHolder.getLocale()));
			}
		} else {
			model.addAttribute("saveStatus", messageSource.getMessage("computer.save.fail", null, LocaleContextHolder.getLocale()));
		}

		model.addAttribute("companies", companyService.getAll());
		return "addComputer";
	}
}
