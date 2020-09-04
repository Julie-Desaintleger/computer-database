package com.excilys.formation.cdb.persistence.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.formation.cdb.model.Company;

public class CompanyMapper implements RowMapper<Company> {
    private static final String ID_COMPANY = "id";
    private static final String NAME = "name";

    private static Logger logger = LoggerFactory.getLogger(CompanyMapper.class);

    /**
     * S'occupe de la conversion du résultat en entité
     * 
     * @param resultSet résultat de la requête
     * @param rowNum    nombre de résultat de la requête
     * @return une entité Company correspondante
     */
    @Override
    public Company mapRow(ResultSet resultSet, int rowNum) throws SQLException {
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