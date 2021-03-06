package com.excilys.core.dto;

import java.io.Serializable;

/**
 * Representation of a Company object which is given to the front.
 * 
 * @author pqwarlot
 *
 */
public class CompanyDTO implements Serializable {
	private String id;
	private String name;

	public CompanyDTO() {
	}
	
	public CompanyDTO(Long id, String name) {
		this((id == null) ? "" : id.toString(),
			name);
	}
	
	public CompanyDTO(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public String getIdStr() {
		return (this.id == null) ? "" : String.valueOf(id);
	}

	public String getName() {
		return name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CompanyDTO other = (CompanyDTO) obj;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "CompanyDTO [id=" + id + ", name=" + name + "]";
	}
}
