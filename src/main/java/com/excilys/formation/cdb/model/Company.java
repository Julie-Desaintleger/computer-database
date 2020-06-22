package com.excilys.formation.cdb.model;

/**
 * Une compagnie est le fabricant d'un ordinateur.
 * 
 * @author julie
 *
 */
public class Company {
    private long id;
    private String name;

    public Company(long id, String name) {
	this.id = id;
	this.name = name;
    }

    public Company(String name) {
	super();
	this.name = name;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    @Override
    public String toString() {
	return "Company\t|\tid = " + id + "\t|\tname = " + name;
    }

}
