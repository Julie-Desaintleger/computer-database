package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.mapper.ComputerMapper;

public class ComputerDAO {
    private static ComputerDAO computerDAO;
    private Connection connect = MyConnect.getConnection();

    private static final String SELECT_ALL = "SELECT id, name, introduced, discontinued, company_id FROM computer ORDER BY id";
    private static final String COUNT = "SELECT COUNT(id) AS nb_computer FROM computer";
    private static final String SELECT_BY_ID = "SELECT id, name, introduced, discontinued, company_id FROM computer WHERE computer.id = ?";

    /**
     * L'instance du singleton de ComputerDAO.
     * 
     * @return l'instance de OrdinateurDAO
     */
    public static ComputerDAO getInstance() {
	if (computerDAO == null) {
	    computerDAO = new ComputerDAO();
	}
	return computerDAO;
    }

    /**
     * Lister tous les ordinateurs
     * 
     * @return Liste tous les ordinateurs
     */
    public List<Computer> getAll() {
	List<Computer> computerList = new ArrayList<Computer>();

	try {
	    PreparedStatement statement = connect.prepareStatement(SELECT_ALL);
	    ResultSet resultSet = statement.executeQuery();

	    while (resultSet.next()) {
		Computer computer = ComputerMapper.map(resultSet);
		computerList.add(computer);
	    }
	} catch (SQLException e) {
	    System.err.println("Erreur DAO -> Lister tous les ordinateurs" + e.getMessage());
	}
	return computerList;
    }

    /**
     * Compter le nombre total d'ordinateurs.
     * 
     * @return le nombre total d'ordinateurs.
     */
    public int countAll() {
	int result = 0;

	try {
	    PreparedStatement statement = connect.prepareStatement(COUNT);
	    ResultSet resultSet = statement.executeQuery();

	    while (resultSet.next()) {
		result = resultSet.getInt("nb_computer");
	    }
	    System.out.println("Nombre total d'entrées dans la base : " + result);
	} catch (SQLException e) {
	    System.err.println("Erreur DAO -> Compter tous les ordinateurs");
	}
	return result;
    }

    /**
     * Recherche un ordinateur par un identifiant
     * 
     * @param id l'identifiant à chercher en base
     * @return L'ordinateur correspondant si l'identifiant est présent, sinon null.
     */
    public Computer findById(Long id) {
	Computer computer = null;
	if (id != null) {
	    try {
		PreparedStatement statement = connect.prepareStatement(SELECT_BY_ID);
		statement.setLong(1, id);
		ResultSet resultSet = statement.executeQuery();
		while (resultSet.next()) {
		    computer = ComputerMapper.map(resultSet);
		}
	    } catch (SQLException e) {
		System.err.println("Erreur DAO -> Ordinateur par id : " + e.getMessage());
	    }
	}
	return computer;
    }
}