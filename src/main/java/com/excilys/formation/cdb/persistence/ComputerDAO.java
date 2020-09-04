package com.excilys.formation.cdb.persistence;

import java.sql.Date;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.exception.DAOException;
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

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public ComputerDAO(DataSource dataSource) {
	jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Lister tous les ordinateurs
     * 
     * @return Liste tous les ordinateurs
     */
    public List<Computer> getAll() {
	logger.info("Liste des ordinateurs.");
	try {
	    logger.debug("Query : " + SELECT_ALL);
	    return jdbcTemplate.query(SELECT_ALL, new ComputerMapper());
	} catch (DataAccessException e) {
	    logger.error("Erreur DAO -> Lister tous les ordinateurs" + e.getMessage());
	    throw new DAOException("Erreur DAO -> Lister tous les ordinateurs.", e);
	}
    }

    /**
     * Compter le nombre total d'ordinateurs.
     * 
     * @return le nombre total d'ordinateurs.
     */
    public int countAll() {
	logger.info("Nombre d'ordinateurs.");
	try {
	    logger.debug("Query : " + COUNT);
	    return jdbcTemplate.queryForObject(COUNT, Integer.class);
	} catch (DataAccessException e) {
	    logger.error("Erreur DAO -> Compter tous les ordinateurs");
	    throw new DAOException("Erreur DAO -> Compter tous les ordinateurs", e);
	}
    }

    /**
     * Recherche un ordinateur par un identifiant
     * 
     * @param id l'identifiant à chercher en base
     * @return L'ordinateur correspondant si l'identifiant est présent, sinon null.
     */
    public Computer findById(Long id) {
	Computer computer = null;
	logger.info("Recherche ordinateur.");
	if (id != null) {
	    try {
		logger.debug("Query : " + SELECT_BY_ID);
		return jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[] { id }, new ComputerMapper());
	    } catch (DataAccessException e) {
		logger.error("Erreur DAO -> Ordinateur par id : " + e.getMessage());
		throw new DAOException("Erreur DAO -> Ordinateur par id", e);
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
	    try {
		Long company_id;
		Date introducedDate = computer.getIntroduced() == null ? null : Date.valueOf(computer.getIntroduced());
		Date discontinuedDate = computer.getDiscontinued() == null ? null
			: Date.valueOf(computer.getDiscontinued());
		if (computer.getCompany() == null || computer.getCompany().getId() == 0) {
		    company_id = null;
		} else {
		    company_id = computer.getCompany().getId();
		}
		Object[] params = { computer.getName(), introducedDate, discontinuedDate, company_id };
		logger.info("Creation ordinateur.");
		logger.debug("Query : " + INSERT);
		jdbcTemplate.update(INSERT, params);
	    } catch (DataAccessException e) {
		logger.error(
			"Erreur DAO -> insertion ordinateur. Vérifiez que l'id pour l'entreprise" + e.getMessage());
		throw new DAOException("Erreur DAO -> insertion ordinateur.", e);
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
	    try {
		Date introducedDate = null;
		Date discontinuedDate = null;
		if (computer.getIntroduced() != null) {
		    introducedDate = Date.valueOf(computer.getIntroduced());
		}
		if (computer.getDiscontinued() != null) {
		    discontinuedDate = Date.valueOf(computer.getDiscontinued());
		}
		Long company_id;
		if (computer.getCompany() == null || computer.getCompany().getId() == 0) {
		    company_id = null;
		} else {
		    company_id = computer.getCompany().getId();
		}
		Object[] params = { computer.getName(), introducedDate, discontinuedDate, company_id,
			computer.getId() };
		logger.info("Mise à jour ordinateur.");
		logger.debug("Query : " + UPDATE);
		jdbcTemplate.update(UPDATE, params);
	    } catch (DataAccessException e) {
		logger.error("Erreur DAO -> mise a jour ordinateur" + e.getMessage());
		throw new DAOException("Erreur DAO -> mise a jour ordinateur", e);
	    }

	}

    }

    /**
     * Suppression d'un ordinateur
     * 
     * @param id l'identifiant de l'ordinateur à supprimer en base
     */
    public void delete(Long id) {
	try {
	    logger.info("Suppression ordinateur.");
	    logger.debug("Query : " + DELETE);
	    jdbcTemplate.update(DELETE, new Object[] { id });
	} catch (DataAccessException e) {
	    logger.error("Erreur DAO -> suppression ordinateur" + e.getMessage());
	    throw new DAOException("Erreur DAO -> suppression ordinateur", e);
	}
    }

    /**
     * Liste tous les ordinateurs dans une page spécifique
     * 
     * @param p les informations qui doivent être contenues dans la page p
     * @return la liste d'ordinateurs
     */
    public List<Computer> getByPage(Page p) {
	try {
	    String order = "computer.id";
	    String orderChoice = String.format(SELECT_WITH_PAGE, order);
	    Object[] params = { p.getRows(), p.getFirstLine() };
	    logger.info("Liste d'ordinateurs par page.");
	    logger.debug("Query : " + orderChoice);
	    return jdbcTemplate.query(orderChoice, params, new ComputerMapper());
	} catch (DataAccessException e) {
	    logger.error("Erreur DAO -> liste des ordinateurs de la page : " + p.getCurrentPage() + e.getMessage());
	    throw new DAOException("Erreur DAO -> liste des ordinateurs de la page", e);
	}
    }

    /**
     * Compter le nombre d'ordinateur à rechercher.
     * 
     * @param search le pattern de recherche soit pour un ordinateur soit pour une
     *               compagnie
     * @return le nombre d'ordinateur à rechercher.
     */
    public int count(String search) {
	try {
	    logger.info("Nombre d'ordinateurs par recherche.");
	    if (search == null) {
		logger.debug("Query : " + COUNT);
		return jdbcTemplate.queryForObject(COUNT, Integer.class);
	    } else {
		Object[] params = { "%" + search + "%", "%" + search + "%" };
		logger.debug("Query : " + COUNT_SEARCH);
		return jdbcTemplate.queryForObject(COUNT_SEARCH, params, Integer.class);
	    }
	} catch (DataAccessException e) {
	    logger.error("Erreur DAO -> compter tous les ordinateurs à rechercher", e);
	    throw new DAOException("Erreur DAO -> compter tous les ordinateurs à rechercher", e);
	}
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
	try {
	    logger.info("Liste ordinateurs à rechercher.");
	    if (order == null || order.isEmpty()) {
		order = "computer.id";
	    }
	    if (research == null || research.isEmpty()) {
		String orderChoice = String.format(SELECT_WITH_PAGE, order);
		logger.debug("Query : " + orderChoice);
		Object[] params = { p.getRows(), p.getFirstLine() };
		return jdbcTemplate.query(orderChoice, params, new ComputerMapper());
	    } else {
		String orderChoice = String.format(SELECT_BY_SEARCH, order);
		logger.debug("Query : " + orderChoice);
		Object[] params = { "%" + research + "%", "%" + research + "%", p.getRows(), p.getFirstLine() };
		return jdbcTemplate.query(orderChoice, params, new ComputerMapper());
	    }
	} catch (DataAccessException e) {
	    logger.error("Erreur DAO -> liste des ordinateurs à rechercher de la page : " + p.getCurrentPage()
		    + e.getMessage());
	    throw new DAOException("Erreur DAO -> liste des ordinateurs à rechercher de la page", e);
	}
    }

}
