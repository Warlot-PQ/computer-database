package com.excilys.core.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Computer entity DB representation
 * 
 * @author pqwarlot
 *
 */
@Entity
@Table(name="computer")
public class Computer {
	// Optional
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// Mandatory
	@Column(name="name")
	private String name;
	@Column(name="introduced")
	private LocalDate introduced;
	@Column(name="discontinued")
	private LocalDate discontinued;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="company_id")
	private Company company;
	
	/**
	 * Construct an empty computer object
	 */
	public Computer() {
		this(null, null, null, null, null);
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
	public Computer(String name, LocalDate introduced, LocalDate discontinued, Company company) {
		this(null, name, introduced, discontinued, company);
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
	public Computer(Long id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
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
		return this.introduced;
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
		return this.discontinued;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public long getCompanyId() {
		if (getCompany() == null) {
			setCompany(new Company());
		}
		return getCompany().getId();
	}
	

	public void setCompanyId(long id) {
		if (getCompany() == null) {
			setCompany(new Company());
		}
		getCompany().setId(id);
	}
	
	public String getCompanyName() {
		if (getCompany() == null) {
			setCompany(new Company());
		}
		return getCompany().getName();
	}

	public void setCompanyName(String name) {
		if (getCompany() == null) {
			setCompany(new Company());
		}
		getCompany().setName(name);
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

	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company=" + company + "]";
	}
	
	
}
