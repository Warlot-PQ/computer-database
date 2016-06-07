package com.excilys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.bean.CompanyDTO;
import com.excilys.bean.ComputerDTO;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.service.mapper.RequestParameter;
import com.excilys.validation.ComputerDTOValidator;
import com.excilys.validation.Validation;

/**
 * Servlet implementation class EditComputer
 */
@WebServlet("/EditComputer")
public class EditComputer extends HttpServlet {
	private static Logger LOGGER = null;  
	private static ComputerService COMPUTER_SERVICE = ComputerService.getInstance();
	private static CompanyService COMPANY_SERVICE = CompanyService.getInstance();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputer() {
        super();
		LOGGER = LoggerFactory.getLogger(this.getClass());
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
		RequestParameter requestParameter = new RequestParameter(request);
		Long id = requestParameter.getId();
		
		if (id == null) {
			LOGGER.debug("Id null found.");
			return;
		}
		
		ComputerDTO computer = COMPUTER_SERVICE.get(id);
		List<CompanyDTO> companies = COMPANY_SERVICE.getAll();

		requestParameter.setComputerDTO(computer);
		requestParameter.setCompanies(companies);
	}

	private void updateComputer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestParameter requestParameter = new RequestParameter(request);
		ComputerDTO c1 = new RequestParameter(request).getComputerDTOWithId();

		Validation validation = new ComputerDTOValidator(c1).checkAll().getValidation();

		if (validation.getMessages().isEmpty()) {
			COMPUTER_SERVICE.update(c1.toEntity());
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
