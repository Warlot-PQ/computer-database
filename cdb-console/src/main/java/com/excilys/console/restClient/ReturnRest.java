package com.excilys.console.restClient;

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
