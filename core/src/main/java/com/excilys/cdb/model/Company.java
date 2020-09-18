package com.excilys.cdb.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Une compagnie est le fabricant d'un ordinateur.
 * 
 * @author julie
 *
 */
@Entity(name = "Company")
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    public Company() {
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
	private Long id = null;
	private String name = null;

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
