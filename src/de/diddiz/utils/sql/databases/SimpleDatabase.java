package de.diddiz.utils.sql.databases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * A simple class containing a {@link Connection}. {@code SimpleDatabase} is meant to be used for local databases like sqlite or H2.
 * Sub-classes should add methods to handle common queries, so the actual code doesn't have to handle SQL stuff.
 * 
 * @author Robin Kupper
 */
public abstract class SimpleDatabase implements AutoCloseable
{
	/**
	 * Do never close this manually. When the database is no longer needed use {@link #close()} instead.
	 */
	protected final Connection conn;

	public SimpleDatabase(String driver, String url, String user, String pw) throws SQLException {
		try {
			Class.forName(driver);
		} catch (final ClassNotFoundException ex) {
			throw new AssertionError(ex);
		}
		conn = DriverManager.getConnection(url, user, pw);

		// Initialize database
		try (Statement st = conn.createStatement()) {
			initializeTables(st);
		}
	}

	@Override
	public void close() throws SQLException {
		conn.close();
	}

	/**
	 * This method is called every time a connection is created.
	 * Please use <code>CREATE TABLE IF NOT EXISTS [...]</code>.
	 */
	protected abstract void initializeTables(Statement st) throws SQLException;

	protected static String escape(String str) {
		if (str.indexOf('\'') > 0)
			str = str.replace("'", "''");
		return str;
	}
}
