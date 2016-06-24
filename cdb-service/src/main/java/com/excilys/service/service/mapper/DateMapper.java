package com.excilys.service.service.mapper;

import java.sql.Date;
import java.time.LocalDate;

public class DateMapper {
	/**
	 * Convert a SQL Date object to a Java LocalData object
	 * @param date SQL Date object to convert
	 * @return null if date null, Java LocalData otherwise
	 */
	public static LocalDate sqlDateToJavaLocalDate(Date date) {
		if (date == null) {
			return null;
		}
		return date.toLocalDate();
	}

	/**
	 * Convert a Java LocalData object to a SQL Date
	 * @param date Java LocalData object to convert
	 * @return null if date null, SQL Date otherwise
	 */
	public static Date javaLocalDateToSqlDate(LocalDate date) {
		if (date == null) {
			return null;
		}
		return Date.valueOf(date);
	}
	
	/**
	 * Convert a SQL Date to a String
	 * @param date SQL Date to convert
	 * @return null if date null, String otherwise
	 */
	public static String sqlDateToString(Date date) {
		if (date == null) {
			return null;
		}
		return date.toString();
	}
}