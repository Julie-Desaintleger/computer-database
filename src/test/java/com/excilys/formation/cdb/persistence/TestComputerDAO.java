package com.excilys.formation.cdb.persistence;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.excilys.formation.cdb.model.Computer;

public class TestComputerDAO {

    static ComputerDAO computerDao;

    @Test
    public void testGetAll() {
	ComputerDAO computerDao = ComputerDAO.getInstance();
	List<Computer> computerList = computerDao.getAll();

	List<Computer> computerListToCheck = Arrays.asList(new Computer(1, "MacBook Pro 15.4 inch", null, null, 1),
		new Computer(2, "MacBook Pro", java.sql.Date.valueOf("2006-01-10"), null, 1),
		new Computer(3, "Apple III", java.sql.Date.valueOf("1980-05-01"), java.sql.Date.valueOf("1984-04-01"),
			1),
		new Computer(4, "Macintosh", java.sql.Date.valueOf("1984-01-24"), null, 1),
		new Computer(5, "Macintosh", java.sql.Date.valueOf("1984-01-24"), null, 1),
		new Computer(6, "HP TouchPad", java.sql.Date.valueOf("2011-02-09"), null, 4),
		new Computer(7, "Macintosh", java.sql.Date.valueOf("1984-01-24"), null, 1),
		new Computer(8, "HP Veer", java.sql.Date.valueOf("2011-02-09"), null, 4),
		new Computer(9, "Dell Vostro", null, null, 0));

	assertEquals(computerList.toString(), computerListToCheck.toString());
    }

}
