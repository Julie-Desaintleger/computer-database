package com.excilys.formation.cdb.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.excilys.formation.cdb.config.AppConfig;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class TestCompanyDAO {

    @Autowired
    CompanyDAO companyDao;

    @Autowired
    ComputerDAO computerDao;

    @Test
    public void testCountAll() {
	int nb_company_db_with_sql = companyDao.countAll();

	assertTrue(nb_company_db_with_sql == 3);
    }

    @Test
    public void testGetAll() {
	List<Company> companyList = companyDao.getAll();
	List<Company> companyListToCheck = Arrays.asList(new Company(Long.valueOf(1), "Apple Inc."),
		new Company(Long.valueOf(2), "Nokia"), new Company(Long.valueOf(3), "ASUS"));

	assertEquals(companyList.toString(), companyListToCheck.toString());
    }

    @Test
    public void testNotGetAll() {
	List<Company> companyList = companyDao.getAll();
	List<Company> companyListToCheck = Arrays.asList(new Company(Long.valueOf(1), "Apple Inc."),
		new Company(Long.valueOf(2), "Nokia"));

	assertNotEquals(companyList.toString(), companyListToCheck.toString());
    }

    @Test
    public void testDeleteByCompany() {
	companyDao.deleteByCompany(Long.valueOf(4));
	List<Computer> computers = computerDao.getAll();

	assertEquals(7, computers.size());
	assertEquals(3, companyDao.countAll());
    }
}
