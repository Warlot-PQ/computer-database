package com.excilys.test;

import static org.junit.Assert.*;

import java.sql.Timestamp;

import org.junit.After;
import org.junit.Test;

import com.excilys.beans.Computer;

public class TestComputer {
	private Computer computer;
	
	@After
	public void tearDown() throws Exception {
		computer = null;
	}
	
	@Test
	public void testEmpty() {
		computer = new Computer();
		assertTrue(computer.isEmpty());
	}
	
	@Test
	public void testNotEmpty1() {
		String name = "test1";
		Timestamp timestamp1 = new Timestamp(14545454);
		int companyId = 4;
		
		computer = new Computer(name, timestamp1, null, companyId);
		assertFalse(computer.isEmpty());
		assertEquals(name, computer.getName());
		assertEquals(timestamp1, computer.getIntroduced());
		assertEquals(new Timestamp(0), computer.getDiscontinued());
		assertEquals(companyId, computer.getCompany_id());
	}
	
	@Test
	public void testNotEmpty2() {
		int id = 3;
		String name = "test1";
		Timestamp timestamp1 = new Timestamp(14545454);
		int companyId = 4;
		
		computer = new Computer(id, name, timestamp1, null, companyId);
		assertFalse(computer.isEmpty());
		assertEquals(id, computer.getId());
		assertEquals(name, computer.getName());
		assertEquals(timestamp1, computer.getIntroduced());
		assertEquals(new Timestamp(0), computer.getDiscontinued());
		assertEquals(companyId, computer.getCompany_id());
	}
}
