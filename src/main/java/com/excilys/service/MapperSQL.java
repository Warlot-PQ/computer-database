package com.excilys.service;

import java.sql.Date;
import java.time.LocalDate;

public class MapperSQL {
	protected static LocalDate sqlDateToJavaLocalDate(Date date) {
		if (date == null) {
			return null;
		}
		return date.toLocalDate();
	}

	protected static Date javaLocalDateToSqlTimeStamp(LocalDate date) {
		if (date == null) {
			return null;
		}
		return Date.valueOf(date);
	}
}