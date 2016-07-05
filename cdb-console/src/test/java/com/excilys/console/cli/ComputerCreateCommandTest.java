package com.excilys.console.cli;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.excilys.console.restClient.ClientRestComputer;
import com.excilys.console.restClient.ReturnRest;
import com.excilys.core.dto.ComputerDTO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.excilys.console.config.ConsoleConfig.class, loader=AnnotationConfigContextLoader.class)
public class ComputerCreateCommandTest {
	
	private ComputerCreateCommand ccc = new ComputerCreateCommand();
	@Mock
	private ClientRestComputer clientRest;

	@Rule 
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Before
	public void setUp() {
		ReturnRest<String> returnRest = new ReturnRest<String>(200, "Test success create computer");
		when(clientRest.createComputer(any(ComputerDTO.class))).thenReturn(returnRest);
		
		ccc.setClientRest(clientRest);
	}
	
	/**
	 * Create a computer and check that creation method call is executed once on rest API
	 */
	@Test
	public void createComputer() {
		ByteArrayInputStream in = new ByteArrayInputStream("My string\n02/12/2015\n\n3".getBytes());
		System.setIn(in);
		
		ccc.execute(new Scanner(System.in));
		verify(clientRest, times(1)).createComputer(any(ComputerDTO.class));
	}
	
	/**
	 * Create a computer with wrong introduced date and check that creation method call is never executed rest API
	 */
	@Test
	public void createComputerWrongDateCalender() {
		ByteArrayInputStream in = new ByteArrayInputStream("My string\n32/12/2015\n\n3".getBytes());
		System.setIn(in);
		
		ccc.execute(new Scanner(System.in));
		verify(clientRest, never()).createComputer(any(ComputerDTO.class));
	}
	
	/**
	 * Create a computer with wrong introduced date and check that creation method call is never executed rest API
	 */
	@Test
	public void createComputerWrongDateString() {
		ByteArrayInputStream in = new ByteArrayInputStream("My string\nHelloMyNameIsDoris\n\n3".getBytes());
		System.setIn(in);
		
		ccc.execute(new Scanner(System.in));
		verify(clientRest, never()).createComputer(any(ComputerDTO.class));
	}
}
