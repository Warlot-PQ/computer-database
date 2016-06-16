package com.excilys.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.excilys.bean.Computer;
import com.excilys.bean.ComputerDTO;
import com.excilys.service.interfaces.ComputerDAO;
import com.excilys.service.mapper.ComputerMapper;
import com.excilys.service.mapper.CountMapper;
import com.excilys.service.mapper.DateMapper;

/**
 * DB manipulation on Computer table
 * Singleton class.
 * 
 * @author pqwarlot
 *
 */
@Repository("computerDAO")
@Scope("singleton")
public class ComputerDAOImpl implements ComputerDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public ComputerDAOImpl() {
	}

	@Override
	public List<ComputerDTO> findAll(PageRequest pageRequest) {
		String SQL_SELECT = new QueryCreator(pageRequest, false).createQuery();
		return jdbcTemplate.query(SQL_SELECT, new ComputerMapper());
	}
	
	@Override
	public void create(Computer computer) {		
		final String SQL_INSERT= "INSERT INTO computer (name, discontinued, introduced, company_id) VALUES ( ?, ?, ?, ?)"; 
			
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(
		    new PreparedStatementCreator() {
		        @Override
				public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
		            PreparedStatement ps = connection.prepareStatement(SQL_INSERT, new String[] {"id"});
		            ps.setString(1, computer.getName());
		            ps.setDate(2, DateMapper.javaLocalDateToSqlDate(computer.getDiscontinued()));
		            ps.setDate(3, DateMapper.javaLocalDateToSqlDate(computer.getIntroduced()));
		            ps.setObject(4, computer.getCompanyId());
		            return ps;
		        }
		    },
		    keyHolder);
		computer.setId(keyHolder.getKey().intValue());
	}

	@Override
	public void delete(Long id) {
		final String SQL_DELETE = "DELETE FROM computer WHERE id=?";
		jdbcTemplate.update(SQL_DELETE, id);
	}

	@Override
	public void updateById(Computer computer) {
		final String SQL_UPDATE = "UPDATE computer SET name=?, discontinued=?, introduced=?, company_id=? WHERE id=?";
		
		jdbcTemplate.update(SQL_UPDATE, 
				computer.getName(),
				DateMapper.javaLocalDateToSqlDate(computer.getDiscontinued()),
				DateMapper.javaLocalDateToSqlDate(computer.getIntroduced()),
				computer.getCompanyId(),
				computer.getId());
	}

	@Override
	public int count(PageRequest pageRequest) {
		final String SQL_COUNT = new QueryCreator(pageRequest, true).createQuery();
		return jdbcTemplate.queryForObject(SQL_COUNT, new CountMapper());
	}
	
	@Override
	public int deleteByCompany(Long id) {
		final String SQL_DELETE = "DELETE FROM computer WHERE company_id=?";
		return jdbcTemplate.update(SQL_DELETE, id);
	}
}
