package com.excilys.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.bean.Computer;
import com.excilys.bean.ComputerDTO;
import com.excilys.db.CoManager;
import com.excilys.exception.DAOException;
import com.excilys.service.interfaces.DAOComputer;
import com.excilys.service.mapper.MapperDTO;
import com.excilys.service.mapper.MapperSQL;

/**
 * DB manipulation on Computer table
 * 
 * @author pqwarlot
 *
 */
public class ComputerDAO implements DAOComputer {
	private static ComputerDAO instance;
	private static Logger logger = null;

	private ComputerDAO() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	protected static DAOComputer getInstance() {
		if (instance == null) {
			synchronized (ComputerDAO.class) {
				if (instance == null) {
					instance = new ComputerDAO();
				}
			}
		}
		return instance;
	}

	@Override
	public List<ComputerDTO> findAll(PageRequest pageRequest, Connection connection) {
		boolean internalConnection = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = null;
		List<ComputerDTO> computers = new ArrayList<>();
		
		if (connection == null) {
			connection = CoManager.getInstance().getConnection();
			internalConnection = true;
		}

		try {
			pstmt = new QueryCreator(pageRequest, connection, false).createPreparedQuery();

			logger.debug(pstmt.toString());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				computers.add(MapperDTO.createComputerDTO(rs));
			}
		} catch (SQLException e) {
			logger.error("computer find all SQL error!", e);

			throw new DAOException(e);
		} finally {
			if (internalConnection) {
				CoManager.cleanup(connection, pstmt, rs);
			} else {
				CoManager.cleanup(null, pstmt, rs);				
			}
		}

		return computers;
	}
	
	@Override
	public void create(Computer computer) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet generatedKeys = null;
		String sql = "INSERT INTO computer (name, discontinued, introduced, company_id) " + "VALUES ( ?, ?, ?, ?)";

		connection = CoManager.getInstance().getConnection();

		try {
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, computer.getName());
			pstmt.setDate(2, MapperSQL.javaLocalDateToSqlDate(computer.getDiscontinued()));
			pstmt.setDate(3, MapperSQL.javaLocalDateToSqlDate(computer.getIntroduced()));
			pstmt.setObject(4, computer.getCompanyId());

			System.out.println(pstmt.toString());

			pstmt.executeUpdate();

			generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				computer.setId(generatedKeys.getInt(1));
			}
		} catch (SQLException e) {
			logger.error("computer create SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.cleanup(connection, pstmt, generatedKeys);
		}
	}

	@Override
	public void delete(Long id) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM computer WHERE id=?";

		connection = CoManager.getInstance().getConnection();

		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, id);

			logger.debug(pstmt.toString());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("computer delete SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.cleanup(connection, pstmt, null);
		}
	}

	@Override
	public void updateById(Computer computer) {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet generatedKeys = null;
		String sql = "UPDATE computer " + "SET name=?, discontinued=?, introduced=?, company_id=? " + "WHERE id=?";

		connection = CoManager.getInstance().getConnection();

		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, computer.getName());
			pstmt.setDate(2, MapperSQL.javaLocalDateToSqlDate(computer.getDiscontinued()));
			pstmt.setDate(3, MapperSQL.javaLocalDateToSqlDate(computer.getIntroduced()));
			pstmt.setObject(4, computer.getCompanyId());
			pstmt.setLong(5, computer.getId());

			logger.debug(pstmt.toString());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("computer update error!");
			logger.error("computer update by id SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.cleanup(connection, pstmt, generatedKeys);
		}
	}

	@Override
	public int getRowNumber(PageRequest pageRequest, Connection connection) {
		boolean internalConnection = false;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int number = 0;
		

		if (connection == null) {
			connection = CoManager.getInstance().getConnection();
			internalConnection = true;
		}
		
		try {
			pstmt = new QueryCreator(pageRequest, connection, true).createPreparedQuery();

			logger.debug(pstmt.toString());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				number = rs.getInt("TOTAL");
			}

		} catch (SQLException e) {
			System.out.println("computer update error!");
			logger.error("computer update by id SQL error!", e);

			throw new DAOException(e);
		} finally {
			if (internalConnection) {
				CoManager.cleanup(connection, pstmt, rs);
			} else {
				CoManager.cleanup(null, pstmt, rs);				
			}
		}

		return number;
	}
	
	@Override
	public void deleteByCompany(Long id, Connection connection) {
		boolean internalConnection = false;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM computer WHERE company_id=?";

		if (connection == null) {
			connection = CoManager.getInstance().getConnection();
			internalConnection = true;
		}
		
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, id);

			logger.debug(pstmt.toString());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("computer delete SQL error!", e);

			throw new DAOException(e);
		} finally {
			if (internalConnection) {
				CoManager.cleanup(connection, pstmt, null);
			} else {
				CoManager.cleanup(null, pstmt, null);				
			}
		}
	}
}
