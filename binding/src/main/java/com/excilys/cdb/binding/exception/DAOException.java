package com.excilys.cdb.binding.exception;

public class DAOException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public DAOException(String message, Throwable cause) {
	super(message, cause);
    }
}
