package com.excilys.console.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.console.console.cdb.config.AppConfig;
import com.excilys.formation.ui.Cli;

public class Main {

    public static void main(String[] args) {
	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
	context.getBean(Cli.class).start();
	context.close();
    }
}
