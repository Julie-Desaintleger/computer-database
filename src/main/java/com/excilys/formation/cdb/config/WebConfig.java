package com.excilys.formation.cdb.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
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

    @Bean(name = "messageSource")
    public MessageSource getMessageResource() {
	ReloadableResourceBundleMessageSource messageResource = new ReloadableResourceBundleMessageSource();

	// Read i18n/messages_xxx.properties file.
	// For example: i18n/messages_en.properties

	messageResource.setBasename("classpath:i18n/messages");
	messageResource.setDefaultEncoding("ISO-8859-1");
	return messageResource;
    }

    @Bean(name = "localeResolver")
    public LocaleResolver getLocaleResolver() {
	CookieLocaleResolver resolver = new CookieLocaleResolver();
	// resolver.setCookieDomain("myAppLocaleCookie");

	return resolver;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
	LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
	localeInterceptor.setParamName("lang");

	registry.addInterceptor(localeInterceptor);
    }

}
