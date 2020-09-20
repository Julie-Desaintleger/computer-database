package com.excilys.cdb.binding.dto;

import java.io.Serializable;

public class CompanyDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;

    public CompanyDTO(String id, String name) {
	this.id = id;
	this.name = name;
    }

    public CompanyDTO() {
	id = null;
	name = null;
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

    @Override
    public String toString() {
	return "CompanyDTO\t|\tid = " + id + "\t|\tname = " + name;
    }

    public static class Builder {
	private String id;
	private String name;

	public Builder setId(String id) {
	    this.id = id;
	    return this;
	}

	public Builder setName(String name) {
	    this.name = name;
	    return this;
	}

	public CompanyDTO build() {
	    CompanyDTO companyDTO = new CompanyDTO();
	    companyDTO.id = this.id;
	    companyDTO.name = this.name;
	    return companyDTO;
	}
    }

}
