package com.excilys.service.service.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.core.dto.CompanyDTO;

public class CompanyMapper implements RowMapper<CompanyDTO> {

	@Override
	public CompanyDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new CompanyDTO(rs.getString("id"), rs.getString("name"));
	}
}
