package com.excilys.core.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertiesReader {
	static final Logger LOGGER = LoggerFactory.getLogger(PropertiesReader.class);
	static final String errorMsg = "Cannot load properties languages file from ";
	Properties properties = new Properties();
	
	public PropertiesReader(String filePath) {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(filePath);
		
		if (inputStream != null) {
			try {
				properties.load(inputStream);
			} catch (IOException e) {
				LOGGER.error(errorMsg + filePath, e);
				System.exit(-1);
			}
		} else {
			LOGGER.error(errorMsg + filePath);
			System.exit(-1);			
		}
	}
	
	public Enumeration<?> propertyNames() {
		return properties.propertyNames();
	}
	
	public String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}
	
	/**
	 * Get properties file content as JSON string encoded in UTF-8
	 * @return String containing JSON
	 */
	public String toJSON() {
		String jsonStr = "{";
		Enumeration<?> elts = properties.propertyNames();
		while(elts.hasMoreElements()) {
			String elt = (String) elts.nextElement();
			
			jsonStr += "\"" + elt + "\"";
			jsonStr += ":";
			jsonStr += "\"" + properties.getProperty(elt) + "\"";
			
			if (elts.hasMoreElements()) {
				jsonStr += ",";
			}
		}
		jsonStr += "}";
		
		try {
			jsonStr = new String(jsonStr.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("Encoding error", e);
			System.exit(-1);
		}
		return jsonStr;
	}
}
