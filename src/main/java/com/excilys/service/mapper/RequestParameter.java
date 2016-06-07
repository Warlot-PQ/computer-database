package com.excilys.service.mapper;

import static org.apache.commons.lang3.StringEscapeUtils.escapeHtml4;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;

import com.excilys.Pagination.Page;
import com.excilys.bean.CompanyDTO;
import com.excilys.bean.ComputerDTO;
import com.excilys.validation.Validation;

/**
 * Get/set attribute from/to the HttpServletRequest, regroup all manipulation on HttpServletRequest object.
 * Use builder pattern.
 * @author pqwarlot
 *
 */
public class RequestParameter {
	private static int LIMIT_DEFAULT = 20;
	private static boolean ALPHA_NUMERICAL_ORDER_DEFAULT = true;
	private HttpServletRequest request;
	
	public RequestParameter(HttpServletRequest request) {
		this.request = request;
	}

	public ComputerDTO getComputerDTOWithId(){
		String computerId = escapeHtml4(request.getParameter("id"));
		ComputerDTO computerDTO = getComputerDTO();
		computerDTO.setId((computerId == null) ? computerId : computerId.trim());
		return computerDTO;
	}
	
	/**
	 * Map a ComputerDTO object from parameters in HttpServletRequest object
	 * @return ComputerDTO mapped
	 */
	public ComputerDTO getComputerDTO(){
		String computerName = escapeHtml4(request.getParameter("computerName"));
		String introduced = escapeHtml4(request.getParameter("introduced"));
		String discontinued = escapeHtml4(request.getParameter("discontinued"));
		String companyId = escapeHtml4(request.getParameter("companyId"));
		
		return new ComputerDTO(
				(computerName == null) ? computerName : computerName.trim(),
				(introduced == null) ? introduced : introduced.trim(),
				(discontinued == null) ? discontinued : discontinued.trim(),
				(companyId == null) ? companyId : companyId.trim());
	}
	
	/**
	 * Get the page variable in HttpServletRequest parameter
	 * @return escaped page parameter
	 */
	public int getCurrentPage() {
		String currentPageStr = escapeHtml4(request.getParameter("page"));
		int currentPageInt = 1;
				
		if (StringUtils.isNumeric(currentPageStr)) {
			currentPageInt = Integer.valueOf(currentPageStr);
			if (currentPageInt == 0) {
				currentPageInt = 1;
			}
		}
		return currentPageInt;
	}
	
	/**
	 * Get the limit variable in HttpServletRequest parameter
	 * @return escaped limit parameter
	 */
	public int getLimit() {
		String currentPageStr = escapeHtml4(request.getParameter("limit"));
		int currentPageInt = LIMIT_DEFAULT;
				
		if (StringUtils.isNumeric(currentPageStr)) {
			currentPageInt = Integer.valueOf(currentPageStr);
		}
		return currentPageInt;
	}
	
	/**
	 * Get the selection variable in HttpServletRequest parameter
	 * @return escaped and trimmed selection parameter
	 */
	public String getSelection() {
		String selection = escapeHtml4(request.getParameter("selection"));
		return (selection != null) ? selection.trim() : selection;
	}
	
	/**
	 * Get the search variable in HttpServletRequest parameter
	 * @return escaped and trimmed search parameter
	 */
	public String getSearchElt() {
		String search = escapeHtml4(request.getParameter("search"));
		return (search != null) ? search.trim() : search;
	}
	
	public String getOrderBy() {
		String orderBy = escapeHtml4(request.getParameter("orderBy"));
		return (orderBy != null) ? orderBy.trim() : orderBy;
	}
	
	public Boolean getOrderAlphaNumerical() {
		String orderAlphaNumericalStr = escapeHtml4(request.getParameter("orderAlphaNum"));
		boolean orderAlphaNumerical = ALPHA_NUMERICAL_ORDER_DEFAULT;

		if (orderAlphaNumericalStr != null && (orderAlphaNumericalStr.equalsIgnoreCase("true") || orderAlphaNumericalStr.equalsIgnoreCase("false"))) {
			orderAlphaNumerical = Boolean.valueOf(orderAlphaNumericalStr);
		}
		return orderAlphaNumerical;
	}
	
	public Long getId() {
		String idStr = escapeHtml4(request.getParameter("id"));
		Long id = null;
		
		if (StringUtils.isNumeric(idStr)) {
			id = Long.valueOf(idStr);
		}
		return id;
	}
	
	/**
	 * Set page object to HttpServletRequest attribute
	 * @param page Page object
	 */
	public void setPage(Page<?> page) {
		request.setAttribute("page", page);
	}
	
	public void setCompanies(List<CompanyDTO> companies) {
		request.setAttribute("companies", companies);
	}

	/**
	 * Set validation object to HttpServletRequest attribute
	 * @param validation Validation object
	 */
	public void setErrorMessage(Validation validation) {
		request.setAttribute("validation", validation);
	}
	
	/**
	 * Set computerSearch name to HttpServletRequest attribute
	 * @param search String computerSearch name
	 */
	public void setComputerSearched(String search) {
		request.setAttribute("computerSearch", search);
	}
	
	public void setOrderBy(String orderBy) {
		request.setAttribute("orderBy", orderBy);

	}
	
	public void setOrderAlphaNumerical(String orderAlphaNumerical) {
		request.setAttribute("orderAlphaNumerical", orderAlphaNumerical);
	}
	
	public void setComputerDTO(ComputerDTO c) {
		request.setAttribute("computer", c);
	}
}
