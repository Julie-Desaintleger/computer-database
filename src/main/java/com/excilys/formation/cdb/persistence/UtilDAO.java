package com.excilys.formation.cdb.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class UtilDAO {

    /*
     * Constructeur caché par défaut (car c'est une classe finale utilitaire,
     * contenant uniquement des méthode appelées de manière statique)
     */
    private UtilDAO() {
    }

    /**
     * Fermeture silencieuse du resultset
     * 
     * @param resultSet
     */
    public static void fermetureSilencieuse(ResultSet resultSet) {
	if (resultSet != null) {
	    try {
		resultSet.close();
	    } catch (SQLException e) {
		System.out.println("Échec de la fermeture du resultSet : " + e.getMessage());
	    }
	}
    }

    /**
     * Fermeture silencieuse du statement
     * 
     * @param statement
     */
    public static void fermetureSilencieuse(Statement statement) {
	if (statement != null) {
	    try {
		statement.close();
	    } catch (SQLException e) {
		System.out.println("Échec de la fermeture du statement : " + e.getMessage());
	    }
	}
    }

}
