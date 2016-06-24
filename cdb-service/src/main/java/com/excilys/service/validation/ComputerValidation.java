package com.excilys.service.validation;

import java.time.LocalDate;

import com.excilys.core.entity.Computer;

public class ComputerValidation {
	/**
	 * 
	 * @param c1
	 * @return
	 */
	public static boolean date(Computer c1) {
		LocalDate introduced = c1.getIntroduced();
		LocalDate discontinued = c1.getDiscontinued();
			
		if (introduced != null && discontinued != null && introduced.isAfter(discontinued)) {
			return false;
		}		
		
		return true;
	}
}
