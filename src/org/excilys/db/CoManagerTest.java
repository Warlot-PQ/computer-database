package org.excilys.db;

import java.util.ResourceBundle;

import org.excilys.exceptions.DriverException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CoManagerTest extends CoManager {
	protected static String resourceBundle = "com/excilys/db/configTest";
	
	protected CoManagerTest() throws DriverException {
		properties = ResourceBundle.getBundle(resourceBundle);
		
		try {
			Class.forName(properties.getString("DB_DRIVER"));
		} catch (ClassNotFoundException e) {
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.error("Driver load error!", e);
			
			throw new DriverException(e);
		}
	}
	
	public static CoManager getInstance() throws DriverException {
		if (instance == null) {
			synchronized (CoManagerTest.class) {
				if (instance == null) {
					instance = new CoManagerTest();
				}
			}
		}
		return instance;
	}
}
