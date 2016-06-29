package com.excilys.webapp.controller.mapper;

import javax.annotation.PostConstruct;
import javax.validation.constraints.Min;

/**
 * Get/set attribute from/to an http request.
 * 
 * @author pqwarlot
 *
 */
public class RequestModel {
	private static final int DEFAULT_PAGE = 1;
	private static final int DEFAULT_LIMIT = 20;
	@Min(1)
	private int page = DEFAULT_PAGE;
	@Min(10)
	private int limit = DEFAULT_LIMIT;
	private String search;
	private String orderBy;
	private boolean orderAlphaNum = true; 
	private String selection;
	
	public RequestModel(){
	}
	
	@PostConstruct
	public void resetOnError() {
		if (getPage() < 1 || getLimit() < 10) {
			reset();
		}
	}
	
	public void reset() {
		setPage(DEFAULT_PAGE);
		setLimit(DEFAULT_LIMIT);
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getSearch() {
		return search;
	}
	
	public void setSearch(String search) {
		this.search = search;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean isOrderAlphaNum() {
		return orderAlphaNum;
	}

	public void setOrderAlphaNum(boolean orderAlphaNum) {
		this.orderAlphaNum = orderAlphaNum;
	}
	
	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}
	
	@Override
	public String toString() {
		return "RequestModel [page=" + page + ", limit=" + limit + ", search=" + search + ", orderBy=" + orderBy
				+ ", orderAlphaNum=" + orderAlphaNum + ", computersSelected=" + selection + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((selection == null) ? 0 : selection.hashCode());
		result = prime * result + limit;
		result = prime * result + (orderAlphaNum ? 1231 : 1237);
		result = prime * result + ((orderBy == null) ? 0 : orderBy.hashCode());
		result = prime * result + page;
		result = prime * result + ((search == null) ? 0 : search.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RequestModel other = (RequestModel) obj;
		if (selection == null) {
			if (other.selection != null)
				return false;
		} else if (!selection.equals(other.selection))
			return false;
		if (limit != other.limit)
			return false;
		if (orderAlphaNum != other.orderAlphaNum)
			return false;
		if (orderBy == null) {
			if (other.orderBy != null)
				return false;
		} else if (!orderBy.equals(other.orderBy))
			return false;
		if (page != other.page)
			return false;
		if (search == null) {
			if (other.search != null)
				return false;
		} else if (!search.equals(other.search))
			return false;
		return true;
	}
}
