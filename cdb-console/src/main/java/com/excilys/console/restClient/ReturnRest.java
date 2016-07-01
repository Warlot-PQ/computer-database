package com.excilys.console.restClient;

/**
 * Response from a Restfull API. HTTP status code and entities.
 * @author pqwarlot
 *
 * @param <E> entities return by the request.
 */
public class ReturnRest<E> {
	private int statusCode;
	
	private E entity;
	
	public ReturnRest(int statusCode, E entity) {
		this.statusCode = statusCode;
		this.entity = entity;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public E getEntity() {
		return entity;
	}

	public void setEntity(E entity) {
		this.entity = entity;
	}
}
