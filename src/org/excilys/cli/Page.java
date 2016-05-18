package org.excilys.cli;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Handle pagination for a list of entities
 * @author pqwarlot
 *
 * @param <T> entities to list, list through toString method
 */
public class Page<T> {
	private static int eltByPage = 30;
	private List<T> entities;
	private int totalPage;
	
	private int currentPage = 1;
	private List<T> currentEntities;

	public Page(List<T> entities) {
		if (entities == null) {
			entities = new ArrayList<>();
		} else {
			this.entities = entities;
		}
		this.totalPage = (int) Math.ceil(entities.size() / eltByPage);
		this.currentEntities = entities.subList(0, currentPage * eltByPage);
	}
	
	/**
	 * Show entities subset regarding to the current page and handle command to move back, forward and leave listing.
	 * Blocking method while pagination is active.
	 */
	public void startPagination(){
		@SuppressWarnings("resource")
		Scanner input = new Scanner(System.in);
		String command = null;
		
		do {
			listObj();
			System.out.printf("%n>");
			command = input.nextLine();
			
			if (command.equals("n")) this.nextPage();
			if (command.equals("p")) this.previousPage();			
		} while (command.equals("e") == false);
	}

	private void nextPage() {
		if (currentPage < totalPage) {
			currentPage += 1;
		}
		this.currentEntities = entities.subList((currentPage - 1) * eltByPage, currentPage * eltByPage);
	}

	private void previousPage() {
		if (currentPage > 1) {
			currentPage -= 1;
		}
		this.currentEntities = entities.subList((currentPage - 1) * eltByPage, currentPage * eltByPage);
	}

	private void listObj() {
		for (Object entity : this.currentEntities) {
			System.out.println(entity.toString());
		}
		System.out.printf("%npage %d/%d, press n for next, p for previous, e to leave listing.", this.currentPage, this.totalPage);
	}
}
