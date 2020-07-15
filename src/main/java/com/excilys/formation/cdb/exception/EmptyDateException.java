package com.excilys.formation.cdb.exception;

public class EmptyDateException extends ComputerInvalidException {
    private static final long serialVersionUID = 1L;
    private String message;

    public EmptyDateException() {
	message = "Date is required.";
    }

    public String getMessage() {
	return message;
    }

}