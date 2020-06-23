package com.excilys.formation.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.cdb.model.Company;

public class CompanyMapper {
    private static Company newCompany;

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
	    System.err.println("Erreur -> Mapping Company");
	}
	return newCompany;
    }

}