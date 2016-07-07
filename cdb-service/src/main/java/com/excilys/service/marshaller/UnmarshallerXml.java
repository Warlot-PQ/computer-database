package com.excilys.service.marshaller;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.binding.ComputerMapper;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.dto.ComputersDTO;
import com.excilys.service.service.ComputerService;

@Service
public class UnmarshallerXml {
	static final Logger LOGGER = LoggerFactory.getLogger(Marshaller.class);  
	@Autowired
	ComputerService computerService;
	
	public void loadComputers(File file) {
		JAXBContext jaxbContext = null;
		ComputersDTO computersDTO = null;
		// Unmarshall
		try {
			jaxbContext = JAXBContext.newInstance(ComputersDTO.class, ComputerDTO.class);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
			
			computersDTO = (ComputersDTO) jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			LOGGER.error("Unmarshalling error", e);
		}

		// Create computers
		if (computersDTO != null && computersDTO.getComputers() != null) {
			for (ComputerDTO computerDTO: computersDTO.getComputers()) {
				try {
					computerService.create(ComputerMapper.toEntity(computerDTO));
					LOGGER.debug("entry added {}", computerDTO);
				} catch (Exception e) {
					LOGGER.debug("entry not added {}", computerDTO);
				}
			}
		}
	}
}
