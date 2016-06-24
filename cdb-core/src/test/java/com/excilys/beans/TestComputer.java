package com.excilys.beans;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;

import org.junit.After;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.excilys.core.entity.Company;
import com.excilys.core.entity.Computer;

public class TestComputer {
	private Computer computer;

	@After
	public void tearDown() throws Exception {
		computer = null;
	}

	@Test
	@Ignore
	public void testEmpty() {
		computer = new Computer();

		assertNull(computer.getId());
		assertNull(computer.getName());
		assertNull(computer.getDiscontinued());
		assertNull(computer.getId());
		assertNull(computer.getCompanyId());
	}

	@Test
	public void testNotEmpty1() {
		String name = "test1";
		Timestamp timestamp1 = new Timestamp(14545454);

		computer = new Computer(name, timestamp1.toLocalDateTime().toLocalDate(), null, new Company(4L, "Netronics"));
		assertNotNull(computer);
		assertEquals(name, computer.getName());
		assertEquals(timestamp1.toLocalDateTime().toLocalDate(), computer.getIntroduced());
		assertNull(computer.getDiscontinued());
		Assert.assertThat(new Company(4L, "Netronics"), is(computer.getCompany()));
	}

	@Test
	public void testNotEmpty2() {
		Long id = 3L;
		String name = "test1";
		Timestamp timestamp1 = new Timestamp(14545454);

		computer = new Computer(id, name, null, timestamp1.toLocalDateTime().toLocalDate(), new Company(4L, "Netronics"));
		assertNotNull(computer);
		assertEquals(id, computer.getId());
		assertEquals(name, computer.getName());
		assertNull(computer.getIntroduced());
		assertEquals(timestamp1.toLocalDateTime().toLocalDate(), computer.getDiscontinued());
		Assert.assertThat(new Company(4L, "Netronics"), is(computer.getCompany()));
	}
}
