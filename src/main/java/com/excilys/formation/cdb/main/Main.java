package com.excilys.formation.cdb.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.formation.cdb.spring.SpringConfiguration;
import com.excilys.formation.cdb.ui.Cli;

public class Main {

    public static void main(String[] args) {
	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
	context.getBean(Cli.class).start();
	context.close();
    }
}
