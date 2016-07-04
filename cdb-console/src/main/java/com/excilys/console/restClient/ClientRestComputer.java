package com.excilys.console.restClient;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.core.dto.ComputerDTO;
import com.excilys.service.pagination.Page;

/**
 * Access to the Computer Restfull API.
 * @author pqwarlot
 *
 */
public class ClientRestComputer {
	private static final Logger LOGGER = LoggerFactory.getLogger(ClientRestComputer.class);
	private static final String URI = "http://localhost:8080/computer-database/rest";

	public static ReturnRest<Page<ComputerDTO>> getPageComputer(String pageNumber, String eltByPage) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(URI).path("computer").path(pageNumber).path(eltByPage);
		LOGGER.debug("URI: " + webTarget.getUri());

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		return new ReturnRest<Page<ComputerDTO>>(response.getStatus(),
				response.readEntity(new GenericType<Page<ComputerDTO>>() {
				}));
	}

	public static ReturnRest<List<ComputerDTO>> getAllComputer() {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(URI).path("computer");
		LOGGER.debug("URI: " + webTarget.getUri());

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		return new ReturnRest<List<ComputerDTO>>(response.getStatus(),
				response.readEntity(new GenericType<List<ComputerDTO>>() {
				}));
	}

	public static ReturnRest<ComputerDTO> getComputer(String computerId) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(URI).path("computer").path(computerId);
		LOGGER.debug("URI: " + webTarget.getUri());

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
		Response response = invocationBuilder.get();

		return new ReturnRest<ComputerDTO>(response.getStatus(), 
				response.readEntity(new GenericType<ComputerDTO>() {
				}));
	}

	public static ReturnRest<String> createComputer(ComputerDTO computerDTO) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(URI).path("computer");
		LOGGER.debug("URI: " + webTarget.getUri());
		
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.TEXT_PLAIN);
		Response response = invocationBuilder.post(Entity.entity(computerDTO, MediaType.APPLICATION_JSON));
		
		return new ReturnRest<String>(response.getStatus(), response.readEntity(String.class));
	}

	public static ReturnRest<String> updateComputer(ComputerDTO computerDTO) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(URI).path("computer");
		LOGGER.debug("URI: " + webTarget.getUri());
		
		Invocation.Builder invocationBuilder =  webTarget.request(MediaType.TEXT_PLAIN);
		Response response = invocationBuilder.put(Entity.entity(computerDTO, MediaType.APPLICATION_JSON));
		
		return new ReturnRest<String>(response.getStatus(), response.readEntity(String.class));
	}

	public static ReturnRest<String> deleteComputer(String computerId) {
		Client client = ClientBuilder.newClient();
		WebTarget webTarget = client.target(URI).path("computer").path(computerId);
		LOGGER.debug("RestClient delete computer id=" + computerId);
		LOGGER.debug("URI: " + webTarget.getUri());

		Invocation.Builder invocationBuilder = webTarget.request(MediaType.TEXT_PLAIN);
		Response response = invocationBuilder.delete();
		LOGGER.debug("RestClient status: " + response.getStatus());

		return new ReturnRest<String>(response.getStatus(), response.readEntity(String.class));
	}
}
