package com.excilys.validation;

import java.time.LocalDate;

import com.excilys.bean.ComputerDTO;
import com.excilys.bean.mapper.DateMapper;

public class ComputerValidation {
	/**
	 * 
	 * @param c1
	 * @return
	 */
	public static boolean date(ComputerDTO c1) {
		LocalDate introduced = DateMapper.convertStringToLocalDate(c1.getIntroduced());
		LocalDate discontinued = DateMapper.convertStringToLocalDate(c1.getDiscontinued());
			
		if (introduced != null && discontinued != null && introduced.isAfter(discontinued)) {
			return false;
		}		
		
		return true;
	}
}
