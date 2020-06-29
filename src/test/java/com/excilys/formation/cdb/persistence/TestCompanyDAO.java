package com.excilys.formation.cdb.persistence;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.excilys.formation.cdb.model.Company;

public class TestCompanyDAO {

    static CompanyDAO companyDao;

    @Test
    public void testCountAll() {
	int nb_company_db_with_sql = CompanyDAO.getInstance().countAll();

	assertTrue(nb_company_db_with_sql == 4);
    }

    @Test
    public void testGetAll() {
	CompanyDAO companyDao = CompanyDAO.getInstance();
	List<Company> companyList = companyDao.getAll();
	List<Company> companyListToCheck = Arrays.asList(new Company(1, "Apple Inc."), new Company(2, "Nokia"),
		new Company(3, "ASUS"), new Company(4, "Hewlett-Packard"));

	assertEquals(companyList.toString(), companyListToCheck.toString());
    }

    @Test
    public void testNotGetAll() {
	CompanyDAO companyDao = CompanyDAO.getInstance();
	List<Company> companyList = companyDao.getAll();
	List<Company> companyListToCheck = Arrays.asList(new Company(1, "Apple Inc."), new Company(2, "Nokia"));

	assertNotEquals(companyList.toString(), companyListToCheck.toString());
    }

}
