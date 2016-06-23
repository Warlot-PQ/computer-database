package com.excilys.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.After;
import org.junit.Test;

import com.excilys.entity.Company;

public class TestCompany {
	private Company company = null;

	@After
	public void tearDown() throws Exception {
		company = null;
	}

	@Test
	public void testEmpty() {
		company = new Company();

		assertNull(company.getId());
		assertNull(company.getName());
	}

	@Test
	public void testNotEmpty1() {
		String name = "test1";

		company = new Company(name);

		assertNull(company.getId());
		assertEquals(name, company.getName());
	}

	@Test
	public void testNotEmpty2() {
		Long id = 3L;
		String name = "test1";

		company = new Company(id, name);

		assertEquals(id, company.getId());
		assertEquals(name, company.getName());
	}
}
