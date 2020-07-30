package com.excilys.formation.cdb.persistence;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;

import com.excilys.formation.cdb.model.Computer;

public class TestComputerDAO {

    static ComputerDAO computerDao;

    @Test
    public void testGetAll() {
	ComputerDAO computerDao = ComputerDAO.getInstance();
	List<Computer> computerList = computerDao.getAll();

	assertEquals(7, computerList.size());
    }

}
