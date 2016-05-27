package com.excilys.validation;

import com.excilys.beans.Computer;

public class ComputerValidator {
	private Computer computer;

	public ComputerValidator(Computer computer) {
		this.computer = computer;
	}

	public Validation check() {
		Validation validate = new Validation();

		if (computer.getName() == null || (computer.getName() != null && computer.getName().trim().isEmpty())) {
			// Check name field content
			validate.setError(true);
			validate.setMsg("name cannot be empty");
			return validate;
		} else if (computer.getName() != null && computer.getName().length() > 255) {
			// Check computer name field size
			validate.setError(true);
			validate.setMsg("name must be 255 character max");
			return validate;
		} else if (computer.getIntroduced() != null && computer.getIntroduced().toString().length() > 10) {
			// Check computer introduced field size
			validate.setError(true);
			validate.setMsg("introduced must be 10 character");
			return validate;
		} else if (computer.getDiscontinued() != null && computer.getDiscontinued().toString().length() > 10) {
			// Check computer discontinued field size
			validate.setError(true);
			validate.setMsg("discontinued must be 10 character");
			return validate;
		} else if (computer.getCompanyId() != null && computer.getCompanyId().toString().length() > 20) {
			// Check computer company field size
			validate.setError(true);
			validate.setMsg("company must be 20 digit max");
			return validate;
		} else if (computer.getIntroduced() != null && computer.getIntroduced().toString().matches(
				"^(?:(?:31(-)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(-)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(-)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(-)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")) {
			// Check introduced date format using regEx
			validate.setError(true);
			validate.setMsg("introduced date must be dd-mm-yyyy");
			return validate;
		} else if (computer.getDiscontinued() != null && computer.getDiscontinued().toString().matches(
				"^(?:(?:31(-)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(-)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(-)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(-)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$")) {
			// Check discontinued date format using regEx
			validate.setError(true);
			validate.setMsg("discontinued date must be dd-mm-yyyy");
			return validate;
		} else if (computer.getIntroduced() != null && computer.getDiscontinued() != null
				&& computer.getIntroduced().isAfter(computer.getDiscontinued())) {
			validate.setError(true);
			validate.setMsg("introduced date must be less than discontinued date");
			return validate;
		}

		validate.setMsg("computer added to the DB");
		return validate;
	}
}
