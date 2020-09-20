package com.excilys.cdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.core.model.Company;
import com.excilys.cdb.core.model.Page;
import com.excilys.cdb.persistence.dao.CompanyDAO;

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
