package com.excilys.console.restClient;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.core.dto.CompanyDTO;
import com.excilys.service.pagination.Page;

public class ClientRestCompany {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientRestComputer.class);
	private static final String URI = "http://localhost:8080/computer-database/rest";
	
	public static ReturnRest<Page<CompanyDTO>> getPageCompany(String pageNumber, String eltByPage) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(URI).path("company").path(pageNumber).path(eltByPage);
		LOGGER.debug("URI: " + webTarget.getUri());

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		return new ReturnRest<Page<CompanyDTO>>(response.getStatus(),
				response.readEntity(new GenericType<Page<CompanyDTO>>() {
				}));		
	}
	
	public static ReturnRest<List<CompanyDTO>> getAllCompany() {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(URI).path("company");
		LOGGER.debug("URI: " + webTarget.getUri());

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		return new ReturnRest<List<CompanyDTO>>(response.getStatus(),
				response.readEntity(new GenericType<List<CompanyDTO>>() {
				}));
	}
}
