package com.excilys.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ThreadLocal containing a Connection object. Each thread can have it's own
 * connection.
<<<<<<< HEAD
=======
 * Singleton class.
>>>>>>> Improvment from Gattling tests.
 * 
 * @author pqwarlot
 *
 */
public class ConnectionLocal {
	private static Logger logger;
	// Each thread as it's own instance of this static field
	private static final ThreadLocal<Connection> CONNECTION_TL = new ThreadLocal<Connection>();
	private static final ConnectionLocal INSTANCE = new ConnectionLocal();
	private static final CoManager CO_MANAGER = CoManager.getInstance();

	private ConnectionLocal() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	/**
	 * Get the singleton instance
	 * 
	 * @return ConnectionLocal object
	 */
	public static ConnectionLocal getInstance() {
		return INSTANCE;
	}

	public void newConnection() {
		logger.debug("Creating connection");
		Connection connection = CO_MANAGER.getConnection();

		CONNECTION_TL.set(connection);
	}

	/**
	 * Close the connection linked to the current thread.
	 */
	public void cleanup() {
		Connection connection = CONNECTION_TL.get();

		logger.debug("Closing connection");
		CoManager.cleanup(connection);
	}

	/**
	 * Get the connection linked to the current thread
	 * 
	 * @return Connection object
	 */
	public Connection getConnection() {
		return CONNECTION_TL.get();
	}

	/**
	 * Create a connection available on the current thread and start a
	 * transaction on it
	 */
	public void beginTransaction() {
		logger.debug("Beginning transaction");
		Connection connection = CO_MANAGER.getConnection();

		CONNECTION_TL.set(connection);

		try {
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			logger.error("Begin transaction error", e);
			System.exit(-1);
		}
	}

	/**
	 * Get the connection linked to the current thread and end the transaction
	 */
	public void endTransaction() {
		Connection connection = CONNECTION_TL.get();

		try {
			connection.commit();
			logger.debug("Ending transaction");
			connection.setAutoCommit(true);
		} catch (SQLException e) {
			logger.error("End transaction error", e);
			System.exit(-1);
		} finally {
			cleanup();
		}
	}

	/**
	 * Get the connection linked to the current thread and rollback on it
	 */
	public void rollBack() {
		try {
			CONNECTION_TL.get().rollback();
		} catch (SQLException e) {
			logger.error("Rollback error", e);
		}
	}
}
