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
 * Servlet implementation class EditComputer
 */
@WebServlet("/EditComputer")
public class EditComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputer() {
        super();
        // TODO Auto-generated constructor stub
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
			
			return;
		}
		
		ComputerDTO computer = ComputerService.getInstance().get(id);
		List<CompanyDTO> companies = CompanyService.getInstance().getAll();

		requestParameter.setComputerDTO(computer);
		requestParameter.setCompanies(companies);
	}

	private void updateComputer(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestParameter requestParameter = new RequestParameter(request);
		ComputerDTO c1 = new RequestParameter(request).getComputerDTOWithId();

		Validation validation = new ComputerDTOValidator(c1).check().getValidation();

		if (validation.getMessages().isEmpty()) {
			ComputerService.getInstance().update(c1.toEntity());
			validation.addMessages("Success computer updated to the DB");
		}
		
		validation.displayError();
		requestParameter.setErrorMessage(validation);
	}
	
	private void showPage(HttpServletRequest request, HttpServletResponse response, String page)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/views/" + page).forward(request, response);
	}
}
