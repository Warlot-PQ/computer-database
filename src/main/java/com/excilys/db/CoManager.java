package com.excilys.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * Connection DB factory, use singleton pattern.
 * Singleton class.
 * 
 * @author pqwarlot
 *
 */
@Repository
@Scope("singleton")
public class CoManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(CoManager.class);
	@Autowired
	@Qualifier("hikariDataSource")
	private DataSource hikariDS;
	
	public CoManager() {
	}
	
	/**
	 * Get an HikariCp Connection object.
	 * @return Connection object
	 */
	protected Connection getConnection() {
		Connection connection = null;
		try {
			connection = hikariDS.getConnection();
			LOGGER.debug("Connection asked");
		} catch (SQLException e) {
			LOGGER.error("DB connection error!", e);
			System.exit(-1);
		}
		return connection;
	}

	public static void cleanup(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
				LOGGER.debug("Connection closed");
			} catch (SQLException e) {
				LOGGER.error("Cannot close Connection", e);
			}
		}
	}
	
	/**
	 * Close given parameters.
	 * @param stat Statement object to close
	 * @param rs ResultSet object to close
	 */
	public static void cleanup(Statement stat, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
				LOGGER.debug("ResultSet closed");
			} catch (SQLException e) {
				LOGGER.error("Cannot close ResultSet", e);
			}
		}
		if (stat != null) {
			try {
				stat.close();
				LOGGER.debug("Statement closed");
			} catch (SQLException e) {
				LOGGER.error("Cannot close Statement", e);
			}
		}
	}
}
