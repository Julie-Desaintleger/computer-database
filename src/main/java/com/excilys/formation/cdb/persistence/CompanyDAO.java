package com.excilys.formation.cdb.persistence;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.cdb.exception.DAOException;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.persistence.mapper.CompanyMapper;

@Repository
public class CompanyDAO {
    private static final String SELECT_ALL = "SELECT id, name FROM company ORDER BY id";
    private static final String SELECT_BY_ID = "SELECT id, name FROM company WHERE company.id = ?";
    private static final String COUNT = "SELECT COUNT(id) AS nb_company FROM company";
    private static final String SELECT_WITH_PAGE = "SELECT id, name FROM company ORDER BY id LIMIT ? OFFSET ?";
    private static final String DELETE = "DELETE FROM company where company.id = ?";
    private static final String DELETE_COMPUTERS = "DELETE FROM computer WHERE company_id = ?";

    private final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public CompanyDAO(DataSource dataSource) {
	jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * Lister toutes les compagnies
     * 
     * @return Liste toutes les compagnies
     */
    public List<Company> getAll() {
	logger.info("Liste des compagnies.");
	try {
	    logger.debug("Query : " + SELECT_ALL);
	    return jdbcTemplate.query(SELECT_ALL, new CompanyMapper());
	} catch (DataAccessException e) {
	    logger.error("Erreur DAO -> Lister toutes les compagnies." + e.getMessage());
	    throw new DAOException("Erreur DAO -> Lister toutes les compagnies.", e);
	}
    }

    /**
     * Compter le nombre total de compagnies.
     * 
     * @return le nombre total de compagnies
     */
    public int countAll() {
	logger.info("Nombre total de compagnie.s dans la base.");
	try {
	    logger.debug("Query : " + COUNT);
	    return jdbcTemplate.queryForObject(COUNT, Integer.class);
	} catch (DataAccessException e) {
	    logger.error("Erreur DAO -> Compter toutes les compagnies" + e.getMessage());
	    throw new DAOException("Erreur DAO -> Compter toutes les compagnies", e);
	}
    }

    /**
     * Liste toutes les compagnies dans une page spécifique
     * 
     * @param p les informations qui doivent être contenues dans la page p
     * @return la liste de compagnies
     */
    public List<Company> getByPage(Page p) {
	logger.info("Liste des compagnies de la page.");
	try {
	    logger.debug("Query : " + SELECT_WITH_PAGE);
	    Object[] params = { p.getRows(), p.getFirstLine() };
	    return jdbcTemplate.query(SELECT_WITH_PAGE, params, new CompanyMapper());
	} catch (DataAccessException e) {
	    logger.error("Erreur DAO -> liste des compagnies de la page : " + p.getCurrentPage() + e.getMessage());
	    throw new DAOException("Erreur DAO -> liste des compagnies de la page.", e);
	}
    }

    public Company findById(Long id) {
	logger.info("Recherche compagnie par id.");
	try {
	    logger.debug("Query : " + SELECT_BY_ID);
	    return jdbcTemplate.queryForObject(SELECT_BY_ID, new Object[] { id }, new CompanyMapper());
	} catch (DataAccessException e) {
	    logger.error("Erreur DAO -> Compagnie par id : " + e.getMessage());
	    throw new DAOException("Erreur DAO -> Compagnie par id.", e);
	}
    }

    /**
     * Supprime une compagnie et les ordinateurs associés à cette compagnie.
     * 
     * @param id l'identifiant d'une compagnie
     */
    @Transactional(rollbackFor = { Exception.class })
    public void deleteByCompany(Long id) {
	logger.info("Supression d'une compagnie et ses ordinateurs associés.");
	Company company = findById(id);
	if (company != null) {
	    try {
		logger.debug("First Query : " + DELETE_COMPUTERS);
		int rowCount = jdbcTemplate.update(DELETE_COMPUTERS, new Object[] { id });
		logger.info("Enregistrements d'ordinateurs correctement supprimés, nb enregistrements : " + rowCount);
		logger.debug("Second Query : " + DELETE);
		jdbcTemplate.update(DELETE, new Object[] { id });
	    } catch (DataAccessException e) {
		logger.error("Erreur DAO -> supprimer une compagnie avec les ordinateurs :" + e);
		throw new DAOException("Erreur DAO -> supprimer une compagnie avec les ordinateurs", e);
	    }

	}

    }

}
