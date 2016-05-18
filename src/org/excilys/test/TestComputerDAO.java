package org.excilys.test;

import static org.hamcrest.CoreMatchers.is;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.excilys.beans.Computer;
import org.excilys.services.ComputerService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class TestComputerDAO {
	private ComputerService computerService = null;
	
	@Before
	public void setUp() throws Exception {
		computerService = new ComputerService();
	}

	@After
	public void tearDown() throws Exception {
		computerService = null;
	}

	@Test
	public void testReadAll() throws ParseException {
		Timestamp timestamp1 = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1991-01-01 00:00:00").getTime());
		Timestamp timestamp2 = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2006-01-10 00:00:00").getTime());
				
		List<Computer> companiesExpected = new ArrayList<>();
		companiesExpected.add(new Computer(1, "MacBook Pro 15.4 inch", null, null, 1));
		companiesExpected.add(new Computer(2, "CM-2a", null, null, 2));
		companiesExpected.add(new Computer(3, "CM-200", null, null, 2));
		companiesExpected.add(new Computer(4, "CM-5e", null, null, 2));
		companiesExpected.add(new Computer(5, "CM-5", timestamp1, null, 2));
		companiesExpected.add(new Computer(6, "MacBook Pro", timestamp2, null, 1));
		
		List<Computer> companies = computerService.allComputer();
		
		Assert.assertThat(companies, is(companiesExpected));
	}

	@Test
	@Ignore("Not implemented yet!")
	public void testReadOne() {
		
	}

	@Test
	@Ignore("Not implemented yet!")
	public void testAddDelete() {
		
	}

	@Test
	@Ignore("Not implemented yet!")
	public void testUpdate() {
		
	}

}
