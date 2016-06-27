package com.excilys.dao;

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
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.excilys.ResetDB;
import com.excilys.core.dto.CompanyDTO;
import com.excilys.persistence.repository.interfaces.CompanyDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.excilys.persistence.config.PersistenceConfig.class, loader=AnnotationConfigContextLoader.class)
public class TestCompanyDAO {
	@Autowired
	private CompanyDAO companyDAO;
	
	@Before
	public void setUp() throws Exception {
		ResetDB.setupTest();
	}

	@Test
	public void testReadAll() {
		List<CompanyDTO> companiesExpected = new ArrayList<>();
		companiesExpected.add(new CompanyDTO("1", "Apple Inc"));
		companiesExpected.add(new CompanyDTO("2", "Microsoft"));

		List<CompanyDTO> companies = companyDAO.findAll();

		Assert.assertThat(companies, is(companiesExpected));
	}

	@Test
	public void testReadOne() {
		CompanyDTO companyExpected = new CompanyDTO("2", "Microsoft");

		CompanyDTO company = companyDAO.findById(2L);
		Assert.assertEquals(companyExpected, company);

		company = companyDAO.findById(3L);
		Assert.assertNull(company);

		company = companyDAO.findById(-3L);
		Assert.assertNull(company);

		company = companyDAO.findById(0L);
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
	public void testCount() {
		int rowsNumber = companyDAO.getRowNumber();
		
		Assert.assertEquals(2, rowsNumber);
	}
}
