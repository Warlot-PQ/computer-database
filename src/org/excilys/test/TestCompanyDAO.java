package org.excilys.test;

import static org.hamcrest.CoreMatchers.*;

import java.util.ArrayList;
import java.util.List;

import org.excilys.beans.Company;
import org.excilys.services.CompanyService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TestCompanyDAO {
	private CompanyService companyService = null;
	
	@Before
	public void setUp() throws Exception {
		companyService = new CompanyService();
	}

	@After
	public void tearDown() throws Exception {
		companyService = null;
	}

	@Test
	public void testReadAll() {
		List<Company> companiesExpected = new ArrayList<>();
		companiesExpected.add(new Company(1, "Apple Inc"));
		companiesExpected.add(new Company(2, "Microsoft"));
		
		List<Company> companies = companyService.allCompany();
		
		Assert.assertThat(companies, is(companiesExpected));
	}

}
