package com.excilys.service.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.core.dto.ComputerDTO;

public class ComputerMapper implements RowMapper<ComputerDTO> {

	@Override
	public ComputerDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		String introduced = DateMapper.sqlDateToString(rs.getDate("computer.introduced"));
		String discontinued = DateMapper.sqlDateToString(rs.getDate("computer.discontinued"));
		
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
	
	public static String dateEnToFr(String date) {
		return date.substring(8) + "/" + date.substring(5, 7) + "/" + date.substring(0, 4);
	}
}
