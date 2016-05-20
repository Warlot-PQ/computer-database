package org.excilys.cli;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.swing.text.DateFormatter;

public interface Command {
	public void execute();

	/**
	 * Convert string object to integer object
	 * @param msg string to convert
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
	 * Convert string object to timestamp object, return timestamp(0) if string empty
	 * @param msg string to convert
	 * @return timestamp object if success, null otherwise
	 */
	public static LocalDate convertStringToLocalDateTime(String msg) {
		LocalDate localDate = null;
		
		if (msg != null && msg.equals("") == false) {
			try {
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				localDate = LocalDate.parse(msg, formatter);
			} catch (IllegalArgumentException e) {
				System.out.println("Incorrect format!");
				localDate = null;
			}
		}
		
		return localDate;
	}
}
