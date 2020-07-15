package com.excilys.formation.cdb.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.exception.DateException;
import com.excilys.formation.cdb.exception.NameException;

public class ComputerValidator {

    private static Logger logger = LoggerFactory.getLogger(ComputerValidator.class);

    public static void validatorName(String name) throws NameException {
	if (name.isEmpty()) {
	    logger.info("Name is required");
	    throw new NameException();
	}
    }

    public static void validatorDate(String introduced, String discontinued) throws DateException {
	DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
	LocalDate introducedLocDate = LocalDate.parse(introduced, formatter);
	LocalDate discontinuedLocDate = LocalDate.parse(discontinued, formatter);
	if (discontinuedLocDate.isBefore(introducedLocDate)) {
	    logger.info("Discontinued must be after introduced");
	    throw new DateException();
	}
    }

}
