package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.persistence.mapper.ComputerMapper;

public class ComputerDAO {
    private static ComputerDAO computerDAO;
    private ConnectHikari connect = ConnectHikari.getInstance();
    private final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

    private static final String SELECT_ALL = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id ORDER BY computer.id";
    private static final String COUNT = "SELECT COUNT(id) AS nb_computer FROM computer";
    private static final String COUNT_SEARCH = "SELECT COUNT(computer.id) FROM computer LEFT JOIN company ON company_id = company.id "
	    + "WHERE computer.name LIKE ?";
    private static final String SELECT_BY_ID = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.id = ?";
    private static final String INSERT = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE computer.id = ?";
    private static final String DELETE = "DELETE FROM computer where id = ?";
    private static final String SELECT_WITH_PAGE = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id  ORDER BY id LIMIT ? OFFSET ?";
    private static final String SELECT_BY_SEARCH = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name AS company_name"
	    + " FROM computer LEFT JOIN company on company_id = company.id WHERE computer.name LIKE ? ";

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
	PreparedStatement statement = null;
	ResultSet resultSet = null;

	try (Connection connection = ConnectHikari.getConnection()) {
	    statement = connection.prepareStatement(SELECT_ALL);
	    resultSet = statement.executeQuery();

	    while (resultSet.next()) {
		Computer computer = ComputerMapper.map(resultSet);
		computerList.add(computer);
	    }
	} catch (SQLException e) {
	    logger.error("Erreur DAO -> Lister tous les ordinateurs" + e.getMessage());
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
	PreparedStatement statement = null;
	ResultSet resultSet = null;

	try (Connection connection = ConnectHikari.getConnection()) {
	    statement = connection.prepareStatement(COUNT);
	    resultSet = statement.executeQuery();

	    while (resultSet.next()) {
		result = resultSet.getInt("nb_computer");
	    }
	    logger.info("Nombre total d'entrées dans la base : " + result);
	} catch (SQLException e) {
	    logger.error("Erreur DAO -> Compter tous les ordinateurs");
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
	PreparedStatement statement = null;
	ResultSet resultSet = null;

	if (id != null) {
	    try (Connection connection = ConnectHikari.getConnection()) {
		statement = connection.prepareStatement(SELECT_BY_ID);
		statement.setLong(1, id);
		resultSet = statement.executeQuery();

		while (resultSet.next()) {
		    computer = ComputerMapper.map(resultSet);
		}
	    } catch (SQLException e) {
		logger.error("Erreur DAO -> Ordinateur par id : " + e.getMessage());
	    }
	}
	return computer;
    }

    /**
     * Création d'un nouvel ordinateur
     * 
     * @param computer l'ordinateur à insérer en base
     */
    public void create(Computer computer) {
	PreparedStatement statement = null;

	if (computer != null) {
	    try (Connection connection = ConnectHikari.getConnection()) {
		statement = connection.prepareStatement(INSERT);
		statement.setString(1, computer.getName());
		Date introducedDate = computer.getIntroduced() == null ? null : Date.valueOf(computer.getIntroduced());
		statement.setDate(2, introducedDate);
		Date discontinuedDate = computer.getDiscontinued() == null ? null
			: Date.valueOf(computer.getDiscontinued());
		statement.setDate(3, discontinuedDate);
		// cas pas gérer si, la compagnie n'existe pas en base
		if (computer.getCompany().getId() != 0L) {
		    statement.setLong(4, computer.getCompany().getId());
		} else {
		    statement.setNull(4, Types.BIGINT);
		}
		statement.execute();
	    } catch (SQLException e) {
		logger.error(
			"Erreur DAO -> insertion ordinateur. Vérifiez que l'id pour l'entreprise" + e.getMessage());
	    }
	}
    }

    /**
     * Mise à jour d'un ordinateur
     * 
     * @param computer l'ordinateur à mettre à jour en base
     */
    public void update(Computer computer) {
	PreparedStatement statement = null;

	if (computer != null) {
	    try (Connection connection = ConnectHikari.getConnection()) {
		statement = connection.prepareStatement(UPDATE);
		statement.setString(1, computer.getName());
		Date introducedDate = null;
		Date discontinuedDate = null;
		if (computer.getIntroduced() != null) {
		    introducedDate = Date.valueOf(computer.getIntroduced());
		}
		if (computer.getDiscontinued() != null) {
		    discontinuedDate = Date.valueOf(computer.getDiscontinued());
		}
		statement.setDate(2, introducedDate);
		statement.setDate(3, discontinuedDate);
		if (computer.getCompany().getId() == null || computer.getCompany().getId() == 0L) {
		    statement.setNull(4, Types.BIGINT);
		} else {
		    statement.setLong(4, computer.getCompany().getId());
		}
		statement.setLong(5, computer.getId());
		statement.executeUpdate();
	    } catch (SQLException e) {
		logger.error("Erreur DAO -> mise a jour ordinateur" + e.getMessage());
	    }
	}
    }

    /**
     * Suppression d'un ordinateur
     * 
     * @param id l'identifiant de l'ordinateur à supprimer en base
     */
    public void delete(Long id) {
	PreparedStatement statement = null;

	try (Connection connection = ConnectHikari.getConnection()) {
	    statement = connection.prepareStatement(DELETE);
	    statement.setLong(1, id);
	    statement.execute();
	} catch (SQLException e) {
	    logger.error("Erreur DAO -> suppression ordinateur" + e.getMessage());
	}
    }

    /**
     * Liste tous les ordinateurs dans une page spécifique
     * 
     * @param p les informations qui doivent être contenues dans la page p
     * @return la liste d'ordinateurs
     */
    public List<Computer> getByPage(Page p) {
	List<Computer> computers = new ArrayList<Computer>();
	PreparedStatement statement = null;
	ResultSet resultSet = null;

	try (Connection connection = ConnectHikari.getConnection()) {
	    statement = connection.prepareStatement(SELECT_WITH_PAGE);

	    statement.setInt(1, p.getRows());
	    statement.setInt(2, p.getFirstLine());

	    resultSet = statement.executeQuery();
	    while (resultSet.next()) {
		Computer computer = ComputerMapper.map(resultSet);
		computers.add(computer);

	    }
	} catch (SQLException e) {
	    logger.error("Erreur DAO -> liste des ordinateurs de la page : " + p.getCurrentPage() + e.getMessage());
	}
	return computers;
    }

    /**
     * Compter le nombre d'ordinateur à rechercher.
     * 
     * @param search le pattern de recherche pour un ordinateur
     * @return le nombre d'ordinateur à rechercher.
     */
    public int count(String search) {
	int total = 0;
	try (Connection connect = ConnectHikari.getConnection()) {
	    PreparedStatement statement;
	    if (search == null) {
		statement = connect.prepareStatement(COUNT);
	    } else {
		statement = connect.prepareStatement(COUNT_SEARCH);
		statement.setString(1, "%" + search + "%");
	    }
	    ResultSet result = statement.executeQuery();
	    result.next();
	    total = result.getInt(1);
	} catch (SQLException e) {
	    logger.error("Erreur DAO -> computer tous les ordinateurs à rechercher", e);
	}
	return total;
    }

    /**
     * Liste les ordinateurs à rechercher.
     * 
     * @param p        pour la pagination
     * @param research le pattern pour l'ordinateur à rechercher.
     * @return la liste des ordinateurs recherchés
     */
    public List<Computer> getBySearch(Page p, String research) {
	List<Computer> computers = new ArrayList<Computer>();
	PreparedStatement statement = null;
	ResultSet resultSet = null;

	try (Connection connection = ConnectHikari.getConnection()) {

	    if (research == null || research.isEmpty()) {
		statement = connection.prepareStatement(SELECT_WITH_PAGE);
		statement.setInt(1, p.getRows());
		statement.setInt(2, p.getFirstLine());
	    } else {

		statement = connection.prepareStatement(SELECT_BY_SEARCH);
		statement.setString(1, "%" + research + "%");
	    }

	    resultSet = statement.executeQuery();
	    while (resultSet.next()) {
		Computer computer = ComputerMapper.map(resultSet);
		computers.add(computer);
	    }
	} catch (SQLException e) {
	    logger.error("Erreur DAO -> liste des ordinateurs à rechercher de la page : " + p.getCurrentPage()
		    + e.getMessage());
	}
	return computers;
    }

}
