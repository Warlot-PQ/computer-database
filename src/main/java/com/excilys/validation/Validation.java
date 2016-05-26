package com.excilys.validation;

public class Validation {
	private boolean error;
	private String msg;

	public Validation() {
		this(false, "");
	}

	public Validation(boolean error, String msg) {
		this.error = error;
		this.msg = msg;
	}

	public boolean isError() {
		return error;
	}

	public void setError(boolean error) {
		this.error = error;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
