package org.excilys.db;

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
	 */
	public static CoManager getCoManager() {
		return CoManager.getInstance();
//		return CoManagerTest.getInstance();
	}
}
