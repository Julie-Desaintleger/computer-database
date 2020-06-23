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

    public static void listAllComputers() {
	boolean isCompleted = false;

	while (!isCompleted) {
	    List<Computer> allComputers = computerService.getAll();
	    allComputers.forEach(cm -> System.out.println(cm.toString()));
	    isCompleted = true;
	}
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
