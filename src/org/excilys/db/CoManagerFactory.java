package org.excilys.db;

import org.excilys.exceptions.DriverException;

/**
* Factory generating CoManager to connect to the DB
* 
* @author pqwarlot
*
*/
public class CoManagerFactory {
	/**
	 * Get CoManager to connect to the DB, can be switched between test DB and prod DB
	 * 
	 * @return CoManager
	 * @throws DriverException 
	 */
	public static CoManager getCoManager() throws DriverException {
		return CoManager.getInstance();
//		return CoManagerTest.getInstance();
	}
}
