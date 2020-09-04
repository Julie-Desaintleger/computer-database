package com.excilys.formation.cdb.spring;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

@Configuration
@ComponentScan(basePackages = { "com.excilys.formation.cdb.ui", "com.excilys.formation.cdb.persistence",
	"com.excilys.formation.cdb.service" })
@PropertySource("classpath:datasource.properties")
public class SpringConfiguration extends AbstractContextLoaderInitializer {
    @Override
    protected WebApplicationContext createRootApplicationContext() {
	AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
	context.register(getClass());
	return context;
    }

    @Autowired
    Environment environment;

    private final String URL = "jdbcUrl";
    private final String USER = "dataSource.user";
    private final String DRIVER = "driverClassName";
    private final String PASSWORD = "dataSource.password";

    @Bean
    DataSource dataSource() {
	DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
	driverManagerDataSource.setUrl(environment.getProperty(URL));
	driverManagerDataSource.setUsername(environment.getProperty(USER));
	driverManagerDataSource.setPassword(environment.getProperty(PASSWORD));
	driverManagerDataSource.setDriverClassName(environment.getProperty(DRIVER));
	return driverManagerDataSource;
    }
}
