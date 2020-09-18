package com.excilys.formation.cdb.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = { "com.excilys.formation.cdb.service", "com.excilys.formation.cdb.ui" })
public class ServiceConfig {

}
