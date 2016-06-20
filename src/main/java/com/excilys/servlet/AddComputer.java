package com.excilys.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
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

import com.excilys.bean.ComputerDTO;
import com.excilys.service.interfaces.CompanyService;
import com.excilys.service.interfaces.ComputerService;
import com.excilys.validation.ComputerValidation;

/**
 * Servlet implementation class AddComputer
 */
@Controller
@RequestMapping("/AddComputer")
public class AddComputer extends HttpServlet {
	@Autowired  
	private ComputerService computerService;  
	@Autowired  
	private CompanyService companyService;  
	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;
	
	@RequestMapping(method = RequestMethod.GET)
	protected String doGet(HttpServletRequest request, Model model) {
		model.addAttribute(new ComputerDTO());
		model.addAttribute("companies", companyService.getAll());
		return "addComputer";
	}

	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(HttpServletRequest request, @Valid @ModelAttribute("computerDTO") ComputerDTO computerTosave, BindingResult result, Model model) {
		// Business verification
		if (ComputerValidation.date(computerTosave) == false) {
			result.rejectValue("introduced", "user.error", messageSource.getMessage("computer.date.error", null, LocaleContextHolder.getLocale()));
		}
		
		if (result.hasErrors() == false) {
			computerService.create(computerTosave.toEntity());
			
			model.addAttribute("saveStatus", messageSource.getMessage("computer.save.success", null, LocaleContextHolder.getLocale()));
		} else {
			model.addAttribute("saveStatus", messageSource.getMessage("computer.save.fail", null, LocaleContextHolder.getLocale()));
		}

		model.addAttribute("companies", companyService.getAll());
		return "addComputer";
	}
}
