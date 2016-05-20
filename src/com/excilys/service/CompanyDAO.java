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
import com.excilys.db.CoManagerFactory;
import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DAOException;
import com.excilys.exceptions.DriverException;

/**
 * DB manipulation on Company table
 * 
 * @author pqwarlot
 *
 */
public class CompanyDAO implements DAO<Company> {
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

	public List<Company> findAll() throws DriverException, ConnectionException, DAOException {
		Connection connection = null;
		Statement stmt = null;
		String sql = "SELECT id,name FROM company";
		ResultSet rs = null;
		List<Company> companies = new ArrayList<>();

		connection = CoManagerFactory.getCoManager().getConnection();

		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);

			while (rs.next()) {
				companies.add(new Company(rs.getLong("id"), rs.getString("name")));
			}
		} catch (SQLException e) {
			logger.error("campany fin all SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManagerFactory.getCoManager().cleanup(connection, stmt, rs);
		}

		return companies;
	}

	public Company findById(Long id) throws ConnectionException, DriverException, DAOException {
		Connection connection = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id=?";
		ResultSet rs = null;
		Company company = null;

		connection = CoManagerFactory.getCoManager().getConnection();

		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				company = new Company(rs.getLong("id"), rs.getString("name"));
			}
		} catch (SQLException e) {
			logger.error("company find by id SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManagerFactory.getCoManager().cleanup(connection, pstmt, rs);
		}

		return company;
	}

	public void create(Company obj) {
		// TODO Auto-generated method stub
	}

	public void updateById(Company obj) {
		// TODO Auto-generated method stub
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
	}
}
