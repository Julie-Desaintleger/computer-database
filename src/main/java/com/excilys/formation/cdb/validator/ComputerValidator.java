package com.excilys.formation.cdb.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.exception.DateDiscontinuedException;
import com.excilys.formation.cdb.exception.EmptyDateException;
import com.excilys.formation.cdb.exception.NameException;

public class ComputerValidator {

    private static Logger logger = LoggerFactory.getLogger(ComputerValidator.class);

    public static void validatorName(String name) throws NameException {
	if (name.isEmpty()) {
	    logger.info("Name is required");
	    throw new NameException();
	}
    }

    public static void validatorDate(String introduced, String discontinued)
	    throws DateDiscontinuedException, EmptyDateException {
	DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
	LocalDate discontinuedLocDate = null;
	LocalDate introducedLocDate = null;
	if (!discontinued.isEmpty()) {
	    discontinuedLocDate = LocalDate.parse(discontinued, formatter);
	    if (!introduced.isEmpty()) {
		introducedLocDate = LocalDate.parse(introduced, formatter);
	    } else {
		logger.info("Introduced date have to be known");
		throw new EmptyDateException();
	    }
	    if (discontinuedLocDate.isBefore(introducedLocDate)) {
		logger.info("Discontinued must be after introduced");
		throw new DateDiscontinuedException();
	    }
	}
    }

}
