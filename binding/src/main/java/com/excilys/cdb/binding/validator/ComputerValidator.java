package com.excilys.cdb.binding.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.binding.dto.ComputerDTO;
import com.excilys.cdb.binding.exception.DateDiscontinuedException;
import com.excilys.cdb.binding.exception.EmptyDateException;
import com.excilys.cdb.binding.exception.NameException;

public class ComputerValidator {

    private static Logger logger = LoggerFactory.getLogger(ComputerValidator.class);
    protected static Map<String, String> errors = new HashMap<String, String>();

    public static Map<String, String> getErrors() {
	return errors;
    }

    public static boolean validatorName(ComputerDTO computerDTO) {
	if (computerDTO.getName().isEmpty()) {
	    logger.info("Name is required");
	    errors.put("computerName", new NameException().getMessage());
	    return false;
	}
	return true;
    }

    public static boolean validatorDates(ComputerDTO computerDTO) {
	DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
	LocalDate discontinuedLocDate = null;
	LocalDate introducedLocDate = null;
	if (!computerDTO.getDiscontinued().isEmpty()) {
	    discontinuedLocDate = LocalDate.parse(computerDTO.getDiscontinued(), formatter);
	    if (!computerDTO.getIntroduced().isEmpty()) {
		introducedLocDate = LocalDate.parse(computerDTO.getIntroduced(), formatter);
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

	if (validatorDates(computerDTO) && validatorName(computerDTO)) {
	    return true;
	}

	return false;
    }

}
