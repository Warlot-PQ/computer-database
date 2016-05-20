package org.excilys.test;

import static org.hamcrest.CoreMatchers.is;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.excilys.beans.Computer;
import org.excilys.service.ComputerService;
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
	public void testGetComputer() throws ParseException {
		Computer computerToCreate = null;
		Timestamp discontinuedTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1991-01-01 00:00:00").getTime());
		
		computerToCreate = computerService.getComputer(5);
		
		Assert.assertEquals(computerToCreate.getId(), 5);
		Assert.assertEquals(computerToCreate.getName(), "CM-5");
		Assert.assertEquals(computerToCreate.getIntroduced(), discontinuedTime);
		Assert.assertEquals(computerToCreate.getDiscontinued(), new Timestamp(0));
		Assert.assertEquals(computerToCreate.getCompany_id(), 2);
	}

	@Test
	public void testAddDelete() throws ParseException {
		Timestamp discontinuedTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1991-01-01 00:00:00").getTime());
		Timestamp introducedTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2006-01-10 00:00:00").getTime());
		Computer computerCreated = null;
		Computer computerToCreate = null;
	
		computerToCreate = new Computer("good computer", null, discontinuedTime, 2);
		computerCreated = computerService.createComputer(computerToCreate);
		Assert.assertFalse(computerCreated.isEmpty());
		
		Assert.assertTrue( computerService.deleteComputer(computerCreated.getId()) );
		Assert.assertTrue( computerService.getComputer(computerCreated.getId()).isEmpty() );

		
		computerToCreate = new Computer("good computer", introducedTime, null, 2);
		computerCreated = computerService.createComputer(computerToCreate);
		Assert.assertFalse(computerCreated.isEmpty());
		
		Assert.assertTrue( computerService.deleteComputer(computerCreated.getId()) );
		Assert.assertTrue( computerService.getComputer(computerCreated.getId()).isEmpty() );
		
		
		computerToCreate = new Computer("good computer", null, null, 2);
		computerCreated = computerService.createComputer(computerToCreate);
		Assert.assertFalse(computerCreated.isEmpty());
		
		Assert.assertTrue( computerService.deleteComputer(computerCreated.getId()) );
		Assert.assertTrue( computerService.getComputer(computerCreated.getId()).isEmpty() );
	}

	@Test
	@Ignore("Not implemented yet!")
	public void testAddWrongCompany() throws ParseException {
	}
	
	@Test
	@Ignore("Not implemented yet!")
	public void testUpdate() throws ParseException {
	}

	@Test
	/**
	 * Try to add computer with introducedTime greater than discontinuedTime
	 * @throws ParseException
	 */
	public void testAddWrongComputer() throws ParseException {
		Timestamp discontinuedTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1991-01-01 00:00:00").getTime());
		Timestamp introducedTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2006-01-10 00:00:00").getTime());
		Computer computerToCreate = null;
		
		computerToCreate = new Computer("wrong computer", introducedTime, discontinuedTime, 2);
		
		Assert.assertTrue( computerService.createComputer(computerToCreate).isEmpty() );
	}

	@Test
	/**
	 * Try to update computer with introducedTime greater than discontinuedTime
	 * @throws ParseException
	 */
	public void testupdateWrongComputer() throws ParseException {
		Timestamp discontinuedTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1991-01-01 00:00:00").getTime());
		Timestamp introducedTime = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2006-01-10 00:00:00").getTime());
		Computer computerCreated = null;
		Computer computerToCreate = null;
		
		computerToCreate = new Computer("wrong computer", introducedTime, discontinuedTime, 2);
		
		computerCreated = computerService.createComputer(computerToCreate);
		Assert.assertTrue(computerCreated.isEmpty());
	}
}
