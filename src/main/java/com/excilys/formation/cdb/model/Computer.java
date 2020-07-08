package com.excilys.formation.cdb.model;

import java.time.LocalDate;

/**
 * Un ordinateur doit obligatoirement avoir au moins un nom. Si cela est
 * possible, l'ordinateur peut avoir la date à laquelle il a été introduit, la
 * date a laquelle il a été arrêté et son constructeur.
 * 
 * @author julie
 *
 */
public class Computer {
    private Long id;
    private String name;
    private LocalDate introduced;
    private LocalDate discontinued;
    private Company company;

    public Computer(Long id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
	this.id = id;
	this.name = name;
	this.introduced = introduced;
	this.discontinued = discontinued;
	this.company = company;
    }

    public Computer(String name, LocalDate introduced, LocalDate discontinued, Company company) {
	super();
	this.name = name;
	this.introduced = introduced;
	this.discontinued = discontinued;
	this.company = company;
    }

    public Computer(Long id, String name) {
	super();
	this.id = id;
	this.name = name;
    }

    public Computer() {
	id = null;
	name = null;
	introduced = null;
	discontinued = null;
	company = null;
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

    public LocalDate getIntroduced() {
	return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
	this.introduced = introduced;
    }

    public LocalDate getDiscontinued() {
	return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
	this.discontinued = discontinued;
    }

    public Company getCompany() {
	return company;
    }

    public void setCompany(Company company) {
	this.company = company;
    }

    @Override
    public String toString() {
	return "Computer\t|\tid = " + id + "\t|\tname = " + name + "\t|\tintroduced = " + introduced
		+ "\t|\tdiscontinued = " + discontinued + "\t|\tcompany = " + company.getName();
    }

    public static class Builder {
	private Long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;

	public Builder setId(Long id) {
	    this.id = id;
	    return this;
	}

	public Builder setName(String name) {
	    this.name = name;
	    return this;
	}

	public Builder setIntroduced(LocalDate introduced) {
	    this.introduced = introduced;
	    return this;
	}

	public Builder setDiscontinued(LocalDate discontinued) {
	    this.discontinued = discontinued;
	    return this;
	}

	public Builder setCompany(Company company) {
	    this.company = company;
	    return this;
	}

	public Computer build() {
	    Computer computer = new Computer();
	    computer.id = this.id;
	    computer.name = this.name;
	    computer.introduced = this.introduced;
	    computer.discontinued = this.discontinued;
	    computer.company = this.company;
	    return computer;
	}
    }

}
