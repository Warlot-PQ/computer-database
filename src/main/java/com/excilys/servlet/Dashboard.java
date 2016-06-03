package com.excilys.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.Pagination.Page;
import com.excilys.bean.ComputerDTO;
import com.excilys.service.ComputerService;
import com.excilys.service.PageRequest;
import com.excilys.service.mapper.RequestParameter;
import com.excilys.validation.MapperUtils;

/**
 * Servlet implementation class Dashboard
 */
@WebServlet("/Dashboard")
public class Dashboard extends HttpServlet {   
	private static ComputerService COMPUTER_SERVICE = ComputerService.getInstance();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Dashboard() {
        super();
    }

	/**
	 * Display dashboard page, display all computers with pagination system
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if (new RequestParameter(request).getSearchElt() != null) {
			setSearch(request, response);
		} else {
			setDashboard(request, response);
		}		
		showPage(request, response, "dashboard.jsp");
	}

	/**
	 * Delete computer in POST parameters
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		deleteComputer(request, response);
		setDashboard(request, response);
		showPage(request, response, "dashboard.jsp");
	}

	private void setDashboard(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestParameter requestParameter = new RequestParameter(request);
		Page<ComputerDTO> page = null;
		
		page = COMPUTER_SERVICE.getPage(PageRequest.create()
				.page(requestParameter.getCurrentPage())
				.eltByPage(requestParameter.getLimit())
				.orderBy(requestParameter.getOrderBy())
				.orderAlphaNumerical(requestParameter.getOrderAlphaNumerical())
				.build());
		requestParameter.setPage(page);
		requestParameter.setOrderBy(requestParameter.getOrderBy());
		requestParameter.setOrderAlphaNumerical(requestParameter.getOrderAlphaNumerical().toString());
	}

	private void setSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestParameter requestParameter = new RequestParameter(request);
		Page<ComputerDTO> page = null;
		
		page = COMPUTER_SERVICE.getPage(PageRequest.create()
				.computerSeachedName(new RequestParameter(request).getSearchElt())
				.page(requestParameter.getCurrentPage())
				.eltByPage(requestParameter.getLimit())
				.orderBy(requestParameter.getOrderBy())
				.orderAlphaNumerical(requestParameter.getOrderAlphaNumerical())
				.build());
		
		requestParameter.setPage(page);
		requestParameter.setComputerSearched(new RequestParameter(request).getSearchElt());
	}
	
	private void deleteComputer(HttpServletRequest request, HttpServletResponse response) {
		String computersToDelete = new RequestParameter(request).getSelection();
		String[] computersId;
		
		if (computersToDelete != null) {
			computersId = computersToDelete.split(",");
			
			for (String computerId: computersId) {
				Long id = MapperUtils.convertStringToLong(computerId);
				if (id != null) {
					COMPUTER_SERVICE.delete(id);
				}
			}
		}
		//TODO in JSP, if validation.list is empty -> no error. Display if validation.display == true
	}
	
	private void showPage(HttpServletRequest request, HttpServletResponse response, String page)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/views/" + page).forward(request, response);
	}
}
