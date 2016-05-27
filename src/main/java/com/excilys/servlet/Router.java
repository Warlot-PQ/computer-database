package com.excilys.servlet;
import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.beans.CompanyDTO;
import com.excilys.beans.Computer;
import com.excilys.beans.ComputerDTO;
import com.excilys.cli.Page;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;
import com.excilys.service.CompanyService;
import com.excilys.service.ComputerService;
import com.excilys.validation.ComputerValidator;
import com.excilys.validation.Validation;

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
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = "";
		String actionParam = escapeHtml4(request.getParameter("action"));
		if (actionParam == null) {
			actionParam = "dashboard";
		}

		switch (actionParam) {
		case "dashboard":
			setDashboardJSP(request, response, escapeHtml4(request.getParameter("page")), escapeHtml4(request.getParameter("limit")));
			page = "dashboard.jsp";
			request.setAttribute("displayErrorMsg", false);
			break;
		case "addComputer":
			setAddComputerJSP(request, response);
			request.setAttribute("displayErrorMsg", false);
			page = "addComputer.jsp";
			break;
		default:
			setDashboardJSP(request, response, "1", "10");
			request.setAttribute("displayErrorMsg", false);
			page = "dashboard.jsp";
		}
		showPage(request, response, page);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String page = "";
		String actionParam = escapeHtml4(request.getParameter("action"));
		
		switch (actionParam) {
		case "addComputer":
			saveComputer(request, response);
			setAddComputerJSP(request, response);
			request.setAttribute("displayErrorMsg", true);
			page = "addComputer.jsp";
			break;
		case "deletion":
			deleteComputer(request, response, escapeHtml4(request.getParameter("selection")));
			setDashboardJSP(request, response, escapeHtml4(request.getParameter("page")), escapeHtml4(request.getParameter("limit")));
			request.setAttribute("displayErrorMsg", true);
			page = "dashboard.jsp";
			break;
		default:
			doGet(request, response);
			return;
		}

		showPage(request, response, page);
	}

	public void saveComputer(HttpServletRequest request, HttpServletResponse response) {
		Computer c1 = new Computer(escapeHtml4(request.getParameter("computerName")),
				MapperUtils.convertStringToLocalDateTime(escapeHtml4(request.getParameter("introduced"))),
				MapperUtils.convertStringToLocalDateTime(escapeHtml4(request.getParameter("discontinued"))),
				MapperUtils.convertStringToLong(escapeHtml4(request.getParameter("companyId"))));

		Validation validate = new ComputerValidator(c1).check();

		try {
			ComputerService.getInstance().create(c1);
		} catch (DAOException | ConnectionException | DriverException e) {
			validate.setError(true);
			validate.setMsg("connat add computer to the database");
			e.printStackTrace();
		}

		request.setAttribute("saveError", validate.isError());
		request.setAttribute("saveMsg", validate.getMsg());
	}

	public void setDashboardJSP(HttpServletRequest request, HttpServletResponse response, String pageParam,
			String limitParam) throws ServletException, IOException {
		List<ComputerDTO> computers = null;
		int currentPage = getParamToInt(pageParam);
		int currentLimit = getParamToInt(limitParam);
		if (currentLimit == 1) {
			currentLimit *= 20;
		} // set to 20 if cannot convert to int
		int currentComputersFrom = (currentPage - 1) * currentLimit;
		int currentComputersTo = 1;
		int totalPages = 1;
		int totalComputers = 0;
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

	public void setAddComputerJSP(HttpServletRequest request, HttpServletResponse response) {
		List<CompanyDTO> companies = null;
		boolean loadError = false;

		try {
			companies = CompanyService.getInstance().getAll();
		} catch (DAOException | ConnectionException | DriverException e) {
			e.printStackTrace();
			loadError = true;
		}

		request.setAttribute("loadError", loadError);
		request.setAttribute("companies", companies);
	}

	public void deleteComputer(HttpServletRequest request, HttpServletResponse response, String computersToDelete) {
		String[] computersId = computersToDelete.split(",");
		Validation validate = new Validation(false, "deletion successful");
		
		if (computersId.length == 0) {
			validate.setError(true);
			validate.setMsg("Deletion error, nothing to delete!");
		}
		
		for (String computerId: computersId) {
			Long id = MapperUtils.convertStringToLong(computerId);
			if (id != null) {
				try {
					ComputerService.getInstance().delete(id);
				} catch (DAOException | ConnectionException | DriverException e) {
					validate.setError(true);
					validate.setMsg("Deletion error, something when wrong!");
					e.printStackTrace();
				}
			}
		}
		
		request.setAttribute("delMsg", validate.getMsg());
		request.setAttribute("displayErrorMsg", validate.isError());
	}
	
	public void showPage(HttpServletRequest request, HttpServletResponse response, String page)
			throws ServletException, IOException {
		request.getRequestDispatcher("WEB-INF/views/" + page).forward(request, response);
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
