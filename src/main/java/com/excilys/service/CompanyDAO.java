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

import com.excilys.bean.Company;
import com.excilys.bean.CompanyDTO;
import com.excilys.db.CoManager;
import com.excilys.exception.DAOException;
import com.excilys.service.interfaces.DAOCompany;
import com.excilys.service.mapper.MapperDTO;

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
	public List<CompanyDTO> findAll() {
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
				companies.add(MapperDTO.createCompanyDTO(rs));
			}
		} catch (SQLException e) {
			logger.error("campany fin all SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.cleanup(connection, stmt, rs);
		}

		return companies;
	}

	@Override
	public List<CompanyDTO> findFromTo(int offset, int limit) {
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
			logger.debug(pstmt.toString());
			rs = pstmt.executeQuery();

			while (rs.next()) {
				companies.add(MapperDTO.createCompanyDTO(rs));
			}
		} catch (SQLException e) {
			logger.error("company find by id SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.cleanup(connection, pstmt, rs);
		}

		return companies;
	}

	@Override
	public CompanyDTO findById(Long id) {
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
				company = MapperDTO.createCompanyDTO(rs);
			}
		} catch (SQLException e) {
			logger.error("company find by id SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.cleanup(connection, pstmt, rs);
		}

		return company;
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
	public void delete(Long id, Connection connection) {
		boolean internalConnection = false;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM company WHERE id=?";
		
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
			logger.error("company delete SQL error!", e);

			throw new DAOException(e);
		} finally {
			if (internalConnection) {
				CoManager.cleanup(connection, pstmt, null);
			} else {
				CoManager.cleanup(null, pstmt, null);				
			}
		}
	}

	@Override
	public int getRowNumber() {
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
			CoManager.cleanup(connection, stmt, rs);
		}

		return number;
	}
}
