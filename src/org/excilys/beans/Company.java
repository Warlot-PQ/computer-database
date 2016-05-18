package org.excilys.beans;

/**
 * Company entity DB representation
 * 
 * @author pqwarlot
 *
 */
public class Company {
	private int id;
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
	public Company(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
}
