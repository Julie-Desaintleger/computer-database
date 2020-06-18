package com.excilys.formation.cdb.persistence;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnect {
    private static Connection connection;
    private static boolean driverImported = false;
    private static String driverName;
    private static String url;
    private static String username;
    private static String password;

    public static void initVar() {
	try (InputStream input = MyConnect.class.getClassLoader().getResourceAsStream("config.properties")) {

	    Properties properties = new Properties();

	    if (input == null) {
		System.err.println("Désolé... impossible de trouver le fichier config.properties");
		return;
	    }

	    // load a properties file from class path, inside static method
	    properties.load(input);

	    // get the property value
	    driverName = properties.getProperty("driver");
	    url = properties.getProperty("db.url");

	    username = properties.getProperty("db.user");
	    password = properties.getProperty("db.password");

	} catch (IOException ex) {
	    ex.printStackTrace();
	}
    }

    public MyConnect() {
    }

    private static void init() {
	initVar();
	try {
	    Class.forName(driverName).newInstance();
	    driverImported = true;
	    return;
	} catch (Exception e) {
	    e.printStackTrace();
	    System.err.println("Erreur import du driver");
	}
    }

    public static Connection getConnection() {
	if (!driverImported)
	    init();
	try {
	    if (connection == null || connection.isClosed())
		connection = DriverManager.getConnection(url, username, password);
	    return connection;
	} catch (SQLException e) {
	    e.getMessage();
	    e.printStackTrace();
	    System.err.println("Erreur connexion à la BD");

	}
	return null;
    }

}
