package org.excilys.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.excilys.beans.Company;
import org.excilys.beans.Computer;
import org.excilys.db.CoManagerFactory;
import org.excilys.services.CompanyService;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

/**
 * DB manipulation on Computer table
 * 
 * @author pqwarlot
 *
 */
public class ComputerDAO implements DAO<Computer> {
	
	public List<Computer> findAll() {
		Connection connection = null;
		Statement stmt = null;
		String sql = "SELECT computer.id,computer.name,computer.introduced,"
				+ "computer.discontinued,computer.company_id,company.name AS company_name "
				+ "FROM computer "
				+ "LEFT OUTER JOIN company "
				+ "ON computer.company_id=company.id";
		ResultSet rs = null;
		List<Computer> computers = new ArrayList<>();
		
		connection = CoManagerFactory.getCoManager().getConnection();
		if (connection == null) return computers;
		
		try {
			stmt = (Statement) connection.createStatement();
			rs = stmt.executeQuery(sql);
			
			while(rs.next()){
				computers.add(new Computer(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getTimestamp("introduced"),
						rs.getTimestamp("discontinued"),
						rs.getInt("company_id")
						));
			}
		} catch (SQLException e) {
			System.out.println("computer all finding error!");
//			e.printStackTrace();
		} finally {
			CoManagerFactory.getCoManager().cleanup(connection, stmt, rs);
		}
		
		return computers;
	}
	
	public Computer findById(long id) {
		Computer computer = new Computer();
		if (id < 0) return computer;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id=?";
		ResultSet rs = null;
		
		connection = CoManagerFactory.getCoManager().getConnection();
		if (connection == null) return computer;
		
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			pstmt.setLong(1, id);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				computer = new Computer(
						rs.getInt("id"),
						rs.getString("name"),
						rs.getTimestamp("introduced"),
						rs.getTimestamp("discontinued"),
						rs.getInt("company_id")
						);
			}
		} catch (SQLException e) {
			System.out.println("computer finding error!");
//			e.printStackTrace();
		} finally {
			CoManagerFactory.getCoManager().cleanup(connection, pstmt, rs);
		}
		
		return computer;
	}

	public Computer create(Computer computer) {
		Computer computerEmpty = new Computer();
		if (computer == null) return computerEmpty;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet generatedKeys = null;
		String sql = "INSERT INTO computer (name, discontinued, introduced, company_id) VALUES ( ?, ?, ?, ?)";
		Timestamp introducedTimestamp = null;
		Timestamp discontinuedTimestamp = null;
		
		// Insert null in DB for discontinued and introduced if default timestamp
		if (computer.getIntroduced().equals(new Timestamp(0)) == false) {
			introducedTimestamp = computer.getIntroduced();
		}
		if (computer.getDiscontinued().equals(new Timestamp(0)) == false) {
			discontinuedTimestamp = computer.getDiscontinued();
		}
				
		connection = CoManagerFactory.getCoManager().getConnection();
		if (connection == null) return computerEmpty;
		
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, computer.getName());
			pstmt.setTimestamp(2, introducedTimestamp);
			pstmt.setTimestamp(3, discontinuedTimestamp);
			// Company id not existing
			if (new CompanyService().getCompany(computer.getCompany_id()).isEmpty()) {
				// Set company id to NULL if company id equals 0, error otherwise
				if (computer.getCompany_id() == 0) {
					pstmt.setNull(4, java.sql.Types.INTEGER);
				} else {
					System.out.println("Company id not existing, set it to 0 to force creation.");
					throw new SQLException();
				}
			} else {
				pstmt.setLong(4, computer.getCompany_id());
			}
			
			pstmt.executeUpdate();
			
			generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
            	computer.setId(generatedKeys.getInt(1));
            }
		} catch (SQLException e) {
			System.out.println("computer creation error!");
//			e.printStackTrace();
			computer = computerEmpty;
		} finally {
			CoManagerFactory.getCoManager().cleanup(connection, pstmt, generatedKeys);
		}
		
		return computer;
	}

	public boolean delete(long id) {
		boolean success = true;
		if (id < 0) {
			success = false;
			return success;
		}
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM computer WHERE id=?";
		
		connection = CoManagerFactory.getCoManager().getConnection();
		if (connection == null) return false;
		
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			pstmt.setLong(1, id);

			pstmt .executeUpdate();
		} catch (SQLException e) {
			System.out.println("computer deletion error!");
//			e.printStackTrace();
			success = false;
		} finally {
			CoManagerFactory.getCoManager().cleanup(connection, pstmt, null);
		}
		
		return true;
	}

	public Computer updateById(Computer computer) {
		Computer computerEmpty = new Computer();
		if (computer == null) return computerEmpty;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet generatedKeys = null;
		String sql = "UPDATE computer SET name=?, discontinued=?, introduced=?, company_id=? WHERE id=?";
		Timestamp introducedTimestamp = null;
		Timestamp discontinuedTimestamp = null;
		
		// Insert null in DB for discontinued and introduced if default timestamp
		if (computer.getIntroduced().equals(new Timestamp(0)) == false) {
			introducedTimestamp = computer.getIntroduced();
		}
		if (computer.getDiscontinued().equals(new Timestamp(0)) == false) {
			discontinuedTimestamp = computer.getDiscontinued();
		}
		
		connection = CoManagerFactory.getCoManager().getConnection();
		if (connection == null) return computerEmpty;
		
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			pstmt.setString(1, computer.getName());
			pstmt.setTimestamp(2, discontinuedTimestamp);
			pstmt.setTimestamp(3, introducedTimestamp);
			pstmt.setInt(4, computer.getCompany_id());
			// Company id not existing
			if (new CompanyService().getCompany(computer.getCompany_id()).isEmpty()) {
				// Set company id to NULL if company id equals 0, error otherwise
				if (computer.getCompany_id() == 0) {
					pstmt.setNull(5, java.sql.Types.INTEGER);
				} else {
					System.out.println("Company id not existing, set it to 0 to force creation.");
					throw new SQLException();
				}
			} else {
				pstmt.setLong(5, computer.getCompany_id());
			}

			pstmt.executeUpdate();

			generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
            	computer.setId(generatedKeys.getInt(1));
            }
		} catch (SQLException e) {
			System.out.println("computer update error!");
//			e.printStackTrace();
			computer = computerEmpty;
		} finally {
			CoManagerFactory.getCoManager().cleanup(connection, pstmt, generatedKeys);
		}
		
		return computer;
	}
}
