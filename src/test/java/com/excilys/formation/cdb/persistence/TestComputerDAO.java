package com.excilys.formation.cdb.persistence;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

public class TestComputerDAO {

    static ComputerDAO computerDao;

    @Test
    public void testGetAll() {
	ComputerDAO computerDao = ComputerDAO.getInstance();
	List<Computer> computerList = computerDao.getAll();

	assertEquals(9, computerList.size());
    }

}
