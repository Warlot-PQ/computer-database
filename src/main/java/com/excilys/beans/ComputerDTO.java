package com.excilys.beans;

import java.time.LocalDate;

public class ComputerDTO {
	private Long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Long companyId;
	private String companyName;
	
	public ComputerDTO(Long id, String name, LocalDate introduced, LocalDate discontinued, Long companyId, String companyName) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
		this.companyName = companyName;
	}
	
	public Long getId() {
		return id;
	}

	public String getIdStr() {
		return (id == null) ? "" : String.valueOf(id);
	}

	public String getName() {
		return name;
	}

	public LocalDate getIntroduced() {
		return introduced;
	}

	public String getIntroducedStr() {
		return (introduced == null) ? "" : String.valueOf(introduced);
	}

	public LocalDate getDiscontinued() {
		return discontinued;
	}

	public String getDiscontinuedStr() {
		return (discontinued == null) ? "" : String.valueOf(discontinued);
	}

	public Long getCompanyId() {
		return companyId;
	}

	public String getCompanyIdStr() {
		return (companyId == null) ? "" : String.valueOf(companyId);
	}

	public String getCompanyName() {
		return companyName;
	}

	public Computer toComputerBean() {
		return new Computer(
				getId(),
				getName(),
				getIntroduced(),
				getDiscontinued(),
				getCompanyId());
	}

	@Override
	public String toString() {
		return "ComputerDTO [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued="
				+ discontinued + ", companyId=" + companyId + ", companyName=" + companyName + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((companyId == null) ? 0 : companyId.hashCode());
		result = prime * result + ((companyName == null) ? 0 : companyName.hashCode());
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
		ComputerDTO other = (ComputerDTO) obj;
		if (companyId == null) {
			if (other.companyId != null)
				return false;
		} else if (!companyId.equals(other.companyId))
			return false;
		if (companyName == null) {
			if (other.companyName != null)
				return false;
		} else if (!companyName.equals(other.companyName))
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
