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
import com.excilys.db.ConnectionLocal;
import com.excilys.exception.DAOException;
import com.excilys.service.interfaces.DAOComputer;
import com.excilys.service.mapper.MapperDTO;
import com.excilys.service.mapper.MapperSQL;

/**
 * DB manipulation on Computer table
 * Singleton class.
 * 
 * @author pqwarlot
 *
 */
public class ComputerDAO implements DAOComputer {
	private static Logger logger = null;
	private static ComputerDAO instance = new ComputerDAO();
	private static ConnectionLocal localConnection = ConnectionLocal.getInstance();

	private ComputerDAO() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	protected static DAOComputer getInstance() {
		return instance;
	}

	@Override
	public List<ComputerDTO> findAll(PageRequest pageRequest) throws DAOException {
		Connection connection = localConnection.getConnection();
		PreparedStatement pstmt = null;
//		String sql = null;
		ResultSet rs = null;
		List<ComputerDTO> computers = new ArrayList<>();
		
		try {
			pstmt = new QueryCreator(pageRequest, connection, false).createPreparedQuery();
//			sql = new QueryCreator(pageRequest, connection, false).createQuery();
			
			logger.debug(pstmt.toString());
//			LOGGER.debug(sql);

			rs = pstmt.executeQuery();
//			stmt = connection.createStatement();
//			rs = stmt.executeQuery(sql);
			
			while (rs.next()) {
				computers.add(MapperDTO.createComputerDTO(rs));
			}
		} catch (SQLException e) {
			logger.error("computer find all SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.cleanup(pstmt, rs);
//			CoManager.cleanup(stmt, rs);
		}

		return computers;
	}
	
	@Override
	public void create(Computer computer) throws DAOException {
		Connection connection = localConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet generatedKeys = null;
		String sql = "INSERT INTO computer (name, discontinued, introduced, company_id) " + "VALUES ( ?, ?, ?, ?)";

		try {
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, computer.getName());
			pstmt.setDate(2, MapperSQL.javaLocalDateToSqlDate(computer.getDiscontinued()));
			pstmt.setDate(3, MapperSQL.javaLocalDateToSqlDate(computer.getIntroduced()));
			pstmt.setObject(4, computer.getCompanyId());

			pstmt.executeUpdate();

			generatedKeys = pstmt.getGeneratedKeys();
			if (generatedKeys.next()) {
				computer.setId(generatedKeys.getInt(1));
			}
		} catch (SQLException e) {
			logger.error("computer create SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.cleanup(pstmt, generatedKeys);
		}
	}

	@Override
	public void delete(Long id) throws DAOException {
		Connection connection = localConnection.getConnection();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM computer WHERE id=?";

		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, id);

			logger.debug(pstmt.toString());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("computer delete SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.cleanup(pstmt, null);
		}
	}

	@Override
	public void updateById(Computer computer) throws DAOException {
		Connection connection = localConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet generatedKeys = null;
		String sql = "UPDATE computer " + "SET name=?, discontinued=?, introduced=?, company_id=? " + "WHERE id=?";

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
			logger.error("computer update by id SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.cleanup(pstmt, generatedKeys);
		}
	}

	@Override
	public int count(PageRequest pageRequest) throws DAOException {
		Connection connection = localConnection.getConnection();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int number = 0;
		
		try {
			pstmt = new QueryCreator(pageRequest, connection, true).createPreparedQuery();

			logger.debug(pstmt.toString());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				number = rs.getInt("TOTAL");
			}	
		} catch (SQLException e) {
			logger.error("computer update by id SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.cleanup(pstmt, rs);		
		}
		
		return number;
	}
	
	@Override
	public int deleteByCompany(Long id) throws DAOException {
		Connection connection = localConnection.getConnection();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM computer WHERE company_id=?";
		int computerDeleted = 0;

		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, id);

			logger.debug(pstmt.toString());

			computerDeleted = pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("computer delete SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.cleanup(pstmt, null);
		}
		return computerDeleted;
	}
}
