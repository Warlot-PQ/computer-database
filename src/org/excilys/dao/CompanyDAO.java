package org.excilys.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.excilys.beans.Company;
import org.excilys.beans.Computer;
import org.excilys.db.CoManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

/**
 * DB manipulation on Company table
 * 
 * @author pqwarlot
 *
 */
public class CompanyDAO implements DAO<Company> {
		
	public List<Company> findAll() {
		Connection connection = null;
		Statement stmt = null;
		String sql = "SELECT id,name FROM company";
		ResultSet rs = null;
		List<Company> companies = new ArrayList<>();
		
		connection = CoManagerFactory.getCoManager().getConnection();
		if (connection == null) return companies;
		
		try {
			stmt = (Statement) connection.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				companies.add(new Company(
						rs.getInt("id"),
						rs.getString("name")
						));
			}
		} catch (SQLException e) {
			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.error("campany fin all SQL error!", e);
		} finally {
			CoManagerFactory.getCoManager().cleanup(connection, stmt, rs);
		}
		
		return companies;
	}

	public Company findById(long id) {
		Company company = new Company();
		if (id < 0) return company;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id=?";
		ResultSet rs = null;
		
		connection = CoManagerFactory.getCoManager().getConnection();
		if (connection == null) return company;
		
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				company = new Company(
						rs.getInt("id"),
						rs.getString("name"));
			}
		} catch (SQLException e) {
			System.out.println("company finding error!");

			Logger logger = LoggerFactory.getLogger(this.getClass());
			logger.error("company find by id SQL error!", e);
		} finally {
			CoManagerFactory.getCoManager().cleanup(connection, pstmt, rs);
		}
		
		return company;
	}

	public Company create(Company obj) {
		// TODO Auto-generated method stub
		return new Company();
	}

	public Company updateById(Company obj) {
		// TODO Auto-generated method stub
		return new Company();
	}

	public boolean delete(long id) {
		// TODO Auto-generated method stub
		return false;
	}
}
