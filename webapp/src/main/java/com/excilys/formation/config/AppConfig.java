package com.excilys.formation.cdb.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ DataConfig.class, ServiceConfig.class })
public class AppConfig {

}
