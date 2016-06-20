package com.excilys.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;

import com.excilys.ResetDB;
import com.excilys.bean.CompanyDTO;
import com.excilys.exception.ConnectionException;
import com.excilys.exception.DriverException;
import com.excilys.service.interfaces.CompanyService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.excilys.spring.AppConfig.class, loader=AnnotationConfigWebContextLoader.class)
@WebAppConfiguration
public class TestCompanyService {
	@Autowired
	private CompanyService companyService;
	
	
	@Before
	public void setUp() throws Exception {
		ResetDB.setupTest();
	}

	@Test
	public void testReadAll() throws ConnectionException, DriverException {
		List<CompanyDTO> companiesExpected = new ArrayList<>();
		companiesExpected.add(new CompanyDTO("1", "Apple Inc"));
		companiesExpected.add(new CompanyDTO("2", "Microsoft"));

		List<CompanyDTO> companies = companyService.getAll();

		Assert.assertThat(companies, is(companiesExpected));
	}

	@Test
	public void testReadOne() throws ConnectionException, DriverException {
		CompanyDTO companyExpected = new CompanyDTO("2", "Microsoft");

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
	
	@Test
	public void testCount() throws ConnectionException, DriverException {
		int rowsNumber = companyService.count();
		
		Assert.assertEquals(2, rowsNumber);
	}
}
