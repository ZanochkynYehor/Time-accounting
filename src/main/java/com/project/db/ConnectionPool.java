package com.project.db;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class ConnectionPool {

	private static ConnectionPool pool;
	private DataSource ds;

	private ConnectionPool() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Time-accounting connection pool");
		} catch (NamingException e) {
			throw new IllegalStateException("Cannot init ConnectionPool", e);
		}
	}

	public static synchronized ConnectionPool getInstance() {
		if (pool == null) {
			pool = new ConnectionPool();
		}
		return pool;
	}

	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
}