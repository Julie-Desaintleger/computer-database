package com.excilys.cdb.console.main;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.excilys.cdb.console.ui.Cli;

public class Main {

    public static void main(String[] args) {
	AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
	context.getBean(Cli.class).start();
	context.close();
    }
}
