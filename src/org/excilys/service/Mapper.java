package org.excilys.service;

import java.sql.Date;
import java.time.LocalDate;

public class Mapper {
	static LocalDate sqlDateToJavaLocalDate(Date date) {
		if (date == null) {
			return null;
		}
		return date.toLocalDate();
	}
	
	static Date javaLocalDateToSqlTimeStamp(LocalDate date) {
		if (date == null) {
			return null;
		}
		return Date.valueOf(date);
	}
}
