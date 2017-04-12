package de.diddiz.utils.sql;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Robin Kupper
 */
public class MySQLConnectionPool implements Closeable
{
	private final static int POOL_SIZE = 10;
	private final static long TIME_TO_LIVE = 5 * 60 * 1000L; // 5 Minutes

	static {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (final ClassNotFoundException ex) {
			throw new AssertionError("Can't find mysql driver: ", ex);
		}
	}

	private final List<PooledConnection> connections = new ArrayList<>(POOL_SIZE);
	private final String url, user, password;

	private long nextReaping = System.currentTimeMillis() + TIME_TO_LIVE;

	public MySQLConnectionPool(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}

	@Override
	public synchronized void close() {
		reapConnections();
		for (final Iterator<PooledConnection> itr = connections.iterator(); itr.hasNext();) {
			try {
				itr.next().terminate();
			} catch (final SQLException ex) {}
			itr.remove();
		}
	}

	public synchronized Connection getConnection() throws SQLException {
		if (System.currentTimeMillis() >= nextReaping)
			reapConnections();

		for (final Iterator<PooledConnection> itr = connections.iterator(); itr.hasNext();) {
			final PooledConnection conn = itr.next();
			if (conn.lease()) {
				if (conn.isValid())
					return conn;
				conn.terminate();
				itr.remove();
			}
		}
		final PooledConnection conn = new PooledConnection(DriverManager.getConnection(url, user, password));
		conn.lease();
		if (!conn.isValid()) {
			conn.terminate();
			throw new SQLException("Failed to validate new connection");
		}
		connections.add(conn);
		return conn;
	}

	@SuppressWarnings("resource")
	private void reapConnections() {
		final long stale = System.currentTimeMillis() - TIME_TO_LIVE;
		for (final Iterator<PooledConnection> itr = connections.iterator(); itr.hasNext();) {
			final PooledConnection conn = itr.next();
			if (conn.inUse && stale > conn.lastUse)
				// The connection either got lost, or cached, so we just remove it from the pool do not close it
				itr.remove();
		}
		nextReaping = System.currentTimeMillis() + TIME_TO_LIVE;
	}

	private class PooledConnection extends ForwardingConnection
	{
		private boolean inUse;
		private long lastUse;

		PooledConnection(Connection conn) {
			super(conn);
		}

		@Override
		public void close() throws SQLException {
			synchronized (MySQLConnectionPool.this) {
				inUse = false;

				// Enable auto commit, as this is the expected default state
				if (!conn.getAutoCommit())
					conn.setAutoCommit(true);
			}
		}

		private boolean isValid() {
			try {
				return !conn.isClosed() && conn.isValid(1);
			} catch (final SQLException ex) {
				return false;
			}
		}

		private boolean lease() {
			if (inUse)
				return false;
			inUse = true;
			lastUse = System.currentTimeMillis();
			return true;
		}

		private void terminate() throws SQLException {
			conn.close();
		}
	}
}
