package com.excilys.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.excilys.ResetDB;
import com.excilys.bean.ComputerDTO;
import com.excilys.exception.ConnectionException;
import com.excilys.exception.DAOException;
import com.excilys.exception.DriverException;
import com.excilys.service.mapper.MapperDTO;
import com.steadystate.css.parser.ParseException;

public class TestComputerService {
	private ComputerService computerService = null;
	private LocalDate introduced;
	private LocalDate discontinued;

	@Before
	public void setUp() throws Exception {
		ResetDB.setup();
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
		computersExpected.add(new ComputerDTO("1", "MacBook Pro 15.4 inch", null, null, "1", "Apple Inc"));
		computersExpected.add(new ComputerDTO("2", "CM-2a", null, null, "2", "Microsoft"));
		computersExpected.add(new ComputerDTO("3", "CM-200",null, null, "2", "Microsoft"));
		computersExpected.add(new ComputerDTO("4", "CM-5e", null, null, "2", "Microsoft"));
		computersExpected.add(new ComputerDTO("5", "CM-5", MapperDTO.dateEnToFr(introduced.toString()), null, "2", "Microsoft"));
		computersExpected.add(new ComputerDTO("6", "MacBook Pro", MapperDTO.dateEnToFr(discontinued.toString()), null, "1", "Apple Inc"));

		List<ComputerDTO> computers = computerService.getAll();

		Assert.assertThat(computers, is(computersExpected));
	}
	
	@Rule
	public final ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testReadFromTo() throws DAOException, ConnectionException, DriverException {
		List<ComputerDTO> computersExpected = new ArrayList<>();
		computersExpected.add(new ComputerDTO("1", "MacBook Pro 15.4 inch", null, null, "1", "Apple Inc"));

		List<ComputerDTO> computers = computerService.getAll(PageRequest.create().page(1).eltByPage(1).build());
		Assert.assertEquals(computers, computersExpected);

		computers = computerService.getAll(PageRequest.create().page(1).eltByPage(0).build());
		Assert.assertThat(computers.isEmpty(), is(true));
	}

	@Test
	public void testReadOne() throws ParseException, DAOException, ConnectionException, DriverException {
		ComputerDTO computerExpected = computerService.get(5L);

		Assert.assertEquals("5", computerExpected.getId());
		Assert.assertEquals("CM-5", computerExpected.getName());
		Assert.assertEquals(MapperDTO.dateEnToFr(introduced.toString()), computerExpected.getIntroduced());
		Assert.assertNull(computerExpected.getDiscontinued());
		Assert.assertEquals("2", computerExpected.getCompanyId());
		Assert.assertEquals("Microsoft", computerExpected.getCompanyName());
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
	
	@Test
	public void testCount() throws DAOException, ConnectionException, DriverException {
		int rowsNumber = computerService.count();
		
		Assert.assertEquals(6, rowsNumber);
	}

}
