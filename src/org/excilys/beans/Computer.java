package org.excilys.beans;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

/**
 * Computer entity DB representation
 * 
 * @author pqwarlot
 *
 */
public class Computer {
	// Optional
	private long id;
	// Mandatory
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private int company_id;

	/**
	 * Construct an empty computer object
	 */
	public Computer() {
		this(0, "", new Timestamp(0), new Timestamp(0), 0);
	}
	
	/**
	 * Construct a computer object without id
	 * 
	 * @param name
	 *            computer name
	 * @param introduced
	 *            computer introduced date
	 * @param discontinued
	 *            computer discontinued date
	 * @param company_id
	 *            computer company id
	 */
	public Computer(String name, Timestamp introduced, Timestamp discontinued, int company_id) {
		this(0, name, introduced, discontinued, company_id);
	}

	/**
	 * Construct a computer object
	 * 
	 * @param id
	 *            computer unique id
	 * @param name
	 *            computer name
	 * @param introduced
	 *            computer introduced date
	 * @param discontinued
	 *            computer discontinued date
	 * @param company_id
	 *            computer company id
	 */
	public Computer(long id, String name, Timestamp introduced, Timestamp discontinued, int company_id) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company_id = company_id;
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

	/**
	 * Get introduced date, 1970-01-01 if null in DB
	 * 
	 * @return date as timestamp format
	 */
	public Timestamp getIntroduced() {
		if (introduced == null) {
			return new Timestamp(0);
		}
		return introduced;
	}

	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}

	/**
	 * Get discontinued date, 1970-01-01 if null in DB
	 * 
	 * @return date as timestamp format
	 */
	public Timestamp getDiscontinued() {
		if (discontinued == null) {
			return new Timestamp(0);
		}
		return discontinued;
	}

	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}

	public int getCompany_id() {
		return company_id;
	}

	public void setCompany_id(int company_id) {
		this.company_id = company_id;
	}

	public String toString() {
		String introduced = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(this.getIntroduced());
		String discontinued = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(this.getDiscontinued());

		String s = String.format("id: %d name: %s introduced: %s discontinued: %s company: %d", this.getId(),
				this.getName(), introduced, discontinued, this.getCompany_id());

		return s;
	}
	
	/**
	 * Check if object is empty (replace null)
	 * @return true is empty, false otherwise
	 */
	public boolean isEmpty() {
		if (getName().equals("")
				&& getDiscontinued().equals(new Timestamp(0)) 
				&& getIntroduced().equals(new Timestamp(0)) 
				&& getCompany_id() == 0) {
			return true;
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + company_id;
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = (int) (prime * result + id);
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
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
		Computer other = (Computer) obj;
		if (company_id != other.company_id)
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id != other.id)
			return false;
		if (introduced == null) {
			if (other.introduced != null)
				return false;
		} else if (!introduced.equals(other.introduced))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
