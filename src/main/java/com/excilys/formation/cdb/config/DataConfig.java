package com.excilys.formation.cdb.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.excilys.formation.cdb.persistence" })
@PropertySource("classpath:datasource.properties")
public class DataConfig {
    private final Logger logger = LoggerFactory.getLogger(DataConfig.class);

    @Autowired
    Environment environment;

    private final String URL = "jdbcUrl";
    private final String USER = "dataSource.user";
    private final String DRIVER = "driverClassName";
    private final String PASSWORD = "dataSource.password";

    @Bean
    public DataSource dataSource() {
	logger.info("new Datasource");
	DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
	driverManagerDataSource.setUrl(environment.getProperty(URL));
	driverManagerDataSource.setUsername(environment.getProperty(USER));
	driverManagerDataSource.setPassword(environment.getProperty(PASSWORD));
	driverManagerDataSource.setDriverClassName(environment.getProperty(DRIVER));
	return driverManagerDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
	JpaTransactionManager transactionManager = new JpaTransactionManager();
	transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

	return transactionManager;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
	LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	em.setDataSource(dataSource());
	em.setPackagesToScan(new String[] { "com.excilys.computerDatabase.models" });

	JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	em.setJpaVendorAdapter(vendorAdapter);
	return em;
    }
}
