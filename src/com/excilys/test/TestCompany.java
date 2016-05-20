package com.excilys.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Test;

import com.excilys.beans.Company;

public class TestCompany {
	private Company company = null;
	
	@After
	public void tearDown() throws Exception {
		company = null;
	}
	
	@Test
	public void testEmpty() {
		company = new Company();
		assertTrue(company.isEmpty());
	}
	
	@Test
	public void testNotEmpty1() {
		String name = "test1";
		
		company = new Company(name);
		assertFalse(company.isEmpty());
		assertEquals(name, company.getName());
	}
	
	@Test
	public void testNotEmpty2() {
		Long id = (long) 3;
		String name = "test1";
		
		company = new Company(id, name);
		assertFalse(company.isEmpty());
		assertEquals(id, company.getId());
		assertEquals(name, company.getName());
	}
}
