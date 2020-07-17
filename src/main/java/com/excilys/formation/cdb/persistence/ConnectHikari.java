package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class ConnectHikari {
    private static HikariConfig config = new HikariConfig("/datasource.properties");
    private static HikariDataSource ds;

    private ConnectHikari() {
	ds = new HikariDataSource(config);
    }

    private static class ConnectionHikariHolder {
	private final static ConnectHikari instance = new ConnectHikari();
    }

    public static ConnectHikari getInstance() {
	return ConnectionHikariHolder.instance;
    }

    public static Connection getConnection() throws SQLException {
	return ds.getConnection();
    }
}
