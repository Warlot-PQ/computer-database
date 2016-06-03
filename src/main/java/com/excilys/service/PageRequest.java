package com.excilys.service;

/**
 * Represent all informations needed to create a DB QUERY to fetch data. Set only necessery field.
 * @author pqwarlot
 *
 */
public class PageRequest {
	private Integer page;
	private Integer eltByPage;
	private Long computerId;
	private String computerSearchedName;
	private String orderBy;
	private boolean orderAlphaNumerical;
	
	private PageRequest(Builder pageRequestBuilder) {
		this.page = pageRequestBuilder.page;
		this.eltByPage = pageRequestBuilder.eltByPage;
		this.computerId = pageRequestBuilder.computerId;
		this.computerSearchedName = pageRequestBuilder.computerName;
		this.orderBy = pageRequestBuilder.orderBy;
		this.orderAlphaNumerical = pageRequestBuilder.orderAlphaNumerical;
	}

	/**
	 * Entry point to create an instance.
	 * @return
	 */
	public static Builder create() {
		return new Builder();
	}
	
	public static class Builder {
		private Integer page;
		private Integer eltByPage;
		private Long computerId;
		private String computerName;
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
			this.computerId = computerId;
			return this;
		}
		
		public Builder computerSeachedName(String computerName) {
			this.computerName = computerName;
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

	public Long getComputerId() {
		return computerId;
	}

	public void setComputerId(Long computerId) {
		this.computerId = computerId;
	}

	public String getComputerSearchedName() {
		return computerSearchedName;
	}

	public void setComputerSearchedName(String computerSearchedName) {
		this.computerSearchedName = computerSearchedName;
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
