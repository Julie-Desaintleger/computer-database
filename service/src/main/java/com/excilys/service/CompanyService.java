package com.excilys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.persistence.CompanyDAO;

@Service
public class CompanyService {
    private CompanyDAO companyDAO;

    @Autowired
    public CompanyService(CompanyDAO companyDAO) {
	this.companyDAO = companyDAO;
    }

    public List<Company> getAll() {
	return companyDAO.getAll();
    }

    public Long countAll() {
	return companyDAO.countAll();
    }

    public List<Company> getAllByPage(Page page) {
	return companyDAO.getByPage(page);
    }

    public Company getById(Long id) {
	return companyDAO.findById(id);
    }

    public void deleteByCompany(Long idCompany) {
	companyDAO.deleteByCompany(idCompany);
    }

}
