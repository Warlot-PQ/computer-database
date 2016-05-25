package com.excilys.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.excilys.beans.ComputerDTO;
import com.excilys.db.CoManagerFactory;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;

public class TestComputerService {
	private ComputerService computerService = null;
	private LocalDate introduced;
	private LocalDate discontinued;

	@Before
	public void setUp() throws Exception {
		CoManagerFactory.enableTest();
		computerService = ComputerService.getInstance();

		introduced = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("1991-01-01 00:00:00").getTime())
				.toLocalDateTime().toLocalDate();
		discontinued = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2006-01-10 00:00:00").getTime())
				.toLocalDateTime().toLocalDate();
	}

	@After
	public void tearDown() throws Exception {
		computerService = null;
	}

	@Test
	public void testReadAll() throws ParseException, DAOException, ConnectionException, DriverException {
		List<ComputerDTO> computersExpected = new ArrayList<>();
		computersExpected.add(new ComputerDTO(1L, "MacBook Pro 15.4 inch", null, null, 1L, "Apple Inc"));
		computersExpected.add(new ComputerDTO(2L, "CM-2a", null, null, 2L, "Microsoft"));
		computersExpected.add(new ComputerDTO(3L, "CM-200", null, null, 2L, "Microsoft"));
		computersExpected.add(new ComputerDTO(4L, "CM-5e", null, null, 2L, "Microsoft"));
		computersExpected.add(new ComputerDTO(5L, "CM-5", introduced, null, 2L, "Microsoft"));
		computersExpected.add(new ComputerDTO(6L, "MacBook Pro", discontinued, null, 1L, "Apple Inc"));

		List<ComputerDTO> computers = computerService.getAll();

		Assert.assertThat(computers, is(computersExpected));
	}

	@Test
	public void testReadFromTo() throws DAOException, ConnectionException, DriverException {
		List<ComputerDTO> computersExpected = new ArrayList<>();
		computersExpected.add(new ComputerDTO(2L, "CM-2a", null, null, 2L, "Microsoft"));

		List<ComputerDTO> computers = computerService.getFromTo(1, 1);
		Assert.assertThat(computers, is(computersExpected));

		computers = computerService.getFromTo(-1, 1);
		Assert.assertNull(computers);

		computers = computerService.getFromTo(1, 0);
		Assert.assertNull(computers);
	}

	@Test
	public void testReadOne() throws ParseException, DAOException, ConnectionException, DriverException {
		ComputerDTO computerToCreate = null;

		computerToCreate = computerService.get(5L);

		Assert.assertEquals(computerToCreate.getId().longValue(), 5L);
		Assert.assertEquals(computerToCreate.getName(), "CM-5");
		Assert.assertEquals(computerToCreate.getIntroduced(), introduced);
		Assert.assertNull(computerToCreate.getDiscontinued());
		Assert.assertEquals(computerToCreate.getCompanyId().longValue(), 2L);
		Assert.assertEquals(computerToCreate.getCompanyName(), "Microsoft");
	}

	@Test
	@Ignore("Feature not implemented yet!")
	public void testCreateAndDelete() throws ParseException {
		fail("not implemented!");
	}

	@Test
	@Ignore("Not implemented yet!")
	public void testUpdate() throws ParseException {
		fail("not implemented!");
	}

}
