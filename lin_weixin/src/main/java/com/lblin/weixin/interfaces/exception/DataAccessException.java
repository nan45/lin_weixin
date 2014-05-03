package com.lblin.weixin.interfaces.exception;

public class DataAccessException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public DataAccessException(String message) {
		super(message);
	}

	public DataAccessException(Throwable cause) {
		super(cause);
	}
} 
