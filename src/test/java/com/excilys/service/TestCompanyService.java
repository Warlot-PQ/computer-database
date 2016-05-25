package com.excilys.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.excilys.beans.CompanyDTO;
import com.excilys.db.CoManagerFactory;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;

public class TestCompanyService {
	private CompanyService companyService = null;

	@Before
	public void setUp() throws Exception {
		CoManagerFactory.enableTest();
		companyService = CompanyService.getInstance();
	}

	@After
	public void tearDown() throws Exception {
		companyService = null;
	}

	@Test
	public void testReadAll() throws DAOException, ConnectionException, DriverException {
		List<CompanyDTO> companiesExpected = new ArrayList<>();
		companiesExpected.add(new CompanyDTO(1L, "Apple Inc"));
		companiesExpected.add(new CompanyDTO(2L, "Microsoft"));

		List<CompanyDTO> companies = companyService.getAll();

		Assert.assertThat(companies, is(companiesExpected));
	}

	@Test
	public void testReadFromTo() throws DAOException, ConnectionException, DriverException {
		List<CompanyDTO> companiesExpected = new ArrayList<>();
		companiesExpected.add(new CompanyDTO(2L, "Microsoft"));

		List<CompanyDTO> companies = companyService.getFromTo(1, 1);
		Assert.assertThat(companies, is(companiesExpected));

		companies = companyService.getFromTo(-1, 1);
		Assert.assertNull(companies);

		companies = companyService.getFromTo(1, 0);
		Assert.assertNull(companies);
	}

	@Test
	public void testReadOne() throws DAOException, ConnectionException, DriverException {
		CompanyDTO companyExpected = new CompanyDTO(2L, "Microsoft");
		
		CompanyDTO company = companyService.get(2L);
		Assert.assertEquals(companyExpected, company);

		company = companyService.get(3L);
		Assert.assertNull(company);

		company = companyService.get(-3L);
		Assert.assertNull(company);

		company = companyService.get(null);
		Assert.assertNull(company);
	}
	
	@Test
	@Ignore("Feature not implemented yet!")
	public void testCreateAndDelete() {
		fail("not implemented!");
	}
	
	@Test
	@Ignore("Feature not implemented yet!")
	public void testUpdate() {
		fail("not implemented!");
	}
}
