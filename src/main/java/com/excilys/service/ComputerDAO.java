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

import com.excilys.beans.Computer;
import com.excilys.beans.ComputerDTO;
import com.excilys.db.CoManager;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;

/**
 * DB manipulation on Computer table
 * 
 * @author pqwarlot
 *
 */
public class ComputerDAO implements DAO<Computer, ComputerDTO> {
	private static ComputerDAO instance;
	private static Logger logger = null;

	private ComputerDAO() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	protected static ComputerDAO getInstance() {
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
	public List<ComputerDTO> findAll() throws DAOException, ConnectionException, DriverException {
		Connection connection = null;
		Statement stmt = null;
		String sql = "SELECT computer.id,computer.name,computer.introduced,"
				+ "computer.discontinued,computer.company_id,company.name AS company_name " + "FROM computer "
				+ "LEFT OUTER JOIN company " + "ON computer.company_id=company.id";
		ResultSet rs = null;
		List<ComputerDTO> computers = new ArrayList<>();

		connection = CoManager.getInstance().getConnection();

		try {
			stmt = connection.createStatement();

			logger.debug(sql);

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				computers.add(new ComputerDTO(rs.getLong("id"), rs.getString("name"),
						MapperSQL.sqlDateToJavaLocalDate(rs.getDate("introduced")),
						MapperSQL.sqlDateToJavaLocalDate(rs.getDate("discontinued")), rs.getLong("company_id"),
						rs.getString("company_name")));
			}
		} catch (SQLException e) {
			logger.error("computer find all SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.getInstance().cleanup(connection, stmt, rs);
		}

		return computers;
	}

	@Override
	public List<ComputerDTO> findFromTo(int offset, int limit)
			throws DAOException, ConnectionException, DriverException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT computer.id,computer.name,computer.introduced,"
				+ "computer.discontinued,computer.company_id,company.name AS company_name " + "FROM computer "
				+ "LEFT OUTER JOIN company " + "ON computer.company_id=company.id " + "LIMIT ?, ?";
		ResultSet rs = null;
		List<ComputerDTO> computers = new ArrayList<>();

		connection = CoManager.getInstance().getConnection();

		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, limit);

			logger.debug(pstmt.toString());

			rs = pstmt.executeQuery();
			while (rs.next()) {
				computers.add(new ComputerDTO(rs.getLong("id"), rs.getString("name"),
						MapperSQL.sqlDateToJavaLocalDate(rs.getDate("introduced")),
						MapperSQL.sqlDateToJavaLocalDate(rs.getDate("discontinued")), rs.getLong("company_id"),
						rs.getString("company_name")));
			}
		} catch (SQLException e) {
			logger.error("computer find by id SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.getInstance().cleanup(connection, pstmt, rs);
		}

		return computers;
	}

	@Override
	public ComputerDTO findById(Long id) throws DAOException, ConnectionException, DriverException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT computer.id,computer.name,computer.introduced,"
				+ "computer.discontinued,computer.company_id,company.name AS company_name" + " FROM computer "
				+ "LEFT OUTER JOIN company " + "ON computer.company_id=company.id" + " WHERE computer.id=?";
		ResultSet rs = null;
		ComputerDTO computer = null;

		connection = CoManager.getInstance().getConnection();

		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, id);

			logger.debug(pstmt.toString());

			rs = pstmt.executeQuery();

			if (rs.next()) {
				computer = new ComputerDTO(rs.getLong("id"), rs.getString("name"),
						MapperSQL.sqlDateToJavaLocalDate(rs.getDate("introduced")),
						MapperSQL.sqlDateToJavaLocalDate(rs.getDate("discontinued")), rs.getLong("company_id"),
						rs.getString("company_name"));
			}
		} catch (SQLException e) {
			logger.error("computer find by id SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.getInstance().cleanup(connection, pstmt, rs);
		}

		return computer;
	}

	@Override
	public void create(Computer computer) throws DAOException, ConnectionException, DriverException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet generatedKeys = null;
		String sql = "INSERT INTO computer (name, discontinued, introduced, company_id) " + "VALUES ( ?, ?, ?, ?)";

		connection = CoManager.getInstance().getConnection();

		try {
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, computer.getName());
			pstmt.setDate(2, MapperSQL.javaLocalDateToSqlTimeStamp(computer.getDiscontinued()));
			pstmt.setDate(3, MapperSQL.javaLocalDateToSqlTimeStamp(computer.getIntroduced()));
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
			CoManager.getInstance().cleanup(connection, pstmt, generatedKeys);
		}
	}

	@Override
	public void delete(Long id) throws DAOException, ConnectionException, DriverException {
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
			CoManager.getInstance().cleanup(connection, pstmt, null);
		}
	}

	@Override
	public void updateById(Computer computer) throws DAOException, ConnectionException, DriverException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet generatedKeys = null;
		String sql = "UPDATE computer " + "SET name=?, discontinued=?, introduced=?, company_id=? " + "WHERE id=?";

		connection = CoManager.getInstance().getConnection();

		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, computer.getName());
			pstmt.setDate(2, MapperSQL.javaLocalDateToSqlTimeStamp(computer.getDiscontinued()));
			pstmt.setDate(3, MapperSQL.javaLocalDateToSqlTimeStamp(computer.getIntroduced()));
			pstmt.setObject(4, computer.getCompanyId());
			pstmt.setLong(5, computer.getId());

			logger.debug(pstmt.toString());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("computer update error!");
			logger.error("computer update by id SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.getInstance().cleanup(connection, pstmt, generatedKeys);
		}
	}

	@Override
	public int getRowNumber() throws DAOException, ConnectionException, DriverException {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(id) AS TOTAL FROM computer";
		int number = 0;

		connection = CoManager.getInstance().getConnection();

		try {
			stmt = connection.createStatement();

			logger.debug(sql);

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				number = rs.getInt("TOTAL");
			}

		} catch (SQLException e) {
			System.out.println("computer update error!");
			logger.error("computer update by id SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.getInstance().cleanup(connection, stmt, rs);
		}

		return number;
	}
}
