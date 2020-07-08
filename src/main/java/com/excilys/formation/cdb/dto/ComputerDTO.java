package com.excilys.formation.cdb.dto;

import java.io.Serializable;

public class ComputerDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String introduced;
    private String discontinued;
    private CompanyDTO company;

    public ComputerDTO(String id, String name) {
	this.id = id;
	this.name = name;
    }

    public ComputerDTO(String id, String name, String introduced, String discontinued, CompanyDTO company) {
	this.id = id;
	this.name = name;
	this.introduced = introduced;
	this.discontinued = discontinued;
	this.company = company;
    }

    public ComputerDTO() {
	id = null;
	name = null;
	introduced = null;
	discontinued = null;
	company = null;
    }

    public String getId() {
	return id;
    }

    public void setId(String id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getIntroduced() {
	return introduced;
    }

    public void setIntroduced(String introduced) {
	this.introduced = introduced;
    }

    public String getDiscontinued() {
	return discontinued;
    }

    public void setDiscontinued(String discontinued) {
	this.discontinued = discontinued;
    }

    public CompanyDTO getCompany() {
	return company;
    }

    public void setCompany(CompanyDTO company) {
	this.company = company;
    }

    @Override
    public String toString() {
	return "ComputerDTO\t|\tid = " + id + "\t|\tname = " + name + "\t|\tintroduced = " + introduced
		+ "\t|\tdiscontinued = " + discontinued + "\t|\tcompany = " + company;
    }

    public static class Builder {
	private String id;
	private String name;
	private String introduced;
	private String discontinued;
	private CompanyDTO company;

	public Builder setId(String id) {
	    this.id = id;
	    return this;
	}

	public Builder setName(String name) {
	    this.name = name;
	    return this;
	}

	public Builder setIntroduced(String introduced) {
	    this.introduced = introduced;
	    return this;
	}

	public Builder setDiscontinued(String discontinued) {
	    this.discontinued = discontinued;
	    return this;
	}

	public Builder setCompany(CompanyDTO company) {
	    this.company = company;
	    return this;
	}

	public ComputerDTO build() {
	    ComputerDTO computerDTO = new ComputerDTO();
	    computerDTO.id = this.id;
	    computerDTO.name = this.name;
	    computerDTO.introduced = this.introduced;
	    computerDTO.discontinued = this.discontinued;
	    computerDTO.company = this.company;
	    return computerDTO;
	}
    }

}
