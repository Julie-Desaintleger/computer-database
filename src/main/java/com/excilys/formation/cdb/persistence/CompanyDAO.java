package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.persistence.mapper.CompanyMapper;

public class CompanyDAO {
    private static CompanyDAO companyDAO;
    private Connection connect = MyConnect.getConnection();

    private static final String SELECT_ALL = "SELECT id, name FROM company ORDER BY id";
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

	try {
	    PreparedStatement statement = connect.prepareStatement(SELECT_ALL);
	    ResultSet resultSet = statement.executeQuery();

	    while (resultSet.next()) {
		Company company = CompanyMapper.map(resultSet);
		companyList.add(company);
	    }
	} catch (SQLException e) {
	    System.err.println("Erreur DAO -> Lister toutes les compagnies");
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

	try {
	    PreparedStatement statement = connect.prepareStatement(COUNT);
	    ResultSet resultSet = statement.executeQuery();

	    while (resultSet.next()) {
		result = resultSet.getInt("nb_company");
	    }
	    System.out.println("Nombre total d'entrées dans la base : " + result);

	} catch (SQLException e) {
	    System.err.println("Erreur DAO -> Compter toutes les compagnies");
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

	try {
	    PreparedStatement statement = connect.prepareStatement(SELECT_WITH_PAGE);

	    statement.setInt(1, p.getRows());
	    statement.setInt(2, p.getFirstLine());

	    ResultSet resultSet = statement.executeQuery();
	    while (resultSet.next()) {
		Company company = CompanyMapper.map(resultSet);
		companies.add(company);
	    }
	} catch (SQLException e) {
	    System.err
		    .println("Erreur DAO -> liste des compagnies de la page : " + p.getCurrentPage() + e.getMessage());
	}
	return companies;
    }
}
