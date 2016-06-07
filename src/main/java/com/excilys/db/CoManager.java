package com.excilys.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

/**
 * Connection DB factory, use singleton pattern.
 * Singleton class.
 * 
 * @author pqwarlot
 *
 */
public class CoManager {
	private static Logger logger = null;
	private static HikariDataSource hikariDS = null;
	protected static final CoManager INSTANCE = new CoManager();
	
	private CoManager() {
		logger = LoggerFactory.getLogger(this.getClass());
		
		HikariConfig config= new HikariConfig("/hikari.properties");
		config.setMaximumPoolSize(50);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		try {
			hikariDS = new HikariDataSource(config);
		} catch (Exception e) {
			logger.error("HikariCp config error", e);
			System.exit(-1);
		}
	}

	/**
	 * Get an HikariCp Connection object.
	 * @return Connection object
	 */
	protected Connection getConnection() {
		Connection connection = null;
		try {
			connection = hikariDS.getConnection();
			logger.debug("Connection asked");
		} catch (SQLException e) {
			logger.error("DB connection error!", e);
			System.exit(-1);
		}
		return connection;
	}

	public static CoManager getInstance() {
		return INSTANCE;
	}


	public static void cleanup(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
				logger.debug("Connection closed");
			} catch (SQLException e) {
				logger.error("Cannot close Connection", e);
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
				logger.debug("ResultSet closed");
			} catch (SQLException e) {
				logger.error("Cannot close ResultSet", e);
			}
		}
		if (stat != null) {
			try {
				stat.close();
				logger.debug("Statement closed");
			} catch (SQLException e) {
				logger.error("Cannot close Statement", e);
			}
		}
	}
}
