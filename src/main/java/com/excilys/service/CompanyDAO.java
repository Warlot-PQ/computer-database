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

import com.excilys.beans.Company;
import com.excilys.beans.CompanyDTO;
import com.excilys.db.CoManager;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;

/**
 * DB manipulation on Company table
 * 
 * @author pqwarlot
 *
 */
public class CompanyDAO implements DAOCompany {
	private static CompanyDAO instance;
	private static Logger logger = null;

	private CompanyDAO() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	protected static CompanyDAO getInstance() {
		if (instance == null) {
			synchronized (CompanyDAO.class) {
				if (instance == null) {
					instance = new CompanyDAO();
				}
			}
		}
		return instance;
	}

	@Override
	public List<CompanyDTO> findAll() throws DriverException, ConnectionException, DAOException {
		Connection connection = null;
		Statement stmt = null;
		String sql = "SELECT id,name " + "FROM company";
		ResultSet rs = null;
		List<CompanyDTO> companies = new ArrayList<>();

		connection = CoManager.getInstance().getConnection();

		try {
			stmt = connection.createStatement();

			logger.debug(sql);

			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				companies.add(new CompanyDTO(rs.getLong("id"), rs.getString("name")));
			}
		} catch (SQLException e) {
			logger.error("campany fin all SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.getInstance().cleanup(connection, stmt, rs);
		}

		return companies;
	}

	@Override
	public List<CompanyDTO> findFromTo(int offset, int limit)
			throws DAOException, ConnectionException, DriverException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT id,name " + "FROM company " + "LIMIT ?, ?";
		ResultSet rs = null;
		List<CompanyDTO> companies = new ArrayList<>();

		connection = CoManager.getInstance().getConnection();

		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, offset);
			pstmt.setInt(2, limit);
			System.out.println(pstmt.toString());

			logger.debug(pstmt.toString());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				companies.add(new CompanyDTO(rs.getLong("id"), rs.getString("name")));
			}
		} catch (SQLException e) {
			logger.error("company find by id SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.getInstance().cleanup(connection, pstmt, rs);
		}

		return companies;
	}

	@Override
	public CompanyDTO findById(Long id) throws ConnectionException, DriverException, DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT id,name " + "FROM company " + "WHERE id=?";
		ResultSet rs = null;
		CompanyDTO company = null;

		connection = CoManager.getInstance().getConnection();

		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, id);

			logger.debug(pstmt.toString());

			rs = pstmt.executeQuery();

			while (rs.next()) {
				company = new CompanyDTO(rs.getLong("id"), rs.getString("name"));
			}
		} catch (SQLException e) {
			logger.error("company find by id SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.getInstance().cleanup(connection, pstmt, rs);
		}

		return company;
	}

	@Override
	public void create(Company obj) {
		// TODO Auto-generated method stub
	}

	@Override
	public void updateById(Company obj) {
		// TODO Auto-generated method stub
	}

	@Override
	public void delete(Long id) throws ConnectionException, DriverException, DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM company WHERE id=?";

		connection = CoManager.getInstance().getConnection();

		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, id);

			logger.debug(pstmt.toString());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("company delete SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.getInstance().cleanup(connection, pstmt, null);
		}
	}

	@Override
	public int getRowNumber() throws DAOException, ConnectionException, DriverException {
		Connection connection = null;
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(id) AS TOTAL FROM company";
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
			System.out.println("company update error!");
			logger.error("company update by id SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.getInstance().cleanup(connection, stmt, rs);
		}

		return number;
	}
}
