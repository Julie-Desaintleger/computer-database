package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.persistence.mapper.CompanyMapper;

public class CompanyDAO {
    private static CompanyDAO companyDAO;
    private ConnectHikari connect = ConnectHikari.getInstance();
    private final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

    private static final String SELECT_ALL = "SELECT id, name FROM company ORDER BY id";
    private static final String SELECT_BY_ID = "SELECT id, name FROM company WHERE company.id = ?";
    private static final String COUNT = "SELECT COUNT(id) AS nb_company FROM company";
    private static final String SELECT_WITH_PAGE = "SELECT id, name FROM company ORDER BY id LIMIT ? OFFSET ?";

    /**
     * L'instance du singleton de CompanyDAO.
     * 
     * @return l'instance de CompagnieDAO
     */
    public static CompanyDAO getInstance() {
	if (companyDAO == null) {
	    companyDAO = new CompanyDAO();
	}
	return companyDAO;
    }

    /**
     * Lister toutes les compagnies
     * 
     * @return Liste toutes les compagnies
     */
    public List<Company> getAll() {
	List<Company> companyList = new ArrayList<Company>();
	PreparedStatement statement = null;
	ResultSet resultSet = null;

	try (Connection connection = ConnectHikari.getConnection()) {
	    statement = connection.prepareStatement(SELECT_ALL);
	    resultSet = statement.executeQuery();

	    while (resultSet.next()) {
		Company company = CompanyMapper.map(resultSet);
		companyList.add(company);
	    }
	} catch (SQLException e) {
	    logger.error("Erreur DAO -> Lister toutes les compagnies");
	}
	return companyList;
    }

    /**
     * Compter le nombre total de compagnies.
     * 
     * @return le nombre total de compagnies
     */
    public int countAll() {
	int result = 0;
	PreparedStatement statement = null;
	ResultSet resultSet = null;

	try (Connection connection = ConnectHikari.getConnection()) {
	    statement = connection.prepareStatement(COUNT);
	    resultSet = statement.executeQuery();

	    while (resultSet.next()) {
		result = resultSet.getInt("nb_company");
	    }
	    logger.info("Nombre total d'entrées dans la base : " + result);
	} catch (SQLException e) {
	    logger.error("Erreur DAO -> Compter toutes les compagnies");
	}
	return result;
    }

    /**
     * Liste toutes les compagnies dans une page spécifique
     * 
     * @param p les informations qui doivent être contenues dans la page p
     * @return la liste de compagnies
     */
    public List<Company> getByPage(Page p) {
	List<Company> companies = new ArrayList<Company>();
	PreparedStatement statement = null;
	ResultSet resultSet = null;

	try (Connection connection = ConnectHikari.getConnection()) {
	    statement = connection.prepareStatement(SELECT_WITH_PAGE);
	    statement.setInt(1, p.getRows());
	    statement.setInt(2, p.getFirstLine());
	    resultSet = statement.executeQuery();

	    while (resultSet.next()) {
		Company company = CompanyMapper.map(resultSet);
		companies.add(company);
	    }
	} catch (SQLException e) {
	    logger.error("Erreur DAO -> liste des compagnies de la page : " + p.getCurrentPage() + e.getMessage());
	}
	return companies;
    }

    public Company findById(Long id) {
	Company company = null;
	PreparedStatement statement = null;
	ResultSet resultSet = null;

	if (id != null) {
	    try (Connection connection = ConnectHikari.getConnection()) {
		statement = connection.prepareStatement(SELECT_BY_ID);
		statement.setLong(1, id);
		resultSet = statement.executeQuery();

		while (resultSet.next()) {
		    company = CompanyMapper.map(resultSet);
		}
	    } catch (SQLException e) {
		logger.error("Erreur DAO -> Compagnie par id : " + e.getMessage());
	    }
	}
	return company;
    }

}
