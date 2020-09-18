package com.excilys.console.config;

import java.util.logging.Logger;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.excilys.formation.cdb.persistence", "com.excilys.formation.cdb.service" })
public class CliConfig {
    private final Logger logger = LoggerFactory.getLogger(DataConfig.class);

    @Bean
    public HikariDataSource dataSource() {
	logger.info("new datasource");
	return new HikariDataSource(new HikariConfig("/datasource.properties"));
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
	em.setPackagesToScan(new String[] { "com.excilys.formation.cdb.model" });

	JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	em.setJpaVendorAdapter(vendorAdapter);
	return em;
    }
}
