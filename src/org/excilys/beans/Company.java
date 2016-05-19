package org.excilys.beans;

/**
 * Company entity DB representation
 * 
 * @author pqwarlot
 *
 */
public class Company {
	private long id;
	private String name;

	/**
	 * Construct an empty company object
	 */
	public Company() {
		this(0, "");
	}

	/**
	 * Construct a company object without id
	 * 
	 * @param name
	 *            company name
	 */
	public Company(String name) {
		this(0, name);
	}
	
	/**
	 * Construct a company object
	 * 
	 * @param id
	 *            company id
	 * @param name
	 *            company name
	 */
	public Company(long id, String name) {
		this.id = id;
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "id: " + this.getId() + " name: " + this.getName();
	}
	
	public boolean isEmpty() {
		if (getName().equals("")) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (int) (prime * result + id);
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
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
