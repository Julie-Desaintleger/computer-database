package com.excilys.binding.exception;

public class DateDiscontinuedException extends ComputerInvalidException {

    private static final long serialVersionUID = 1L;
    private String message;

    public DateDiscontinuedException() {
	message = "Discontinued date must be after introduced date.";
    }

    public String getMessage() {
	return message;
    }
}