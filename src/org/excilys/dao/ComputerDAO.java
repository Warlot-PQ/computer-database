package org.excilys.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.excilys.beans.Computer;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import com.mysql.jdbc.Statement;

/**
 * DB manipulation on Computer table
 * 
 * @author pqwarlot
 *
 */
public class ComputerDAO extends DAO<Computer> {
	
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
		
		connection = CoManager.getInstance().getConnection();
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
			e.printStackTrace();
		} finally {
			CoManager.getInstance().cleanup(connection, stmt, rs);
		}
		
		return computers;
	}
	
	public Computer find(int id) {
		Computer computer = new Computer();
		if (id < 0) return computer;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		String sql = "SELECT id,name,introduced,discontinued,company_id FROM computer WHERE id=?";
		ResultSet rs = null;
		
		connection = CoManager.getInstance().getConnection();
		if (connection == null) return computer;
		
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			pstmt.setInt(1, id);
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
			System.err.println();
		} finally {
			CoManager.getInstance().cleanup(connection, pstmt, rs);
		}
		
		return computer;
	}

	public Computer create(Computer computer) {
		Computer computerEmpty = new Computer();
		if (computer == null) return computerEmpty;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
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
		
		connection = CoManager.getInstance().getConnection();
		if (connection == null) return computerEmpty;
		
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			pstmt.setString(1, computer.getName());
			pstmt.setTimestamp(2, introducedTimestamp);
			pstmt.setTimestamp(3, discontinuedTimestamp);
			pstmt.setInt(4, computer.getCompany_id());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CoManager.getInstance().cleanup(connection, pstmt, null);
		}
		
		return computer;
	}

	public boolean delete(Computer computer) {
		if (computer == null) return false;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
		String sql = "DELETE FROM computer WHERE id=?";
		
		connection = CoManager.getInstance().getConnection();
		if (connection == null) return false;
		
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			pstmt.setInt(1, computer.getId());

			pstmt .executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CoManager.getInstance().cleanup(connection, pstmt, null);
		}
		
		return true;
	}

	public Computer update(Computer computer) {
		Computer computerEmpty = new Computer();
		if (computer == null) return computerEmpty;
		
		Connection connection = null;
		PreparedStatement pstmt = null;
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
		
		connection = CoManager.getInstance().getConnection();
		if (connection == null) return computerEmpty;
		
		try {
			pstmt = (PreparedStatement) connection.prepareStatement(sql);
			pstmt.setString(1, computer.getName());
			pstmt.setTimestamp(2, discontinuedTimestamp);
			pstmt.setTimestamp(3, introducedTimestamp);
			pstmt.setInt(4, computer.getCompany_id());
			pstmt.setInt(5, computer.getId());

			pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			CoManager.getInstance().cleanup(connection, pstmt, null);
		}
		
		return computer;
	}
}
