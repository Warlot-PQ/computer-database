package com.excilys.binding;

import com.excilys.core.date.DateMapper;
import com.excilys.core.dto.ComputerDTO;
import com.excilys.core.entity.Company;
import com.excilys.core.entity.Computer;

/**
 * Mapper around Computer and ComputerDTO object
 * @author pqwarlot
 *
 */
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
