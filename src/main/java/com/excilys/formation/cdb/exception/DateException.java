package com.excilys.formation.cdb.exception;

public class DateException extends ComputerInvalidException {

    private static final long serialVersionUID = 1L;
    private String message;

    public DateException() {
	message = "Discontinued date must be after introduced date.";
    }

    public String getMessage() {
	return message;
    }
}