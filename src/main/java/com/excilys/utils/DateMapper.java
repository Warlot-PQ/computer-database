package com.excilys.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * static methods use to convert elements.
 * @author pqwarlot
 *
 */
public class DateMapper {
	/**
	 * Convert string object to integer object
	 * 
	 * @param msg
	 *            string to convert
	 * @return integer object if success, null otherwise
	 */
	public static Long convertStringToLong(String msg) {
		Long number = null;

		if (msg != null && msg.equals("") == false) {
			try {
				number = Long.valueOf(msg);
			} catch (IllegalArgumentException e) {
				System.out.println("Incorrect format!");
				number = null;
			}
		} else if (msg != null && msg.equals("") == true) {
			number = null;
		}

		return number;
	}

	/**
	 * Convert string object to timestamp object, return timestamp(0) if string
	 * empty
	 * 
	 * @param msg
	 *            string to convert
	 * @return timestamp object if success, null otherwise
	 */
	public static LocalDate convertStringToLocalDate(String msg) {
		LocalDate localDate = null;

		if (msg != null && msg.equals("") == false) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
				localDate = LocalDate.parse(msg, formatter);
			} catch (IllegalArgumentException | DateTimeParseException e) {
				System.out.println("Incorrect format!");
				localDate = null;
			}
		}

		return localDate;
	}
	
	public static String convertLocalDateToString(LocalDate locDate) {
		if (locDate == null) {
			return "";
		}
		return dateEnToFr(locDate.toString());	
	}
	
	public static String dateEnToFr(String date) {
		return date.substring(8) + "/" + date.substring(5, 7) + "/" + date.substring(0, 4);
	}
}
