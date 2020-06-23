package com.excilys.formation.cdb.ui;

import java.util.List;
import java.util.Scanner;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;

public class Cli {
    private static Scanner sc;
    private static CompanyService companyService;
    private static ComputerService computerService;

    /**
     * Liste toutes les entreprises dans la base
     */
    public static void listAllCompanies() {
	boolean isCompleted = false;

	while (!isCompleted) {
	    List<Company> allCompanies = companyService.getAll();
	    allCompanies.forEach(cp -> System.out.println(cp.toString()));
	    isCompleted = true;
	}
    }

    /**
     * Liste tous les ordinateurs dans la base
     */
    public static void listAllComputers() {
	boolean isCompleted = false;

	while (!isCompleted) {
	    List<Computer> allComputers = computerService.getAll();
	    allComputers.forEach(cm -> System.out.println(cm.toString()));
	    isCompleted = true;
	}
    }

    /**
     * Affiche les détails d'un ordinateur.
     */
    public static void detailsComputer() {
	Long idComputer = getId();
	if (idComputer != null) {
	    checkId(idComputer);
	} else {
	    System.err.println("Erreur pour la récupération de l'id");
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
		System.err.println("Entrez un nombre..." + e.getMessage());
	    }
	}
	return idComputer;
    }

    /**
     * Vérification sur l'entier saisie appartient bien a un ordinateur en base
     * 
     * @param idComputer l'identifiant d'ordinateur à chercher
     * @return true si l'identifiant existe, sinon false.
     */
    private static boolean checkId(Long idComputer) {
	boolean isPresent = false;
	if (idComputer != null) {
	    Computer c = computerService.getById(idComputer);
	    if (c != null) {
		isPresent = true;
		System.out.println("L'id correspond à l'ordinateur suivant : \n" + c.toString());
	    } else {
		System.err.println("L'id ne correspond à aucun ordinateur");
	    }
	} else {
	    System.err.println("Erreur récupération sur l'id");
	}
	return isPresent;
    }

    /**
     * Affiche les commandes possibles pour le client
     */
    public static void start() {
	System.out.println("Entrez votre commande : ");
	System.out.println("0 - Liste des entreprises");
	System.out.println("1 - Liste des ordinateurs");
	System.out.println("2 - Détails d'un ordinateur");
	System.out.println("3 - Création d'un ordinateur");
	System.out.println("4 - Mise à jour d'un ordinateur");
	System.out.println("5 - Suppression d'un ordinateur");
	System.out.println("6 - Quitter");

	companyService = CompanyService.getInstance();
	computerService = ComputerService.getInstance();

	select_option();
    }

    /**
     * Traitement pour les entrées du client
     */
    public static void select_option() {
	boolean isContinue = true;
	sc = new Scanner(System.in);
	String answer;

	while (isContinue) {
	    answer = sc.nextLine();
	    switch (answer) {
	    case ("0"):
		System.out.println("Liste des entreprises :");
		listAllCompanies();
		break;
	    case ("1"):
		System.out.println("Liste des ordinateurs :");
		listAllComputers();
		break;
	    case ("2"):
		System.out.println("Détail de l'ordinateur :");
		detailsComputer();
		break;
	    case ("6"):
		System.out.println("Merci. Au revoir !");
		isContinue = false; // TO DO close connexion
		break;

	    default:
		System.out.println("Recommencez... je n'ai pas compris");
		break;
	    }

	}
    }

}
