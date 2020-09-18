package com.excilys.formation.cdb.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class MainWebAppInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext context) throws ServletException {

	AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();
	rootContext.register(AppConfig.class);

	AnnotationConfigWebApplicationContext dispatcherContext = new AnnotationConfigWebApplicationContext();
	dispatcherContext.register(WebConfig.class);

	DispatcherServlet dispatcherServlet = new DispatcherServlet(dispatcherContext);
	dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);

	ServletRegistration.Dynamic dispatcherRegistration = context.addServlet("Dispatcher", dispatcherServlet);
	dispatcherRegistration.setLoadOnStartup(1);
	dispatcherRegistration.addMapping("/");

	context.addListener(new ContextLoaderListener(rootContext));
    }
}