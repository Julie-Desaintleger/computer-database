package com.excilys.formation.cdb.ui;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;

@Component
public class Cli {
    private static Scanner sc;
    private final static Logger logger = LoggerFactory.getLogger(Cli.class);

    @Autowired
    CompanyService companyService;
    @Autowired
    ComputerService computerService;

    /**
     * Liste toutes les entreprises dans la base
     */
    public void listAllCompanies() {
	boolean isCompleted = false;
	Page newPage = new Page();
	int nbCompanies = companyService.countAll();

	while (!isCompleted) {
	    List<Company> allCompanies = companyService.getAllByPage(newPage);
	    allCompanies.forEach(cp -> System.out.println(cp.toString()));
	    isCompleted = optionsPages(newPage, nbCompanies);
	}
    }

    /**
     * Liste tous les ordinateurs dans la base
     */
    public void listAllComputers() {
	boolean isCompleted = false;
	Page newPage = new Page();
	int nbComputer = computerService.countAll();

	while (!isCompleted) {
	    List<Computer> allComputers = computerService.getAllByPage(newPage);
	    allComputers.forEach(cm -> System.out.println(cm.toString()));
	    isCompleted = optionsPages(newPage, nbComputer);
	}
    }

    /**
     * Affiche les détails d'un ordinateur.
     */
    public void detailsComputer() {
	Long idComputer = getId();

	if (idComputer != null) {
	    checkId(idComputer);
	} else {
	    logger.error("Erreur pour la récupération de l'id");
	}
    }

    /**
     * Récupère un entier tapé par l'utilisateur pour l'identifiant d'un ordinateur
     * 
     * @return l'identifiant de l'ordinateur
     */
    private static Long getId() {
	System.out.println("Entrez un identifiant d'ordinateur : ");
	String reply;
	Long idComputer = null;
	boolean isCorrectId = false;

	while (!isCorrectId) {
	    reply = sc.nextLine();

	    try {
		idComputer = Long.parseLong(reply);
		isCorrectId = true;
		System.out.println("id Computer : " + idComputer);
	    } catch (Exception e) {
		logger.error("Entrez un nombre..." + e.getMessage());
	    }
	}
	return idComputer;
    }

    /**
     * Récupère un entier tapé par l'utilisateur pour l'identifiant d'une compagnie
     * 
     * @return l'identifiant d'une compagnie
     */
    private static Long getIdCompany() {
	System.out.println("Entrez un identifiant d'une compagnie : ");
	String reply;
	Long idCompany = null;
	boolean isCorrectId = false;

	while (!isCorrectId) {
	    reply = sc.nextLine();

	    try {
		idCompany = Long.parseLong(reply);
		isCorrectId = true;
		System.out.println("id Company : " + idCompany);
	    } catch (Exception e) {
		logger.error("Entrez un nombre..." + e.getMessage());
	    }
	}
	return idCompany;
    }

    /**
     * Vérification sur l'entier saisie appartient bien a un ordinateur en base
     * 
     * @param idComputer l'identifiant d'ordinateur à chercher
     * @return true si l'identifiant existe, sinon false.
     */
    private boolean checkId(Long idComputer) {
	boolean isPresent = false;

	if (idComputer != null) {
	    Computer c = computerService.getById(idComputer);

	    if (c != null) {
		isPresent = true;
		System.out.println("L'id correspond à l'ordinateur suivant : \n" + c.toString());
	    } else {
		logger.error("L'id ne correspond à aucun ordinateur");
	    }
	} else {
	    logger.error("Erreur récupération sur l'id");
	}
	return isPresent;
    }

    /**
     * Vérification sur l'entier saisie appartient bien à une compagnie en base
     * 
     * @param idCompany l'identifiant d'une compagnie à chercher
     * @return true si l'identifiant existe, sinon false.
     */
    private boolean checkIdCompany(Long idCompany) {
	boolean isPresent = false;

	if (idCompany != null) {
	    Company c = companyService.getById(idCompany);

	    if (c != null) {
		isPresent = true;
		System.out.println("L'id correspond à la compagnie suivante : \n" + c.toString());
	    } else {
		logger.error("L'id ne correspond à aucune compagnie");
	    }
	} else {
	    logger.error("Erreur récupération sur l'id compagnie");
	}
	return isPresent;
    }

    /**
     * Création d'un nouvel ordinateur
     */
    public void createComputer() {
	Computer newComputer = getInfos();

	if (newComputer != null) {
	    computerService.insert(newComputer);
	}
    }

    /**
     * Récupérer les informations sur un ordinateur
     * 
     * @return les informations sur l'ordinateur
     */
    private Computer getInfos() {
	LocalDate dateContinued = null;
	LocalDate dateDiscontinued = null;
	Long idComputer = null;
	Computer newComputer = null;
	Company newCompany = null;
	String answer;

	System.out.println("Entrez un nom pour l'ordinateur : ");
	String name = sc.nextLine();
	if (name.isEmpty()) {
	    logger.error("Le nom ne peut pas être vide");
	} else {

	    try {
		System.out.println("Entrez la date d'introduction au format YYYY-MM-DD (<Entrer> pour ignorer) : ");
		answer = sc.nextLine();
		if (answer.length() > 0) {
		    dateContinued = Date.valueOf(answer).toLocalDate();
		}
		System.out.println("Entrez la date Discontinued au format YYYY-MM-DD (<Entrer> pour ignorer) : ");
		answer = sc.nextLine();
		if (answer.length() > 0) {
		    dateDiscontinued = Date.valueOf(answer).toLocalDate();
		}
		System.out.println("Entrez l'ID de la compagnie (<Entrer> pour ignorer) : ");
		try {
		    answer = sc.nextLine();
		    if (answer.length() > 0) {
			idComputer = Long.parseLong(answer);
			newCompany = companyService.getById(idComputer);
		    }
		} catch (Exception e) {
		    logger.error("L'id doit être un nombre", e.getMessage());
		    newCompany = null;
		}
		newComputer = new Computer(name, dateContinued, dateDiscontinued, newCompany);
		System.out.println("Nouvel ordinateur : " + newComputer.toString());
	    } catch (Exception e) {
		logger.error("Erreur de format " + e.getMessage());
	    }
	}
	return newComputer;
    }

    /**
     * Mise à jour d'un ordinateur
     */
    public void updateComputer() {
	Long idComputer = getId();

	if (checkId(idComputer)) {
	    Computer newComputer = getInfos();
	    if (newComputer != null) {
		newComputer.setId(idComputer);
		computerService.update(newComputer);
	    }
	}
    }

    /**
     * Suppression d'un ordinateur
     */
    public void deleteComputer() {
	Long idComputer = getId();

	if (checkId(idComputer)) {
	    computerService.delete(idComputer);
	    System.out.println("L'ordinateur a bien été supprimé");
	}
    }

    /**
     * Suppression d'une compagnie avec les ordinateurs associés à cette compagnie.
     */
    public void deleteCompany() {
	Long idCompany = getIdCompany();

	if (checkIdCompany(idCompany)) {
	    companyService.deleteByCompany(idCompany);
	    System.out.println("La compagnie a bien été supprimé ainsi que les ordinateurs associés");
	}
    }

    /**
     * Pagination de l'affichage
     * 
     * @param newPage
     * @param nbTotal
     * @return true quand on arrête l'affichage par page, sinon false.
     */
    public static boolean optionsPages(Page newPage, int nbTotal) {
	boolean stop = false;
	System.out.println("Page " + newPage.getCurrentPage() + "/" + newPage.getTotalPages(nbTotal));
	System.out.println("Entrez 'p' pour Précédent - " + "'s' pour Suivant -" + " 'page [numero page]' "
		+ "et 'q' pour quitter");

	String input = sc.nextLine();

	switch (input) {
	case ("p"):
	    newPage.getPreviousPage();
	    return stop;
	case ("s"):
	    newPage.getNextPage(nbTotal);
	    return stop;
	case ("q"):
	    return stop = true;
	default:
	    if (input.toLowerCase().startsWith("page ")) {
		String num = input.split(" ")[1];
		parsePage(newPage, stop, num);
	    } else {
		System.out.println("Commande inconnue.");
		return stop;
	    }
	}
	return stop;
    }

    private static void parsePage(Page newPage, boolean stop, String num) {
	int idPage;
	try {
	    idPage = Integer.parseInt(num);
	    System.out.println("Vous avez demandé la page : " + idPage);
	    if (idPage > 0 && idPage <= newPage.getTotalPage()) {
		newPage.setCurrentPage(idPage);
		newPage.calculFirstLine();
	    } else {
		System.out.println("Cette page n'existe pas");
		return;
	    }
	} catch (NumberFormatException e) {
	    logger.error("Vous n'avez pas tapé un id de page valide");
	}
    }

    /**
     * Affiche les commandes possibles pour le client
     */
    public static void cmd() {
	System.out.println("Entrez votre commande : ");
	System.out.println("list - Liste des entreprises");
	System.out.println("listc - Liste des ordinateurs");
	System.out.println("computer - Détails d'un ordinateur");
	System.out.println("new - Création d'un ordinateur");
	System.out.println("upd - Mise à jour d'un ordinateur");
	System.out.println("del - Suppression d'un ordinateur");
	System.out.println("delc - Suppression d'une compagnie");
	System.out.println("help - Pour afficher les commandes");
	System.out.println("q - Quitter");
    }

    /**
     * Démarre le service CLI
     */
    public void start() {
	cmd();

	select_option();
    }

    /**
     * Traitement pour les entrées du client
     */
    public void select_option() {
	boolean isContinue = true;
	sc = new Scanner(System.in);
	String answer;

	while (isContinue) {
	    answer = sc.nextLine();
	    switch (answer) {
	    case ("list"):
		System.out.println("Liste des entreprises :");
		listAllCompanies();
		break;
	    case ("listc"):
		System.out.println("Liste des ordinateurs :");
		listAllComputers();
		break;
	    case ("computer"):
		System.out.println("Détail de l'ordinateur :");
		detailsComputer();
		break;
	    case ("new"):
		System.out.println("Création de l'ordinateur :");
		createComputer();
		break;
	    case ("upd"):
		System.out.println("Mise à jour de l'ordinateur :");
		updateComputer();
		break;
	    case ("del"):
		System.out.println("Suppression de l'ordinateur :");
		deleteComputer();
		break;
	    case ("delc"):
		System.out.println("Suppression de la compagnie :");
		deleteCompany();
		break;
	    case ("help"):
		cmd();
		break;
	    case ("q"):
		System.out.println("Merci. Au revoir !");
		isContinue = false;
		break;

	    default:
		System.out.println("Recommencez... je n'ai pas compris");
		cmd();
		break;
	    }
	}
    }

}
