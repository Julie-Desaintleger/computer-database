package com.excilys.formation.cdb.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.exception.DateDiscontinuedException;
import com.excilys.formation.cdb.exception.EmptyDateException;
import com.excilys.formation.cdb.exception.NameException;

public class ComputerValidator {

    private static Logger logger = LoggerFactory.getLogger(ComputerValidator.class);
    protected static Map<String, String> errors = new HashMap<String, String>();

    public static Map<String, String> getErrors() {
	return errors;
    }

    public static boolean validatorName(String name) {
	if (name.isEmpty()) {
	    logger.info("Name is required");
	    errors.put("computerName", new NameException().getMessage());
	    return false;
	}
	return true;
    }

    public static boolean validatorDates(String introduced, String discontinued) {
	DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
	LocalDate discontinuedLocDate = null;
	LocalDate introducedLocDate = null;
	if (!discontinued.isEmpty()) {
	    discontinuedLocDate = LocalDate.parse(discontinued, formatter);
	    if (!introduced.isEmpty()) {
		introducedLocDate = LocalDate.parse(introduced, formatter);
	    } else {
		logger.info("Introduced date have to be known");
		errors.put("dateIntroduced", new EmptyDateException().getMessage());
		return false;
	    }
	    if (discontinuedLocDate.isBefore(introducedLocDate)) {
		logger.info("Discontinued must be after introduced");
		errors.put("dateDiscontinued", new DateDiscontinuedException().getMessage());
		return false;
	    }
	}
	return true;
    }

    public static boolean validateComputer(ComputerDTO computerDTO) {

	if (validatorDates(computerDTO.getIntroduced(), computerDTO.getDiscontinued())
		&& validatorName(computerDTO.getName())) {
	    return true;
	}

	return false;
    }

}
