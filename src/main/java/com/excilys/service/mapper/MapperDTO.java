package com.excilys.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.bean.CompanyDTO;
import com.excilys.bean.ComputerDTO;

/**
 * Create a ComputerDTO from a ResultSet or convert an En date to a Fr date
 * @author pqwarlot
 *
 */
public class MapperDTO {
	public static ComputerDTO createComputerDTO(ResultSet rs) throws SQLException {
		String introduced = MapperSQL.sqlDateToString(rs.getDate("computer.introduced"));
		String discontinued = MapperSQL.sqlDateToString(rs.getDate("computer.discontinued"));
		
		// Convert date En (DB) to Fr
		if (introduced != null) {
			introduced = dateEnToFr(introduced);
		}
		if (discontinued != null) {
			discontinued = dateEnToFr(discontinued);
		}
		
		return new ComputerDTO(
				rs.getString("computer.id"), 
				rs.getString("computer.name"),
				introduced,
				discontinued, 
				rs.getString("computer.company_id"),
				rs.getString("company_name"));
	}
	
	public static CompanyDTO createCompanyDTO(ResultSet rs) throws SQLException {
		return new CompanyDTO(rs.getString("id"), rs.getString("name"));
	}
	
	public static String dateEnToFr(String date) {
		return date.substring(8) + "/" + date.substring(5, 7) + "/" + date.substring(0, 4);
	}
}
