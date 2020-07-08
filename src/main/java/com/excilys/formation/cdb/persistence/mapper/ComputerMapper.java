package com.excilys.formation.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

public class ComputerMapper {
    private static final String ID_COMPUTER = "id";
    private static final String NAME = "name";
    private static final String INTRODUCED = "introduced";
    private static final String DISCONTINUED = "discontinued";
    private static final String COMPANY_ID = "company_id";
    private static final String COMPANY_NAME = "company_name";

    private static Logger logger = LoggerFactory.getLogger(CompanyMapper.class);

    /**
     * S'occupe de la conversion du résultat en entité
     * 
     * @param resultSet résultat de la requête
     * @return une entité Computer correspondante
     */
    public static Computer map(ResultSet resultSet) {
	Computer computer = new Computer();
	try {
	    Long id = resultSet.getLong(ID_COMPUTER);
	    String name = resultSet.getString(NAME);
	    LocalDate introduced = null;
	    introduced = checkIsNullIntroduced(resultSet, introduced);
	    LocalDate discontinued = null;
	    discontinued = checkDiscontinued(resultSet, discontinued);
	    Long company_id = resultSet.getLong(COMPANY_ID);
	    String company_name = resultSet.getString(COMPANY_NAME);
	    computer.setId(id);
	    computer.setName(name);
	    computer.setIntroduced(introduced);
	    computer.setDiscontinued(discontinued);
	    computer.setCompany(new Company.Builder().setId(company_id).setName(company_name).build());
	} catch (Exception e) {
	    logger.error("Erreur -> Mapping ResultSet to Computer", e.getMessage());
	}
	return computer;
    }

    private static LocalDate checkDiscontinued(ResultSet resultSet, LocalDate discontinued) throws SQLException {
	if (resultSet.getDate(DISCONTINUED) != null
		&& resultSet.getDate(DISCONTINUED).after(resultSet.getDate(INTRODUCED))) {
	    discontinued = resultSet.getDate(DISCONTINUED).toLocalDate();
	}
	return discontinued;
    }

    private static LocalDate checkIsNullIntroduced(ResultSet resultSet, LocalDate introduced) throws SQLException {
	if (resultSet.getDate(INTRODUCED) != null) {
	    introduced = resultSet.getDate(INTRODUCED).toLocalDate();
	}
	return introduced;
    }

}
