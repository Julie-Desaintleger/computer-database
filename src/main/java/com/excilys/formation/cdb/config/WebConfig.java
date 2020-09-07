package com.excilys.formation.cdb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@EnableWebMvc
@Configuration
@ComponentScan(basePackages = { "com.excilys.formation.cdb.controller" })
public class WebConfig implements WebMvcConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(WebConfig.class);

    @Bean
    public ViewResolver viewResolver() {
	logger.info("new ViewResolver");
	InternalResourceViewResolver bean = new InternalResourceViewResolver();

	bean.setViewClass(JstlView.class);
	bean.setPrefix("/WEB-INF/views/");
	bean.setSuffix(".jsp");
	logger.debug("class : JstlView.class");
	logger.debug("prefix : /WEB-INF/views/");
	logger.debug("suffix : .jsp");

	return bean;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
	logger.info("addResourceHandlers");
	registry.addResourceHandler("/**").addResourceLocations("/");
    }

}
