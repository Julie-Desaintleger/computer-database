package com.excilys.formation.cdb.model;

/**
 * Une compagnie est le fabricant d'un ordinateur.
 * 
 * @author julie
 *
 */
public class Company {
    private Long id;
    private String name;

    public Company(Long id, String name) {
	this.id = id;
	this.name = name;
    }

    public Company(String name) {
	super();
	this.name = name;
    }

    public Company() {
	id = null;
	name = null;
    }

    public Long getId() {
	return id;
    }

    public void setId(Long id) {
	this.id = id;
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

    public static class Builder {
	private Long id;
	private String name;

	public Builder setId(Long id) {
	    this.id = id;
	    return this;
	}

	public Builder setName(String name) {
	    this.name = name;
	    return this;
	}

	public Company build() {
	    Company company = new Company();
	    company.id = this.id;
	    company.name = this.name;
	    return company;
	}
    }

}
