package com.excilys.webapp.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.core.utils.PropertiesReader;
import com.excilys.webapp.config.WebappConfig;
import com.excilys.webapp.controller.Import;

/**
 * Get i18n messages according to FR and EN settings. 
 * Messages are returned as JSON String.
 * @author pqwarlot
 *
 */
public class Messages {
	static final Logger LOGGER = LoggerFactory.getLogger(Import.class);  
	String messages;
	
	public Messages(String lang) {
		String filePath = WebappConfig.PATH_MSG;
		
		if (lang.equals("fr")) {
			filePath = "messages/messages_fr.properties";
		} else {
			filePath = "messages/messages_en.properties";
		}
		
		PropertiesReader properties = new PropertiesReader(filePath);
		
		messages = properties.toJSON();
	}

	public String getMessages() {
		return messages;
	}
}
