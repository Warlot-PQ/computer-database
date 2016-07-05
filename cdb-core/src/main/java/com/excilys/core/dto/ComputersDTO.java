package com.excilys.core.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "computers")
@XmlSeeAlso({ComputerDTO.class})
@XmlAccessorType (XmlAccessType.FIELD)
public class ComputersDTO implements Serializable {
	@XmlElement(name = "computer")
	List<ComputerDTO> computers = null;

	public List<ComputerDTO> getComputers() {
		return computers;
	}

	public void setComputers(List<ComputerDTO> computers) {
		this.computers = computers;
	}
}
