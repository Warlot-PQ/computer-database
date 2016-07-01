package com.excilys.persistence.pagination;

/**
 * Represent all informations needed to create a DB QUERY to fetch data. Set only necessery field.
 * @author pqwarlot
 *
 */
public class PageRequest {
	private Integer page = 1;
	private Integer eltByPage = 20;
	private Long id;
	private String searchedName;
	private String orderBy;
	private boolean orderAlphaNumerical;
	
	private PageRequest(Builder pageRequestBuilder) {
		this.page = pageRequestBuilder.page;
		this.eltByPage = pageRequestBuilder.eltByPage;
		this.id = pageRequestBuilder.id;
		this.searchedName = pageRequestBuilder.name;
		this.orderBy = pageRequestBuilder.orderBy;
		this.orderAlphaNumerical = pageRequestBuilder.orderAlphaNumerical;
	}

	/**
	 * Entry point to create an instance of a PageRequest object.
	 * @return
	 */
	public static Builder create() {
		return new Builder();
	}
	
	public static class Builder {
		private Integer page = 1;
		private Integer eltByPage = 20;
		private Long id;
		private String name;
		private String orderBy;
		private boolean orderAlphaNumerical;
		
		public Builder page(Integer page) {
			this.page = page;
			return this;
		}
		
		public Builder eltByPage(Integer eltByPage) {
			this.eltByPage = eltByPage;
			return this;
		}
		
		public Builder computerId(Long computerId) {
			this.id = computerId;
			return this;
		}
		
		public Builder computerSeachedName(String computerName) {
			this.name = computerName;
			return this;
		}
		
		public Builder orderBy(String orderBy) {
			this.orderBy = orderBy;
			return this;
		}
		
		public Builder orderAlphaNumerical(boolean orderAlphaNumerical) {
			this.orderAlphaNumerical = orderAlphaNumerical;
			return this;
		}
		
		/**
		 * Create an instance of PageRequest.
		 * @return
		 */
		public PageRequest build(){
			return new PageRequest(this);
		}
	}
	
	public int getFirstIndexElt() {
		return (getPage() - 1) * getEltByPage();
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getEltByPage() {
		return eltByPage;
	}

	public void setEltByPage(Integer eltByPage) {
		this.eltByPage = eltByPage;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSearchedName() {
		return searchedName;
	}

	public void setSearchedName(String searchedName) {
		this.searchedName = searchedName;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public boolean getOrderAlphaNumerical() {
		return orderAlphaNumerical;
	}

	public void setOrderAlphaNumerical(boolean orderAlphaNumerical) {
		this.orderAlphaNumerical = orderAlphaNumerical;
	}
}
