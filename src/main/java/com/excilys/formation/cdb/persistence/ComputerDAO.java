package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.persistence.mapper.ComputerMapper;

@Repository
public class ComputerDAO {
    private static final String SELECT_ALL = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id ORDER BY computer.id";
    private static final String COUNT = "SELECT COUNT(id) AS nb_computer FROM computer";
    private static final String COUNT_SEARCH = "SELECT COUNT(computer.id) FROM computer LEFT JOIN company ON company_id = company.id "
	    + "WHERE computer.name LIKE ? OR company.name LIKE ?";
    private static final String SELECT_BY_ID = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id WHERE computer.id = ?";
    private static final String INSERT = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
    private static final String UPDATE = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE computer.id = ?";
    private static final String DELETE = "DELETE FROM computer where id = ?";
    private static final String SELECT_WITH_PAGE = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name AS company_name FROM computer LEFT JOIN company ON company_id = company.id  ORDER BY %s LIMIT ? OFFSET ?";
    private static final String SELECT_BY_SEARCH = "SELECT computer.id, computer.name, introduced, discontinued, company_id, company.name AS company_name"
	    + " FROM computer LEFT JOIN company on company_id = company.id WHERE computer.name LIKE ? OR company.name LIKE ? ORDER BY %s LIMIT ? OFFSET ?";

    private final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

    /**
     * Lister tous les ordinateurs
     * 
     * @return Liste tous les ordinateurs
     */
    public List<Computer> getAll() {
	List<Computer> computerList = new ArrayList<Computer>();

	try (Connection connection = DataSource.getConnection();
		PreparedStatement preparedStat = connection.prepareStatement(SELECT_ALL);
		ResultSet resultSet = preparedStat.executeQuery()) {

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
	ResultSet resultSet = null;

	try (Connection connection = DataSource.getConnection();
		PreparedStatement statement = connection.prepareStatement(COUNT)) {

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
	ResultSet resultSet = null;

	if (id != null) {
	    try (Connection connection = DataSource.getConnection();
		    PreparedStatement preparedStat = connection.prepareStatement(SELECT_BY_ID)) {
		preparedStat.setLong(1, id);
		resultSet = preparedStat.executeQuery();

		while (resultSet.next()) {
		    computer = ComputerMapper.map(resultSet);
		}
		resultSet.close();
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

	if (computer != null) {
	    try (Connection connection = DataSource.getConnection();
		    PreparedStatement preparedStat = connection.prepareStatement(INSERT)) {
		preparedStat.setString(1, computer.getName());
		Date introducedDate = computer.getIntroduced() == null ? null : Date.valueOf(computer.getIntroduced());
		preparedStat.setDate(2, introducedDate);
		Date discontinuedDate = computer.getDiscontinued() == null ? null
			: Date.valueOf(computer.getDiscontinued());
		preparedStat.setDate(3, discontinuedDate);
		if (computer.getCompany() == null || computer.getCompany().getId() == 0) {
		    preparedStat.setNull(4, java.sql.Types.BIGINT);
		} else {
		    preparedStat.setLong(4, computer.getCompany().getId());
		}
		preparedStat.execute();
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
	if (computer != null) {
	    try (Connection connection = DataSource.getConnection();
		    PreparedStatement preparedStat = connection.prepareStatement(UPDATE)) {
		preparedStat.setString(1, computer.getName());
		Date introducedDate = null;
		Date discontinuedDate = null;
		if (computer.getIntroduced() != null) {
		    introducedDate = Date.valueOf(computer.getIntroduced());
		}
		if (computer.getDiscontinued() != null) {
		    discontinuedDate = Date.valueOf(computer.getDiscontinued());
		}
		preparedStat.setDate(2, introducedDate);
		preparedStat.setDate(3, discontinuedDate);
		if (computer.getCompany() == null || computer.getCompany().getId() == 0) {
		    preparedStat.setNull(4, java.sql.Types.BIGINT);
		} else {
		    preparedStat.setLong(4, computer.getCompany().getId());
		}
		preparedStat.setLong(5, computer.getId());
		preparedStat.executeUpdate();
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
	try (Connection connection = DataSource.getConnection();
		PreparedStatement preparedStat = connection.prepareStatement(DELETE)) {
	    preparedStat.setLong(1, id);
	    preparedStat.execute();
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
	ResultSet resultSet = null;
	String order = "computer.id";
	String orderChoice = String.format(SELECT_WITH_PAGE, order);

	try (Connection connection = DataSource.getConnection();
		PreparedStatement preparedStat = connection.prepareStatement(orderChoice)) {

	    preparedStat.setInt(1, p.getRows());
	    preparedStat.setInt(2, p.getFirstLine());

	    resultSet = preparedStat.executeQuery();
	    while (resultSet.next()) {
		Computer computer = ComputerMapper.map(resultSet);
		computers.add(computer);

	    }
	    preparedStat.close();
	} catch (SQLException e) {
	    logger.error("Erreur DAO -> liste des ordinateurs de la page : " + p.getCurrentPage() + e.getMessage());
	}
	return computers;
    }

    /**
     * Compter le nombre d'ordinateur à rechercher.
     * 
     * @param search le pattern de recherche soit pour un ordinateur soit pour une
     *               compagnie
     * @return le nombre d'ordinateur à rechercher.
     */
    public int count(String search) {
	int total = 0;
	try (Connection connection = DataSource.getConnection();) {
	    PreparedStatement preparedStat;
	    if (search == null) {
		preparedStat = connection.prepareStatement(COUNT);
	    } else {
		preparedStat = connection.prepareStatement(COUNT_SEARCH);
		preparedStat.setString(1, "%" + search + "%");
		preparedStat.setString(2, "%" + search + "%");
	    }
	    ResultSet result = preparedStat.executeQuery();
	    result.next();
	    total = result.getInt(1);
	    preparedStat.close();
	} catch (SQLException e) {
	    logger.error("Erreur DAO -> computer tous les ordinateurs à rechercher", e);
	}
	return total;
    }

    /**
     * Liste les ordinateurs à rechercher selon un ordre
     * 
     * @param p        pour la pagination
     * @param research le pattern pour la recherche sur le nom de l'ordinateur ou
     *                 sur le nom de la compagnie
     * @param order    l'ordre à appliquer
     * @return la liste des ordinateurs recherchés
     */
    public List<Computer> getBySearchOrdered(Page p, String research, String order) {
	List<Computer> computers = new ArrayList<Computer>();
	PreparedStatement preparedStat = null;
	ResultSet resultSet = null;

	try (Connection connection = DataSource.getConnection();) {
	    if (order == null || order.isEmpty()) {
		order = "computer.id";
	    }
	    if (research == null || research.isEmpty()) {
		String orderChoice = String.format(SELECT_WITH_PAGE, order);
		preparedStat = connection.prepareStatement(orderChoice);
		preparedStat.setInt(1, p.getRows());
		preparedStat.setInt(2, p.getFirstLine());
	    } else {
		String orderChoice = String.format(SELECT_BY_SEARCH, order);
		preparedStat = connection.prepareStatement(orderChoice);
		preparedStat.setString(1, "%" + research + "%");
		preparedStat.setString(2, "%" + research + "%");
		preparedStat.setInt(3, p.getRows());
		preparedStat.setInt(4, p.getFirstLine());
	    }

	    resultSet = preparedStat.executeQuery();
	    while (resultSet.next()) {
		Computer computer = ComputerMapper.map(resultSet);
		computers.add(computer);
	    }
	    preparedStat.close();
	    resultSet.close();
	} catch (SQLException e) {
	    logger.error("Erreur DAO -> liste des ordinateurs à rechercher de la page : " + p.getCurrentPage()
		    + e.getMessage());
	}
	return computers;
    }

}
