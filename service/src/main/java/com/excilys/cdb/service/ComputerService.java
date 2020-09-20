package com.excilys.cdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.cdb.core.model.Computer;
import com.excilys.cdb.core.model.Page;
import com.excilys.cdb.persistence.dao.ComputerDAO;

@Service
public class ComputerService {
    private ComputerDAO computerDAO;

    @Autowired
    public ComputerService(ComputerDAO computerDAO) {
	this.computerDAO = computerDAO;
    }

    public List<Computer> getAll() {
	return computerDAO.getAll();
    }

    public Long countAll() {
	return computerDAO.countAll();
    }

    public Computer getById(Long id) {
	return computerDAO.findById(id);
    }

    public void insert(Computer computer) {
	computerDAO.create(computer);
    }

    public void update(Computer computer) {
	computerDAO.update(computer);
    }

    public void delete(Long id) {
	computerDAO.delete(id);
    }

    public List<Computer> getAllByPage(Page page) {
	return computerDAO.getByPage(page);
    }

    public List<Computer> getComputerBySearchOrdered(Page page) {
	return computerDAO.getBySearchOrdered(page);
    }

    public Long countComputers(Page page) {
	return computerDAO.count(page);
    }
}
