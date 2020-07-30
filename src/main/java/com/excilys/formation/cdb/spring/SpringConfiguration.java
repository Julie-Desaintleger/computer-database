package com.excilys.formation.cdb.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import com.excilys.formation.cdb.web.ListComputersServlet;

@Configuration
@ComponentScan(basePackages = { "com.excilys.formation.cdb.persistence", "com.excilys.formation.cdb.service" })
public class SpringConfiguration implements WebApplicationInitializer {

    // https://stackoverflow.com/questions/28877982/spring-java-config-with-multiple-dispatchers
    /*
     * But the implementation should :
     * 
     * - create a root application context - gives it an initial configuration and
     * say what packages it should scan - add a ContextListener for it to the
     * servlet context - then for each dispatcher servlet -- create a child
     * application context -- gives it the same an initial configuration and
     * packages to scan -- create a DispatcherServlet using the context -- add it to
     * the servlet context
     * 
     */
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
	AnnotationConfigWebApplicationContext webCtx = new AnnotationConfigWebApplicationContext();
	webCtx.register(SpringConfiguration.class);
	webCtx.setServletContext(servletContext);

	servletContext.addListener(new ContextLoaderListener(webCtx));

	ServletRegistration.Dynamic servlet = servletContext.addServlet("listComputers", new ListComputersServlet());

	servlet.setLoadOnStartup(1);
	servlet.addMapping("/");

//
//	// dispatcher servlet 2
//	AnnotationConfigWebApplicationContext webEdit = new AnnotationConfigWebApplicationContext();
//	webEdit.setParent(webCtx);
//	webEdit.register(SpringConfiguration.class); // configuration class for servlet 2
//	ServletRegistration.Dynamic dispatcherEdit = servletContext.addServlet("editComputer",
//		new EditComputerServlet());
//	dispatcherEdit.addMapping("/");
//
//	// dispatcher servlet 3
//	AnnotationConfigWebApplicationContext webAdd = new AnnotationConfigWebApplicationContext();
//	webAdd.setParent(webCtx);
//	webAdd.register(SpringConfiguration.class); // configuration class for servlet 3
//	ServletRegistration.Dynamic dispatcherAdd = servletContext.addServlet("addComputer", new AddComputerServlet());
//	dispatcherAdd.addMapping("/");

    }

}
