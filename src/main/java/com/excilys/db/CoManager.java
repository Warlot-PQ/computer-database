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
	private static Logger LOGGER = null;
	private static HikariDataSource HIKARI_DS = null;
	protected static CoManager INSTANCE = new CoManager();
	
	private CoManager() {
		HikariConfig config= new HikariConfig("/hikari.properties");
		config.setMaximumPoolSize(50);
		config.addDataSourceProperty("cachePrepStmts", "true");
		config.addDataSourceProperty("prepStmtCacheSize", "250");
		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
		HIKARI_DS = new HikariDataSource(config);
		
		LOGGER = LoggerFactory.getLogger(this.getClass());
	}

	/**
	 * Get an HikariCp Connection object.
	 * @return Connection object
	 */
	protected Connection getConnection() {
		Connection connection = null;
		try {
			connection = HIKARI_DS.getConnection();
			LOGGER.debug("Connection asked");
		} catch (SQLException e) {
			LOGGER.error("DB connection error!", e);
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
