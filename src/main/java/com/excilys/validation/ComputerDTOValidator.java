package com.excilys.validation;

import java.time.LocalDate;

import com.excilys.bean.ComputerDTO;

/**
 * Validator class use to validate all fields of ComputerDTO object.
 * @author pqwarlot
 *
 */
public class ComputerDTOValidator {
	private final static int MAX_ID_SIZE=20, MAX_NAME_SIZE = 255, MAX_INTRODUCED_SIZE = 10, MAX_DISCONTINUED_SIZE = 10, MAX_COMPANY_SIZE = 20;	
	private final static String DATE_PATTERN_MATCHING = "^(?:(?:31(\\/)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$";
	
	private ComputerDTO computerDTOToValidate;
	private Validation validate = new Validation();

	public ComputerDTOValidator(ComputerDTO computerDTO) {
		this.computerDTOToValidate = computerDTO;
	}

	/**
	 * Validate a ComputerDTO object. Use getValidate() method to get the checking result.
	 * @return ComputerDTOValidator object.
	 */
	public ComputerDTOValidator checkAll() {
		checkId();
		checkName();
		checkCompanyId();
		checkIntroducedDate();
		checkDiscontinuedDate();
		checkDatesOrder();
		return this;
	}
	/**
	 * Validate a ComputerDTO object. Use getValidate() method to get the checking result.
	 * @return ComputerDTOValidator object.
	 */
	public ComputerDTOValidator check() {
		checkName();
		checkCompanyId();
		checkIntroducedDate();
		checkDiscontinuedDate();
		checkDatesOrder();
		return this;
	}
	
	private void checkId() {
		if (computerDTOToValidate.getId() != null && computerDTOToValidate.getId().length() > MAX_ID_SIZE) {
			// Check computer company field size
			validate.addMessages("Error.computer.id.size");
		} 
	}
	
	private void checkName() {
		if (computerDTOToValidate.getName() == null || (computerDTOToValidate.getName() != null && computerDTOToValidate.getName().trim().isEmpty())) {
			// Check name field content
			validate.addMessages("Error.computer.name.empty");
		}
		if (computerDTOToValidate.getName() != null && computerDTOToValidate.getName().length() > MAX_NAME_SIZE) {
			// Check computer name field size
			validate.addMessages("Error.computer.name.size");
		} 
	}
	
	private void checkIntroducedDate() {
		if (computerDTOToValidate.getIntroduced() != null && computerDTOToValidate.getIntroduced().length() > MAX_INTRODUCED_SIZE) {
			// Check computer introduced field size
			validate.addMessages("Error.computer.introduced.size");
		}
		if (computerDTOToValidate.getIntroduced() != null && computerDTOToValidate.getIntroduced().isEmpty() == false && computerDTOToValidate.getIntroduced().matches(DATE_PATTERN_MATCHING) == false) {
			// Check introduced date format using regEx
			validate.addMessages("Error.computer.introduced.format");
		}
	}
	
	private void checkDiscontinuedDate() {
		if (computerDTOToValidate.getDiscontinued() != null && computerDTOToValidate.getDiscontinued().length() > MAX_DISCONTINUED_SIZE) {
			// Check computer discontinued field size
			validate.addMessages("Error.computer.discontinued.size");
		}
		if (computerDTOToValidate.getDiscontinued() != null && computerDTOToValidate.getDiscontinued().isEmpty() == false && computerDTOToValidate.getDiscontinued().matches(DATE_PATTERN_MATCHING) == false) {
			// Check discontinued date format using regEx
			validate.addMessages("Error.computer.discontinued.format");
		} 
	}
	
	private void checkDatesOrder() { 
		LocalDate introduced = MapperUtils.convertStringToLocalDate(computerDTOToValidate.getIntroduced());
		LocalDate discontinued = MapperUtils.convertStringToLocalDate(computerDTOToValidate.getDiscontinued());
				
		if (introduced != null && discontinued != null && introduced.isAfter(discontinued)) {
			validate.addMessages("Error.computer.discontinued.lessThan.introduced");
		}
	}
	
	private void checkCompanyId() {
		if (computerDTOToValidate.getCompanyId() != null && computerDTOToValidate.getCompanyId().length() > MAX_COMPANY_SIZE) {
			// Check computer company field size
			validate.addMessages("Error.computer.company.id.size");
		} 
	}
	
	public ComputerDTO getComputerDTOToValidate() {
		return computerDTOToValidate;
	}

	public void setComputerDTOToValidate(ComputerDTO computerDTOToValidate) {
		this.computerDTOToValidate = computerDTOToValidate;
	}

	public Validation getValidation() {
		return validate;
	}

	public void setValidate(Validation validate) {
		this.validate = validate;
	}

	public static String getDATE_PATTERN_MATCHING() {
		return DATE_PATTERN_MATCHING;
	}	
}
