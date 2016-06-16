package com.excilys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.bean.CompanyDTO;
import com.excilys.bean.ComputerDTO;
import com.excilys.service.interfaces.CompanyService;
import com.excilys.service.interfaces.ComputerService;
import com.excilys.servlet.mapper.RequestParamExtractor;
import com.excilys.validation.ComputerDTOValidator;
import com.excilys.validation.Validation;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/EditComputer")
public class EditComputer extends HttpServlet {  
	@Autowired
	private ComputerService computerService;  
	@Autowired
	private CompanyService companyService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EditComputer.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputer() {
        super();
    }
    
    @Override
	public void init(ServletConfig config) throws ServletException {
    	super.init(config);	
    	SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setEditComputer(request, response);
		showPage(request, response, "editComputer.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		updateComputer(request, response);
		doGet(request, response);
	}
	
	private void setEditComputer(HttpServletRequest request, HttpServletResponse response) {
//		RequestParameter requestParameter = new RequestParameter(request);
		RequestParamExtractor requestParameter = new RequestParamExtractor(request);
				
		Long id = requestParameter.getId();
		
		if (id == null) {
			LOGGER.debug("Id null found.");
			return;
		}
		
		ComputerDTO computer = computerService.get(id);
		List<CompanyDTO> companies = companyService.getAll();

		requestParameter.setComputerDTO(computer);
		requestParameter.setCompanies(companies);
	}

	private void updateComputer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestParamExtractor requestParameter = new RequestParamExtractor(request);
		ComputerDTO c1 = requestParameter.getComputerDTOWithId();

		Validation validation = new ComputerDTOValidator(c1).checkAll().getValidation();

		if (validation.getMessages().isEmpty()) {
			computerService.update(c1.toEntity());
			validation.addMessages("Success computer updated to the DB");
		} else {
			LOGGER.debug("Invalide computer detected, update aborted.");
		}
		
		validation.displayError();
		requestParameter.setErrorMessage(validation);
	}
	
	private void showPage(HttpServletRequest request, HttpServletResponse response, String page)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/views/" + page).forward(request, response);
	}
}
