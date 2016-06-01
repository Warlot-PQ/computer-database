package com.excilys.Pagination;

import java.util.List;

/**
 * Handle pagination for a list of entities
 * 
 * @author pqwarlot
 *
 * @param T
 *            CompanyDTO or ComputerDTO
 */
public class Page<T> {
	private List<T> items;
	private int totalItems = 0;
	private int itemsByPage = 25;
	private int page = 1;
	private int totalPages = 1;

	public Page(List<T> entities, int totalEntities, int currentPage, int entitiesByPage) {
		this.items = entities;
		this.totalItems = totalEntities;
		this.page = currentPage;
		this.itemsByPage = entitiesByPage;
		this.totalPages = calculTotalPage(totalEntities, entitiesByPage);
	}

	public int getFirstItemNumber() {
		return (page - 1) * itemsByPage;
	}
	
	public int getLastItemNumber() {
		return (getCurrentPage() == getTotalPages()) ? getTotalItems() : getFirstItemNumber() + getItemsByPage();
	}
	
	public List<T> getItems() {
		return items;
	}

	public void setItems(List<T> entities) {
		this.items = entities;
	}

	public int getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(int totalEntities) {
		this.totalItems = totalEntities;
	}

	public int getItemsByPage() {
		return itemsByPage;
	}

	public void setItemsByPage(int entitiesByPage) {
		this.itemsByPage = entitiesByPage;
	}

	public int getCurrentPage() {
		return page;
	}

	public void setCurrentPage(int currentPage) {
		this.page = currentPage;
	}

	public int getTotalPages() {
		if (getTotalItems() < getItemsByPage()) {
			// Less than one page
			return totalPages;
		}
		// More than one page, correct last page empty if page before last fully filled.
		return (getTotalItems() % getItemsByPage() == 0) ? totalPages - 1 : totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	} 
	
	private int calculTotalPage(int totalEntities, int entitiesByPage) {
		return (int) Math.ceil(totalEntities / (double) entitiesByPage);
	}
}
