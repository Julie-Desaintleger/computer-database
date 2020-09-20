package com.excilys.cdb.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.cdb.core.model.Company;
import com.excilys.cdb.core.model.Page;
import com.excilys.cdb.core.model.QCompany;
import com.excilys.cdb.core.model.QComputer;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class CompanyDAO {

    @PersistenceContext
    EntityManager entityManager;

    private final Logger logger = LoggerFactory.getLogger(CompanyDAO.class);

    /**
     * Lister toutes les compagnies
     * 
     * @return Liste toutes les compagnies
     */
    public List<Company> getAll() {
	logger.info("Liste des compagnies.");
	QCompany company = QCompany.company;
	JPAQuery<Company> query = new JPAQuery<Company>(entityManager);
	try {
	    return (ArrayList<Company>) query.from(company).orderBy(company.name.asc().nullsLast()).fetch();
	} catch (Exception dae) {
	    logger.error("Erreur DAO -> Lister toutes les compagnies.", dae);
	    return new ArrayList<Company>();
	}
    }

    /**
     * Compter le nombre total de compagnies.
     * 
     * @return le nombre total de compagnies
     */
    public Long countAll() {
	logger.info("Nombre total de compagnie.s dans la base.");
	QCompany company = QCompany.company;
	long result = 0L;
	JPAQuery<Company> query = new JPAQuery<Company>(entityManager);
	try {
	    result = query.from(company).fetchCount();
	} catch (Exception dae) {
	    logger.error("Erreur DAO -> Compter toutes les compagnies", dae);
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
	logger.info("Liste des compagnies de la page.");

	QCompany company = QCompany.company;
	JPAQuery<Company> query = new JPAQuery<Company>(entityManager);
	try {
	    return (ArrayList<Company>) query.from(company).offset(p.getFirstLine()).limit(p.getRows()).fetch();
	} catch (Exception e) {
	    logger.error("Erreur DAO -> liste des compagnies de la page : ", e);
	    return new ArrayList<Company>();
	}
    }

    public Company findById(Long id) {
	logger.info("Recherche compagnie par id.");

	QCompany company = QCompany.company;
	JPAQuery<Company> query = new JPAQuery<Company>(entityManager);
	try {
	    return query.from(company).where(company.id.eq(id)).fetchOne();
	} catch (Exception dae) {
	    logger.error("Erreur DAO -> Compagnie par id : ", dae);
	    return null;
	}
    }

    /**
     * Supprime une compagnie et les ordinateurs associés à cette compagnie.
     * 
     * @param id l'identifiant d'une compagnie
     */
    @Transactional
    public boolean deleteByCompany(Long id) {
	logger.info("Supression d'une compagnie et ses ordinateurs associés.");
	QCompany company = QCompany.company;
	QComputer computer = QComputer.computer;
	JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

	try {
	    queryFactory.delete(computer).where(company.id.eq(id)).execute();
	    queryFactory.delete(company).where(company.id.eq(id)).execute();
	    logger.info("Enregistrements d'ordinateurs correctement supprimés");
	    return true;

	} catch (Exception dae) {
	    logger.error("Erreur DAO -> supprimer une compagnie avec les ordinateurs : ", dae);
	    return false;
	}

    }

}
