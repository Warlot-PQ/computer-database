package org.excilys.db;

import java.util.ResourceBundle;

public class CoManagerTest extends CoManager {
	protected static String resourceBundle = "org/excilys/db/configTest";
	
	protected CoManagerTest() {
		properties = ResourceBundle.getBundle(resourceBundle);
		
		try {
			Class.forName(properties.getString("DB_DRIVER"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized static CoManager getInstance() {
		if (instance == null) {
			instance = new CoManagerTest();
		}
		return instance;
	}
}
