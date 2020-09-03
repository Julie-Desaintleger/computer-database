package com.excilys.formation.cdb.persistence;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.spring.SpringConfiguration;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SpringConfiguration.class)
public class TestComputerDAO {

    @Autowired
    ComputerDAO computerDao;

    @Test
    public void testGetAll() {
	List<Computer> computerList = computerDao.getAll();

	assertEquals(7, computerList.size());
    }

}
