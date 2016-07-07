package com.excilys.webapp.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.excilys.service.marshaller.UnmarshallerXml;

@Controller
@RequestMapping("/Import")
public class Import {
	static final Logger LOGGER = LoggerFactory.getLogger(Import.class);  
	@Autowired
	UnmarshallerXml unmarshallerXml;

	@RequestMapping(method = RequestMethod.POST)
	public void loadXml(@RequestParam("file") MultipartFile file) {
		if (file.isEmpty() == false) {
			byte[] bytes;
			try {
				// Read uploaded file
				bytes = file.getBytes();
				
				// Create the file on server
				File serverFile = new File("computerTemp.xml");
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();
				
				unmarshallerXml.loadComputers(serverFile);
				
				if (serverFile.delete()) {
					LOGGER.debug("File deleted successfully");
				} else {
					LOGGER.debug("File not deleted");
				}				
			} catch (IOException e) {
				LOGGER.debug("File upload error", e);
			}
		} else {
			LOGGER.debug("Empty file uploaded");
		}
	}
}
