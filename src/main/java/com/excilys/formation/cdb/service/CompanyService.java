package com.excilys.formation.cdb.service;

import java.util.List;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.persistence.CompanyDAO;

public class CompanyService {
    private static CompanyService companyService;
    private CompanyDAO companyDAO = CompanyDAO.getInstance();

    public static CompanyService getInstance() {
	if (companyService == null) {
	    companyService = new CompanyService();
	}
	return companyService;
    }

    public List<Company> getAll() {
	return companyDAO.getAll();
    }

    public int countAll() {
	return companyDAO.countAll();
    }

    public List<Company> getAllByPage(Page page) {
	return companyDAO.getByPage(page);
    }

    public void close() {
	companyDAO.closeConnect();
    }

}
