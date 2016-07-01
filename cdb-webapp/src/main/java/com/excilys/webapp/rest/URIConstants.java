package com.excilys.webapp.rest;

public class URIConstants {
	// Computer
	// GET
	public static final String GET_COMPUTER_PAGE = "/rest/computer/{pageNumber}/{limit}";
	public static final String GET_COMPUTER_ALL = "/rest/computer";
	public static final String GET_COMPUTER_BY_ID = "/rest/computer/{id}";
	// POST
	public static final String CREATE_COMPUTER = "/rest/computer";
	// DELETE
	public static final String DELETE_COMPUTER_BY_ID = "/rest/computer/{id}";
	// PUT
	public static final String UPDATE_COMPUTER = "/rest/computer";
	
	// Company
	// GET
	public static final String GET_COMPANY_PAGE = "/rest/company/{pageNumber}/{limit}";
	public static final String GET_COMPANY_ALL = "/rest/company";
	public static final String GET_COMPANY_BY_ID = "/rest/company/{id}";
	// POST
	public static final String CREATE_COMPANY = "/rest/company";
	// DELETE
	public static final String DELETE_COMPANY_BY_ID = "/rest/company/{id}";
	// PUT
	public static final String UPDATE_COMPANY = "/rest/company";
}
