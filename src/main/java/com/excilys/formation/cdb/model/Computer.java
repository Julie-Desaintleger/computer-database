package com.excilys.formation.cdb.model;

import java.sql.Date;

/**
 * Un ordinateur doit obligatoirement avoir au moins un nom. Si cela est
 * possible, l'ordinateur peut avoir la date à laquelle il a été introduit, la
 * date a laquelle il a été arrêté et son constructeur.
 * 
 * @author julie
 *
 */
public class Computer {
    private long id;
    private String name;
    private Date introduced;
    private Date discontinued;
    private long idCompany;

    public Computer(long id, String name, Date introduced, Date discontinued, long idCompany) {
	super();
	this.id = id;
	this.name = name;
	this.introduced = introduced;
	this.discontinued = discontinued;
	this.idCompany = idCompany;
    }

    public Computer(long id, String name) {
	super();
	this.id = id;
	this.name = name;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public Date getIntroduced() {
	return introduced;
    }

    public void setIntroduced(Date introduced) {
	this.introduced = introduced;
    }

    public Date getDiscontinued() {
	return discontinued;
    }

    public void setDiscontinued(Date discontinued) {
	this.discontinued = discontinued;
    }

    public long getIdCompany() {
	return idCompany;
    }

    public void setIdCompany(long idCompany) {
	this.idCompany = idCompany;
    }

    @Override
    public String toString() {
	return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
		+ ", idCompany=" + idCompany + "]";
    }

}
