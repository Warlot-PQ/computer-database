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
import com.excilys.db.ConnectionLocal;
import com.excilys.exception.DAOException;
import com.excilys.service.interfaces.DAOCompany;
import com.excilys.service.mapper.MapperDTO;

/**
 * DB manipulation on Company table.
 * Singleton class.
 * 
 * @author pqwarlot
 *
 */
public class CompanyDAO implements DAOCompany {
	private static Logger logger = null;
	private static CompanyDAO instance = new CompanyDAO();
	private static ConnectionLocal LOCAL_CONNECTION = ConnectionLocal.getInstance();

	private CompanyDAO() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	protected static CompanyDAO getInstance() {
		return instance;
	}

	@Override
	public List<CompanyDTO> findAll() throws DAOException {
		Connection connection = LOCAL_CONNECTION.getConnection();
		Statement stmt = null;
		String sql = "SELECT id,name " + "FROM company";
		ResultSet rs = null;
		List<CompanyDTO> companies = new ArrayList<>();

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
			CoManager.cleanup(stmt, rs);
		}

		return companies;
	}

	@Override
	public List<CompanyDTO> findFromTo(int offset, int limit) throws DAOException {
		Connection connection = LOCAL_CONNECTION.getConnection();
		PreparedStatement pstmt = null;
		String sql = "SELECT id,name " + "FROM company " + "LIMIT ?, ?";
		ResultSet rs = null;
		List<CompanyDTO> companies = new ArrayList<>();

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
			CoManager.cleanup(pstmt, rs);
		}

		return companies;
	}

	@Override
	public CompanyDTO findById(Long id) throws DAOException {
		Connection connection = LOCAL_CONNECTION.getConnection();
		PreparedStatement pstmt = null;
		String sql = "SELECT id,name " + "FROM company " + "WHERE id=?";
		ResultSet rs = null;
		CompanyDTO company = null;

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
			CoManager.cleanup(pstmt, rs);
		}

		return company;
	}

	@Override
	public void create(Company obj) throws DAOException {
		throw new UnsupportedClassVersionError("Not implemeted yet");
	}

	@Override
	public void updateById(Company obj) throws DAOException {
		throw new UnsupportedClassVersionError("Not implemeted yet");
	}

	@Override
	public void delete(Long id) throws DAOException {
		Connection connection = LOCAL_CONNECTION.getConnection();
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM company WHERE id=?";
		
		try {
			pstmt = connection.prepareStatement(sql);
			pstmt.setLong(1, id);
			logger.debug(pstmt.toString());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			logger.error("company delete SQL error!", e);

			throw new DAOException(e);
		} finally {
			CoManager.cleanup(pstmt, null);	
		}
	}

	@Override
	public int getRowNumber() throws DAOException {
		Connection connection = LOCAL_CONNECTION.getConnection();
		Statement stmt = null;
		ResultSet rs = null;
		String sql = "SELECT COUNT(id) AS TOTAL FROM company";
		int number = 0;

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
			CoManager.cleanup(stmt, rs);
		}

		return number;
	}
}
