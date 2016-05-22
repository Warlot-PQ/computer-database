package com.excilys.cli;
import java.util.List;

import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;
import com.excilys.service.Service;

/**
 * Handle pagination for a list of entities
 * @author pqwarlot
 *
 * @param <T> Computer ou Company object
 */
public class Page<T> { // Service must implement fetch(from, to)
	private static int eltByPage = 25;
	private int currentOffset = -eltByPage;
	private Service<T> service;

	public Page(Service<T> service) {
		this.service = service;
	}
	
	/**
	 * Show entities subset regarding to the current page and handle command to move back, forward and leave listing.
	 * Blocking method while pagination is active.
	 */
	// TODO remove that
//	public void startPagination(){
//		@SuppressWarnings("resource")
//		Scanner input = new Scanner(System.in);
//		String command = null;
//		
//		do {
//			listObj();
//			System.out.printf("%n>");
//			command = input.nextLine();
//			
//			if (command.equals("n")) this.nextPage();
//			if (command.equals("p")) this.previousPage();			
//		} while (command.equals("e") == false);
//	}

	public List<T> nextPage() throws DAOException, ConnectionException, DriverException {
		currentOffset += eltByPage;
		return service.getAllFromTo(currentOffset, eltByPage);
	}
	
	/**
	 * Load previous entities page, please current page number before calling this method.
	 * @return List<Computer> or List<Company>, null or current page < 1
	 * @throws DAOException
	 * @throws ConnectionException
	 * @throws DriverException
	 */
	public List<T> previousPage() throws DAOException, ConnectionException, DriverException {
		if (getCurrentPage() < 1) {
			return null;
		}		
		currentOffset -= eltByPage;
		return service.getAllFromTo(currentOffset, eltByPage);
	}
	
	public int getCurrentPage() {
		return currentOffset / eltByPage;
	}
}
