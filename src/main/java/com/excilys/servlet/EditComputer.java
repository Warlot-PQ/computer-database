package com.excilys.servlet;

import java.util.List;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.excilys.bean.CompanyDTO;
import com.excilys.bean.ComputerDTO;
import com.excilys.service.interfaces.CompanyService;
import com.excilys.service.interfaces.ComputerService;
import com.excilys.validation.ComputerValidation;

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
	
	/**
	 * Map GET HTML request to display the computer edition page
	 * @param computerDTO ComputerDTO object, id field is auto-mapped with id parameter in GET request
	 * @return JSP page to display
	 */
		
	@RequestMapping(method = RequestMethod.GET)
	protected String displayEditPage(@RequestParam("id") String id, Model model) {
		ComputerDTO computer = null;
		long idWanted = 1;
		if (id != null && StringUtils.isNumeric(id) == true 
				&& Long.parseLong(id) > 0) {		
			// Get ComputerDTO corresponding to the id wanted
			idWanted = Long.parseLong(id);
		} 
		computer = computerService.get(idWanted);
		
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
		}	

		List<CompanyDTO> companies = companyService.getAll();
		model.addAttribute("companies", companies);
		return "editComputer";
	}

	private void updateComputer(ComputerDTO computerToUpdate, BindingResult result, Model model) {		
		// Business verification only
		if (ComputerValidation.date(computerToUpdate) == false) {
			result.rejectValue("introduced", "user.error", "introduced date must be before discontinued date");
		} else {
			computerService.update(computerToUpdate.toEntity());
			model.addAttribute("updateSucceded", "Computer updaded with success :)");
		}
	}
}
