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
 * Connection DB factory, use singleton pattern
 * 
 * @author pqwarlot
 *
 */
public class CoManager {
	private static Logger logger = null;
	private static HikariDataSource ds = null;
	protected static CoManager instance = new CoManager();
	
	private CoManager() {
		HikariConfig config= new HikariConfig("/hikari.properties");
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		ds = new HikariDataSource(config);
		
		logger = LoggerFactory.getLogger(this.getClass());
	}

	/**
	 * Get an HikariCp Connection object.
	 * @return
	 */
	public Connection getConnection() {
		Connection connection = null;
		try {
			connection = ds.getConnection();
		} catch (SQLException e) {
			logger.error("DB connection error!", e);
			System.exit(-1);
		}
		return connection;
	}

	public static CoManager getInstance() {
		if (instance == null) {
			synchronized (CoManager.class) {
				if (instance == null) {
					instance = new CoManager();
				}
			}
		}
		return instance;
	}

	/**
	 * Close given parameters.
	 * @param connection Connection object to close
	 * @param stat Statement object to close
	 * @param rs ResultSet object to close
	 */
	public static void cleanup(Connection connection, Statement stat, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("Cannot close Connection", e);
			}
		}
		if (stat != null) {
			try {
				stat.close();
			} catch (SQLException e) {
				logger.error("Cannot close Statement", e);
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.error("Cannot close ResultSet", e);
			}
		}
	}
	
	/**
	 * Start a transaction link to the given Connection object
	 * @param con Connection object
	 */
	public static void beginTransaction(Connection con) {
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			logger.error("Begin transaction error", e);
			System.exit(-1);
		}
	}
	
	/**
	 * End the transaction link to the given Connection object
	 * @param con Connection object
	 */
	public static void endTransaction(Connection con) {
		try {
			con.setAutoCommit(true);
		} catch (SQLException e) {
			logger.error("End transaction error", e);
			System.exit(-1);
		}
	}
}
