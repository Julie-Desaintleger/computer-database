package com.excilys.cdb.core.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Un ordinateur doit obligatoirement avoir au moins un nom. Si cela est
 * possible, l'ordinateur peut avoir la date à laquelle il a été introduit, la
 * date a laquelle il a été arrêté et son constructeur.
 * 
 * @author julie
 *
 */
@Entity
@Table(name = "computer")
public class Computer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "introduced")
    private LocalDate introduced;

    @Column(name = "discontinued")
    private LocalDate discontinued;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public Computer() {
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
		+ "\t|\tdiscontinued = " + discontinued + "\t|\tcompany = " + company;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj) {
	    return true;
	}
	if (obj == null) {
	    return false;
	}
	if (getClass() != obj.getClass()) {
	    return false;
	}
	Computer other = (Computer) obj;
	if (!this.getCompany().getId().equals(other.getCompany().getId())) {
	    return false;
	}
	if (discontinued == null) {
	    if (other.discontinued != null) {
		return false;
	    }
	} else if (!discontinued.equals(other.discontinued)) {
	    return false;
	}
	if (introduced == null) {
	    if (other.introduced != null) {
		return false;
	    }
	} else if (!introduced.equals(other.introduced)) {
	    return false;
	}
	if (name == null) {
	    if (other.name != null) {
		return false;
	    }
	} else if (!name.equals(other.name)) {
	    return false;
	}
	return true;
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
