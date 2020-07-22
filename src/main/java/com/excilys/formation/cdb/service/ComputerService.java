package com.excilys.formation.cdb.service;

import java.util.List;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.Page;
import com.excilys.formation.cdb.persistence.ComputerDAO;

public class ComputerService {
    private static ComputerService computerService;
    private static ComputerDAO computerDAO = ComputerDAO.getInstance();

    public static ComputerService getInstance() {
	if (computerService == null) {
	    computerService = new ComputerService();
	}
	return computerService;
    }

    public static List<Computer> getAll() {
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

    public List<Computer> getAllByPage(Page page) {
	return computerDAO.getByPage(page);
    }

    public List<Computer> getComputerBySearchOrdered(Page page, String research, String order) {
	return computerDAO.getBySearchOrdered(page, research, order);
    }

    public int countComputers(String research) {
	return computerDAO.count(research);
    }
}
