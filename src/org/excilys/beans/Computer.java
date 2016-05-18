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
	private int id;
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
	public Computer(int id, String name, Timestamp introduced, Timestamp discontinued, int company_id) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company_id = company_id;
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
	
	public boolean isEmpty() {
		if (getName().equals("")
				&& getDiscontinued().equals(new Timestamp(0)) 
				&& getIntroduced().equals(new Timestamp(0)) 
				&& getCompany_id() == 0) {
			return true;
		}
		return false;
	}
}
