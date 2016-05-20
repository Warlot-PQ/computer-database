package com.excilys.beans;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Computer entity DB representation
 * 
 * @author pqwarlot
 *
 */
public class Computer {
	// Optional
	private Long id;
	// Mandatory
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;

	/**
	 * Construct an empty computer object
	 */
	public Computer() {
		this(null, null, null, null, null, null);
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
	public Computer(String name, LocalDate introduced, LocalDate discontinued, Long company_id, String company_name) {
		this(null, name, introduced, discontinued, company_id, company_name);
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
	public Computer(Long id, String name, LocalDate introduced, LocalDate discontinued, Long company_id, String company_name) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = new Company(company_id, company_name);
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
	public LocalDate getIntroduced() {
		return introduced;
	}

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	/**
	 * Get discontinued date, 1970-01-01 if null in DB
	 * 
	 * @return date as timestamp format
	 */
	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public Long getCompanyId() {
		return company.getId();
	}

	public void setCompanyId(Long companyId) {
		this.company.setId(companyId);
	}

	public String getCompanyName() {
		return company.getName();
	}

	public void setCompanyName(String companyName) {
		this.company.setName(companyName);
	}

	public String toString() {
		String s = String.format("id: %d name: %s introduced: %s discontinued: %s company id: %d company name: %s", 
				getId(),
				getName(), 
				(getIntroduced() == null) ? getIntroduced() : getIntroduced().toString(), 
				(getDiscontinued() == null) ? getDiscontinued() : getDiscontinued().toString(),
				getCompanyId(),
				getCompanyName()
				);

		return s;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		if (company == null) {
			if (other.company != null)
				return false;
		} else if (!company.equals(other.company))
			return false;
		if (discontinued == null) {
			if (other.discontinued != null)
				return false;
		} else if (!discontinued.equals(other.discontinued))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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
