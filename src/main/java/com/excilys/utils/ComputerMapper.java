package com.excilys.utils;

import com.excilys.DTO.ComputerDTO;
import com.excilys.entity.Company;
import com.excilys.entity.Computer;

public class ComputerMapper {
	public static Computer toEntity(ComputerDTO computerDTO) {
		return new Computer(
				DateMapper.convertStringToLong(computerDTO.getId()),
				computerDTO.getName(),
				DateMapper.convertStringToLocalDate(computerDTO.getIntroduced()),
				DateMapper.convertStringToLocalDate(computerDTO.getDiscontinued()),
				new Company(DateMapper.convertStringToLong(computerDTO.getCompanyId()), computerDTO.getCompanyName()));
	}
}
