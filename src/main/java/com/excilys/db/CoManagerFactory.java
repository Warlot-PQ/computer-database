package com.excilys.db;

import com.excilys.exceptions.DriverException;

/**
* Factory generating CoManager to connect to the DB
* 
* @author pqwarlot
*
*/
public class CoManagerFactory {
	private static boolean MODE_TEST = false;
	
	private CoManagerFactory() {
	}
	
	/**
	 * Get CoManager to connect to the DB, can be switched between test DB and prod DB
	 * 
	 * @return CoManager
	 * @throws DriverException 
	 */
	public static CoManager getCoManager() throws DriverException {
		if (CoManagerFactory.MODE_TEST == true) {
			return CoManagerTest.getInstance();
		} else {
			return CoManager.getInstance();
		}
	}
	
	public static void enableTest() {
		CoManagerFactory.MODE_TEST = true;
	}
	
	public static void disableTest() {
		CoManagerFactory.MODE_TEST = false;
	}
}
