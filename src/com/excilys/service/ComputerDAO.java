package com.excilys.service;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.beans.Computer;
import com.excilys.db.CoManagerFactory;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;

/**
 * DB manipulation on Computer table
 * 
 * @author pqwarlot
 *
 */
public class ComputerDAO implements DAO<Computer> {
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

	public List<Computer> findAll() throws DAOException, ConnectionException, DriverException {
		Connection connection = null;
		Statement stmt = null;
		String sql = "SELECT computer.id,computer.name,computer.introduced,"
				+ "computer.discontinued,computer.company_id,company.name AS company_name " + "FROM computer "
				+ "LEFT OUTER JOIN company " + "ON computer.company_id=company.id";
		ResultSet rs = null;
		List<Computer> computers = new ArrayList<>();

		connection = CoManagerFactory.getCoManager().getConnection();

		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				computers.add(new Computer(rs.getLong("id"), rs.getString("name"),
						Mapper.sqlDateToJavaLocalDate(rs.getDate("introduced")),
						Mapper.sqlDateToJavaLocalDate(rs.getDate("discontinued")), rs.getLong("company_id"),
						rs.getString("company_name")));
			}
		} catch (SQLException e) {
			logger.error("computer find all SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManagerFactory.getCoManager().cleanup(connection, stmt, rs);
		}

		return computers;
	}

	public Computer findById(Long id) throws DAOException, ConnectionException, DriverException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT computer.id,computer.name,computer.introduced,"
				+ "computer.discontinued,computer.company_id,company.name AS company_name" + " FROM computer "
				+ "LEFT OUTER JOIN company " + "ON computer.company_id=company.id" + " WHERE computer.id=?";
		ResultSet rs = null;
		Computer computer = null;

		connection = CoManagerFactory.getCoManager().getConnection();

		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				computer = new Computer(rs.getLong("id"), rs.getString("name"),
						Mapper.sqlDateToJavaLocalDate(rs.getDate("introduced")),
						Mapper.sqlDateToJavaLocalDate(rs.getDate("discontinued")), rs.getLong("company_id"),
						rs.getString("company_name"));
			}
		} catch (SQLException e) {
			logger.error("computer find by id SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManagerFactory.getCoManager().cleanup(connection, pstmt, rs);
		}

		return computer;
	}

	public void create(Computer computer) throws DAOException, ConnectionException, DriverException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet generatedKeys = null;
		String sql = "INSERT INTO computer (name, discontinued, introduced, company_id) VALUES ( ?, ?, ?, ?)";

		connection = CoManagerFactory.getCoManager().getConnection();

		try {
			pstmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, computer.getName());
			pstmt.setDate(2, Mapper.javaLocalDateToSqlTimeStamp(computer.getDiscontinued()));
			pstmt.setDate(3, Mapper.javaLocalDateToSqlTimeStamp(computer.getIntroduced()));
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
			CoManagerFactory.getCoManager().cleanup(connection, pstmt, generatedKeys);
		}
	}

	public void delete(Long id) throws DAOException, ConnectionException, DriverException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM computer WHERE id=?";

		connection = CoManagerFactory.getCoManager().getConnection();

		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, id);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("computer delete SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManagerFactory.getCoManager().cleanup(connection, pstmt, null);
		}
	}

	public void updateById(Computer computer) throws DAOException, ConnectionException, DriverException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet generatedKeys = null;
		String sql = "UPDATE computer SET name=?, discontinued=?, introduced=?, company_id=? WHERE id=?";

		connection = CoManagerFactory.getCoManager().getConnection();

		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, computer.getName());
			pstmt.setDate(2, Mapper.javaLocalDateToSqlTimeStamp(computer.getDiscontinued()));
			pstmt.setDate(3, Mapper.javaLocalDateToSqlTimeStamp(computer.getIntroduced()));
			pstmt.setObject(4, computer.getCompanyId());
			pstmt.setLong(5, computer.getId());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("computer update error!");
			logger.error("computer update by id SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManagerFactory.getCoManager().cleanup(connection, pstmt, generatedKeys);
		}
	}
}
