package org.excilys.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.excilys.beans.Company;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

/**
 * DB manipulation on Company table
 * 
 * @author pqwarlot
 *
 */
public class CompanyDAO extends DAO<Company> {
		
	public List<Company> findAll() {
		Connection connection = null;
		Statement stmt = null;
		String sql = "SELECT id,name FROM company";
		ResultSet rs = null;
		List<Company> companies = new ArrayList<>();
		
		connection = CoManager.getInstance().getConnection();
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
			e.printStackTrace();
		} finally {
			CoManager.getInstance().cleanup(connection, stmt, rs);
		}
		
		return companies;
	}

	public Company find(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public Company create(Company obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public Company update(Company obj) {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean delete(Company obj) {
		// TODO Auto-generated method stub
		return false;
	}
}
