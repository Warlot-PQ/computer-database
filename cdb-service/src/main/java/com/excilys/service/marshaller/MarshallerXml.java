package com.excilys.service.marshaller;

import java.io.File;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.dto.ComputersDTO;
import com.excilys.persistence.pagination.PageRequest;
import com.excilys.service.service.ComputerService;

@Service
public class MarshallerXml {
	static final Logger LOGGER = LoggerFactory.getLogger(Marshaller.class);  
	@Autowired
	ComputerService computerService;
	
	public void computerAll(String fileName, PageRequest pageRequest) {
		if (pageRequest == null) return;
		
		JAXBContext jaxbContext;
		try {
			jaxbContext = JAXBContext.newInstance(ComputersDTO.class, ComputerDTO.class);
			Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			List<ComputerDTO> computers = computerService.getAll(pageRequest);
			ComputersDTO computersDTO = new ComputersDTO();
			computersDTO.setComputers(computers);
			
			jaxbMarshaller.marshal(computersDTO, new File(fileName));
		} catch (JAXBException e) {
			LOGGER.error("marshalling error", e);
		}
	}
}
