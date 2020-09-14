package com.excilys.formation.cdb.persistence;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.model.QCompany;
import com.excilys.formation.cdb.model.QComputer;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class ComputerDAO {
    private final Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Lister tous les ordinateurs
     * 
     * @return Liste tous les ordinateurs
     */
    public List<Computer> getAll() {
	logger.info("Liste des ordinateurs.");
	QComputer computer = QComputer.computer;
	JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
	try {
	    return (ArrayList<Computer>) query.from(computer).orderBy(computer.name.asc().nullsLast()).fetch();
	} catch (Exception dae) {
	    logger.error("Erreur DAO -> Lister tous les ordinateurs.", dae);
	    return new ArrayList<Computer>();
	}
    }

    /**
     * Compter le nombre total d'ordinateurs.
     * 
     * @return le nombre total d'ordinateurs.
     */
    public Long countAll() {
	logger.info("Nombre d'ordinateurs.");
	QComputer computer = QComputer.computer;
	long result = 0L;
	JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
	try {
	    result = query.from(computer).fetchCount();
	} catch (Exception dae) {
	    logger.error("Erreur DAO -> Compter toutes les compagnies", dae);
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
	logger.info("Recherche ordinateur.");
	QComputer computer = QComputer.computer;
	JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
	try {
	    return query.from(computer).where(computer.id.eq(id)).fetchOne();
	} catch (Exception dae) {
	    logger.error("Erreur DAO -> Compagnie par id : ", dae);
	    return null;
	}
    }

    /**
     * Création d'un nouvel ordinateur
     * 
     * @param computer l'ordinateur à insérer en base
     */
    @Transactional
    public Computer create(Computer newComputer) {
	logger.info("Creation ordinateur.");
	newComputer.setId(null);
	try {
	    entityManager.persist(newComputer);
	    entityManager.flush();
	    return newComputer;
	} catch (Exception dae) {
	    logger.error("Erreur DAO -> insertion ordinateur.", dae);
	    return null;
	}

    }

    /**
     * Mise à jour d'un ordinateur
     * 
     * @param computer l'ordinateur à mettre à jour en base
     */
    @Transactional
    public Computer update(Computer newComputer) {
	logger.info("Mise à jour ordinateur.");
	QComputer computer = QComputer.computer;
	JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
	try {
	    queryFactory.update(computer).where(computer.id.eq(newComputer.getId()))
		    .set(computer.name, newComputer.getName()).set(computer.introduced, newComputer.getIntroduced())
		    .set(computer.discontinued, newComputer.getDiscontinued())
		    .set(computer.company.id, newComputer.getCompany().getId()).execute();
	    return newComputer;
	} catch (Exception dae) {
	    logger.error("Erreur DAO -> mise a jour ordinateur", dae);
	    return null;
	}
    }

    /**
     * Suppression d'un ordinateur
     * 
     * @param id l'identifiant de l'ordinateur à supprimer en base
     */
    @Transactional
    public void delete(Long id) {
	logger.info("Suppression ordinateur.");
	QComputer computer = QComputer.computer;
	JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

	try {
	    queryFactory.delete(computer).where(computer.id.eq(id)).execute();
	} catch (Exception dae) {
	    logger.error("Erreur DAO -> suppression ordinateur ", dae);
	}

    }

    /**
     * Liste tous les ordinateurs dans une page spécifique
     * 
     * @param p les informations qui doivent être contenues dans la page p
     * @return la liste d'ordinateurs
     */
    public List<Computer> getByPage(Page p) {
	logger.info("Liste d'ordinateurs par page.");
	QComputer computer = QComputer.computer;
	JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
	try {
	    return (ArrayList<Computer>) query.from(computer).offset(p.getCurrentPage() * p.getRows())
		    .limit(p.getRows()).fetch();
	} catch (Exception e) {
	    logger.error("Erreur DAO -> liste des ordinateurs de la page : ", e);
	    return new ArrayList<Computer>();
	}

    }

    /**
     * Compter le nombre d'ordinateur à rechercher.
     * 
     * @param search le pattern de recherche soit pour un ordinateur soit pour une
     *               compagnie
     * @return le nombre d'ordinateur à rechercher.
     */
    public Long count(String search) {
	logger.info("Nombre d'ordinateurs par recherche.");
	QCompany company = QCompany.company;
	QComputer computer = QComputer.computer;
	JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
	try {
	    return query.from(computer).leftJoin(company).on(computer.company.id.eq(company.id))
		    .where(computer.name.contains(search).or(company.name.contains(search))).fetchCount();
	} catch (Exception e) {
	    logger.error("Erreur DAO -> compter tous les ordinateurs à rechercher", e);
	    return 0L;
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
	logger.info("Liste ordinateurs à rechercher.");

	QComputer computer = QComputer.computer;
	QCompany company = QCompany.company;
	JPAQuery<Computer> query = new JPAQuery<Computer>(entityManager);
	try {
	    return (ArrayList<Computer>) query.from(computer).leftJoin(company).on(computer.company.id.eq(company.id))
		    .where(computer.name.contains(research).or(company.name.contains(research)))
		    .offset(p.getCurrentPage() * p.getRows()).limit(p.getRows()).fetch();
	} catch (Exception e) {
	    logger.error("Erreur DAO -> liste des ordinateurs à rechercher de la page : ", e);
	    return new ArrayList<Computer>();
	}

    }

}
