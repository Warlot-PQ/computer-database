package com.excilys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.bean.Company;
import com.excilys.bean.CompanyDTO;
import com.excilys.service.interfaces.CompanyDAO;
import com.excilys.service.mapper.CountMapper;

/**
 * DB manipulation on Company table.
 * Singleton class.
 * 
 * @author pqwarlot
 *
 */
@Repository("companyDAO")
@Scope("singleton")
public class CompanyDAOImpl implements CompanyDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public CompanyDAOImpl() {
	}

	@Override
	public List<CompanyDTO> findAll() {
		final String SQL_SELECT = "SELECT id,name FROM company";
		return jdbcTemplate.query(SQL_SELECT, new BeanPropertyRowMapper<CompanyDTO>(CompanyDTO.class));
	}

	@Override
	public void create(Company obj) {
		throw new UnsupportedClassVersionError("Not implemeted yet");
	}

	@Override
	public void updateById(Company obj) {
		throw new UnsupportedClassVersionError("Not implemeted yet");
	}
	
	@Override
	public CompanyDTO findById(Long id) {
		final String SQL_SELECT = "SELECT id,name FROM company WHERE id=?";
		List<CompanyDTO> companies = jdbcTemplate.query(SQL_SELECT, new Object[]{id}, new BeanPropertyRowMapper<CompanyDTO>(CompanyDTO.class));
		return (companies != null && companies.size() > 0) ? companies.get(0) : null;
	}	

	@Override
	public void delete(Long id) {
		final String SQL_DELETE = "DELETE FROM company WHERE id=?";
		jdbcTemplate.update(SQL_DELETE, id);
	}

	@Override
	public int getRowNumber() {
		final String SQL_COUNT = "SELECT COUNT(id) AS TOTAL FROM company";
		return jdbcTemplate.queryForObject(SQL_COUNT, new CountMapper());
	}
}
