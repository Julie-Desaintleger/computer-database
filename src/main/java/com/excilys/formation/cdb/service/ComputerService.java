package com.excilys.formation.cdb.service;

import java.util.List;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.ComputerDAO;

public class ComputerService {
    private static ComputerService computerService;
    private ComputerDAO computerDAO = ComputerDAO.getInstance();

    public static ComputerService getInstance() {
	if (computerService == null) {
	    computerService = new ComputerService();
	}
	return computerService;
    }

    public List<Computer> getAll() {
	return computerDAO.getAll();
    }

    public int countAll() {
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
}
