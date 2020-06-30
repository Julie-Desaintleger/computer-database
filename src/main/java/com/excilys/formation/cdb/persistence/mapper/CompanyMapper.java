package com.excilys.formation.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.model.Company;

public class CompanyMapper {
    private static Company newCompany;
    private static Logger logger = LoggerFactory.getLogger(CompanyMapper.class);

    /**
     * S'occupe de la conversion du résultat en entité
     * 
     * @param resultSet résultat de la requête
     * @return une entité Company correspondante
     */
    public static Company map(ResultSet resultSet) {
	try {
	    newCompany = new Company(resultSet.getLong("id"), resultSet.getString("name"));
	} catch (SQLException e) {
	    logger.error("Erreur -> Mapping Company");
	}
	return newCompany;
    }

}