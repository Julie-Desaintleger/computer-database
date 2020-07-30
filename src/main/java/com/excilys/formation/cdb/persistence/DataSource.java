package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Component
public class DataSource {

    private static HikariConfig config = new HikariConfig("/datasource.properties");
    @Autowired
    private static HikariDataSource dataSource;
    private static Logger logger = LoggerFactory.getLogger(DataSource.class);

    static {
	dataSource = new HikariDataSource(config);
    }

    private DataSource() {
    }

    public static synchronized Connection getConnection() {
	try {
	    return dataSource.getConnection();
	} catch (SQLException e) {
	    logger.error("error when connecting to datasource", e);
	}
	return null;

    }
}