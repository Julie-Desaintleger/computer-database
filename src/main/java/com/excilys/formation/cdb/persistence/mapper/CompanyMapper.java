package com.excilys.formation.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.model.Company;

public class CompanyMapper {
    private static Logger logger = LoggerFactory.getLogger(CompanyMapper.class);
    private static final String ID_COMPANY = "id";
    private static final String NAME = "name";

    /**
     * S'occupe de la conversion du résultat en entité
     * 
     * @param resultSet résultat de la requête
     * @return une entité Company correspondante
     */
    public static Company map(ResultSet resultSet) {
	Company company = new Company();
	try {
	    Long id = resultSet.getLong(ID_COMPANY);
	    String name = resultSet.getString(NAME);
	    company.setId(id);
	    company.setName(name);
	} catch (SQLException e) {
	    logger.error("Erreur -> Mapping ResultSet to Company", e.getMessage());
	}
	return company;
    }

}