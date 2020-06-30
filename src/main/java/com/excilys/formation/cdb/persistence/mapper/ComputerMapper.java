package com.excilys.formation.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.model.Computer;

public class ComputerMapper {
    private static Computer newComputer;
    private static final String ID_COMPUTER = "id";
    private static final String NAME = "name";
    private static final String INTRODUCED = "introduced";
    private static final String DISCONTINUED = "discontinued";
    private static final String COMPANY_ID = "company_id";
    private static Logger logger = LoggerFactory.getLogger(ComputerMapper.class);

    /**
     * S'occupe de la conversion du résultat en entité
     * 
     * @param resultSet résultat de la requête
     * @return une entité Computer correspondante
     */
    public static Computer map(ResultSet resultSet) {
	try {
	    newComputer = new Computer(resultSet.getLong(ID_COMPUTER), resultSet.getString(NAME));
	    checkIsNullIntroduced(resultSet);
	    checkDiscontinued(resultSet);
	    newComputer.setIdCompany(resultSet.getLong(COMPANY_ID));
	} catch (SQLException e) {
	    logger.error("Erreur -> Mapping Computer");
	}
	return newComputer;
    }

    private static void checkDiscontinued(ResultSet resultSet) throws SQLException {
	if (resultSet.getDate(DISCONTINUED) != null
		&& resultSet.getDate(DISCONTINUED).after(resultSet.getDate(INTRODUCED))) {
	    newComputer.setDiscontinued(resultSet.getDate(DISCONTINUED));
	}
    }

    private static void checkIsNullIntroduced(ResultSet resultSet) throws SQLException {
	if (resultSet.getDate(INTRODUCED) != null) {
	    newComputer.setIntroduced(resultSet.getDate(INTRODUCED));
	}
    }
}
