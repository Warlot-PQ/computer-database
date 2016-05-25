package com.excilys.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.beans.Computer;
import com.excilys.beans.ComputerDTO;
import com.excilys.cli.Page;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;
import com.excilys.service.ComputerService;

/**
 * Servlet implementation class Router
 */
@WebServlet("/Router")
public class Router extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Router() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = "";
		String actionParam = request.getParameter("action");
		if (actionParam == null) {
			actionParam = "dashboard";
		}
		
		switch(actionParam) {
			case "dashboard":
				//TODO check param before dispatching
				setDashboard(request, response, 
						request.getParameter("page"), 
						request.getParameter("limit"));
				page = "views/dashboard.jsp";
				break;
			case "addComputer":
				page = "views/addComputer.jsp";
				break;
			default:
				setDashboard(request, response, "1", "10");
				page = "views/dashboard.jsp";
		}
		showPage(request, response, page);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public void setDashboard(HttpServletRequest request, HttpServletResponse response, String pageParam, String limitParam) throws ServletException, IOException {
		List<ComputerDTO> computers = null;
		int currentPage = getParamToInt(pageParam);
		int currentLimit = getParamToInt(limitParam);
		if (currentLimit == 1) { currentLimit *= 20; } // set to 20 if cannot convert to int
		int currentComputersFrom = (currentPage - 1) * currentLimit;
		int currentComputersTo = 1;
		int totalPages = 1;
		int totalComputers = 1;
		boolean loadError = false;
		Page<Computer, ComputerDTO> page = new Page<>(ComputerService.getInstance(), currentLimit);
		
		try {
			computers = page.getPage(currentPage);
			totalPages = page.getTotalPages();
			totalComputers = ComputerService.getInstance().count();
		} catch (DAOException | ConnectionException | DriverException e) {
			e.printStackTrace();
			loadError = true;
		}
		
		currentComputersTo = (currentPage == totalPages) ? totalComputers % currentLimit : currentLimit;
		currentComputersTo += currentComputersFrom;
		
		request.setAttribute("loadError", loadError);
		request.setAttribute("computers", computers);
		request.setAttribute("currentPage", currentPage);
		request.setAttribute("currentLimit", currentLimit);
		request.setAttribute("currentComputersFrom", currentComputersFrom);
		request.setAttribute("currentComputersTo", currentComputersTo);
		request.setAttribute("totalPages", totalPages);
		request.setAttribute("totalComputers", totalComputers);
	}
	
	public void showPage(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
		request.getRequestDispatcher(page).forward(request, response);
	}

	private int getParamToInt(String pageParam) {
		int number = 1;
		
		if (pageParam != null && pageParam.trim().equals("") == false) {
			try {
				number = Integer.valueOf(pageParam);
			} catch (IllegalArgumentException e) {
				System.out.println("Incorrect format!");
				number = 1;
			}
		} else if (pageParam != null && pageParam.equals("") == true) {
			number = 1;
		}
		
		return number;
	}
}
