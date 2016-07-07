package com.excilys.service.service;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.excilys.core.dto.ComputerDTO;
import com.excilys.persistence.pagination.PageRequest;
import com.excilys.persistence.repository.ComputerDAO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=com.excilys.service.config.ServiceConfig.class, loader=AnnotationConfigContextLoader.class)
public class ComputerServiceTest {
	@Autowired
	ComputerService computerService;
	ComputerDAO computerDAO;
	
	ComputerDTO computerDTO1 = new ComputerDTO("test123", "03/02/2015", "", "5");

	@Rule 
	public MockitoRule mockitoRule = MockitoJUnit.rule();

	@Before
	public void setUp() {
		computerDAO = Mockito.mock(ComputerDAO.class);
		// Set findAll method return value
		List<ComputerDTO> list = new ArrayList<>();
		list.add(computerDTO1);
		when(computerDAO.findAll(any(PageRequest.class))).thenReturn(list);
		
		computerService.setComputerDAO(computerDAO);
	}

	@Test
	public void getComputer() {
		ComputerDTO computerDTO = computerService.get(30L);
		// Check findAll was called once
		verify(computerDAO, times(1)).findAll(any(PageRequest.class));
		
		Assert.assertEquals(computerDTO1, computerDTO);		
	}
	
	@Test
	public void getComputerWrong() {
		ComputerDTO computerDTO = computerService.get(null);
		// Check findAll was called once
		verify(computerDAO, never()).findAll(any(PageRequest.class));
		
		Assert.assertNull(computerDTO);		
	}
}
