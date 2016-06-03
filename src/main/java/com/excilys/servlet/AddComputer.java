package com.excilys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.bean.CompanyDTO;
import com.excilys.bean.ComputerDTO;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.service.mapper.RequestParameter;
import com.excilys.validation.ComputerDTOValidator;
import com.excilys.validation.Validation;

/**
 * Servlet implementation class AddComputer
 */
@WebServlet("/AddComputer")
public class AddComputer extends HttpServlet { 
	private static ComputerService COMPUTER_SERVICE = ComputerService.getInstance();  
	private static CompanyService COMPANY_SERVICE = CompanyService.getInstance();
			
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddComputer() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		setAddComputerJSP(request, response);
		showPage(request, response, "addComputer.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		saveComputer(request, response);
		setAddComputerJSP(request, response);
		showPage(request, response, "addComputer.jsp");
	}
	
	private void setAddComputerJSP(HttpServletRequest request, HttpServletResponse response) {
		List<CompanyDTO> companies = COMPANY_SERVICE.getAll();

		new RequestParameter(request).setCompanies(companies);
	}	

	private void saveComputer(HttpServletRequest request, HttpServletResponse response) {
		ComputerDTO c1 = new RequestParameter(request).getComputerDTO();

		Validation validation = new ComputerDTOValidator(c1).check().getValidation();

		if (validation.getMessages().isEmpty()) {
			COMPUTER_SERVICE.create(c1.toEntity());
			validation.addMessages("Success computer added to the DB");
		}
		
		validation.displayError();
		new RequestParameter(request).setErrorMessage(validation);
	}
	
	private void showPage(HttpServletRequest request, HttpServletResponse response, String page)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/views/" + page).forward(request, response);
	}
}
