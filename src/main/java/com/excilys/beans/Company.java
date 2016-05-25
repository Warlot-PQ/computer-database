package com.excilys.beans;

/**
 * Company entity DB representation
 * 
 * @author pqwarlot
 *
 */
public class Company {
	private Long id;
	private String name;

	/**
	 * Construct an empty company object
	 */
	public Company() {
		this(null, null);
	}

	/**
	 * Construct a company object without id
	 * 
	 * @param name
	 *            company name
	 */
	public Company(String name) {
		this(null, name);
	}

	/**
	 * Construct a company object
	 * 
	 * @param id
	 *            company id
	 * @param name
	 *            company name
	 */
	public Company(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "id: " + getId() + " name: " + getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Company other = (Company) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}