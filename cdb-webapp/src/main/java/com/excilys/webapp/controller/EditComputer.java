package com.excilys.webapp.controller;

import java.util.List;
import java.util.Locale;

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
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.core.dto.CompanyDTO;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.service.service.interfaces.CompanyService;
import com.excilys.service.service.interfaces.ComputerService;
import com.excilys.service.service.interfaces.ComputerService.ErrorMessage;
import com.excilys.binding.ComputerMapper;

/**
 * Servlet implementation class EditComputer
 */
@Controller
@RequestMapping("/EditComputer")
public class EditComputer {  
	@Autowired
	private ComputerService computerService;  
	@Autowired
	private CompanyService companyService;
	@Autowired
	private ReloadableResourceBundleMessageSource messageSource;
	
	/**
	 * Map GET HTML request to display the computer edition page
	 * @param computerDTO ComputerDTO object, id field is auto-mapped with id parameter in GET request
	 * @return JSP page to display
	 */
		
	@RequestMapping(method = RequestMethod.GET)
	protected String displayEditPage(@RequestParam("id") long id, Model model, Locale locale) {
		ComputerDTO computer = computerService.get(id);
		
		if (computer == null) {
			computer = new ComputerDTO();
		}
		
		List<CompanyDTO> companies = companyService.getAll();
		model.addAttribute("companies", companies);
		model.addAttribute("computerDTO", computer);
		return "editComputer";
	}

	/**
	 * Map POST HTML request to edit a computer and display the computer edition page
	 * @param computerDTO @Valid @ModelAttribute("computerDTO")
	 * @param result 
	 * @param request
	 * @param model
	 * @return JSP page to display
	 */
	@RequestMapping(method = RequestMethod.POST)
	protected String doPost(@Valid @ModelAttribute() ComputerDTO computerDTO, BindingResult result, 
			Model model) {

		if (result.hasErrors() == false) {
			updateComputer(computerDTO, result, model);
		} else {
			model.addAttribute("updateStatus", messageSource.getMessage("computer.update.fail", null, LocaleContextHolder.getLocale()));
		}

		List<CompanyDTO> companies = companyService.getAll();
		model.addAttribute("companies", companies);
		return "editComputer";
	}
	
	private void updateComputer(ComputerDTO computerToUpdate, BindingResult result, Model model) {		
		// Business verification only
		if (computerService.update( ComputerMapper.toEntity(computerToUpdate) ) == ErrorMessage.NONE) {
			model.addAttribute("updateStatus", messageSource.getMessage("computer.update.success", null, LocaleContextHolder.getLocale()));
		} else {
			result.rejectValue("introduce", "user.error", messageSource.getMessage("computer.date.error", null, LocaleContextHolder.getLocale()));
			model.addAttribute("updateStatus", messageSource.getMessage("computer.update.fail", null, LocaleContextHolder.getLocale()));
		}
	}
}
