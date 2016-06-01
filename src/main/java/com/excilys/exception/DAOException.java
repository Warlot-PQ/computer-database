package com.excilys.exception;

public class DAOException extends RuntimeException {
	public DAOException(Exception e) {
		super(e);
	}
}
