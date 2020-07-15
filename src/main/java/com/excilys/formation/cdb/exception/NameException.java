package com.excilys.formation.cdb.exception;

public class NameException extends ComputerInvalidException {

    private static final long serialVersionUID = 1L;
    private String message;

    public NameException() {
	message = "Name is required.";
    }

    public String getMessage() {
	return message;
    }

}