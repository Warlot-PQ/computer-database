package com.excilys.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.exceptions.ConnectionException;
import com.excilys.exceptions.DriverException;

/**
 * Connection DB factory, use singleton pattern
 * 
 * @author pqwarlot
 *
 */
public class CoManager {
	protected static CoManager instance = null;
	private static Logger logger = null;
	
	protected ResourceBundle properties;
	protected static String resourceBundle = "com/excilys/db/config";
	
	protected CoManager() throws DriverException {
		logger = LoggerFactory.getLogger(this.getClass());
		properties = ResourceBundle.getBundle(resourceBundle);
		
		try {
			Class.forName(properties.getString("DB_DRIVER"));
		} catch (ClassNotFoundException e) {
			logger.error("Driver load error!", e);
			
			throw new DriverException(e);
		}
	}

	public Connection getConnection() throws ConnectionException {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(properties.getString("JDBC_URL"), properties.getString("DB_LOGIN"), properties.getString("DB_PASSWORD"));
			
		} catch (SQLException e) {
			logger.error("DB connection exception!", e);
			
			throw new ConnectionException(e);
		}
		return connection;
	}
	
	public static CoManager getInstance() throws DriverException {
		if (instance == null) {
			synchronized (CoManager.class) {
				if (instance == null) {
					instance = new CoManager();
				}
			}
		}
		return instance;
	}
	
	public void cleanup(Connection connection, Statement stat, ResultSet rs) {
		if (rs !=null) {
			try {
				rs.close();
			} catch (SQLException e) {
			}
		}
		if (stat!=null) {
			try {
				stat.close();
			} catch (SQLException e) {
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
			}
		}
	}
}
