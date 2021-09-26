package com.project.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mysql.cj.jdbc.MysqlConnectionPoolDataSource;

public class DBUtils {

	static final Logger log = LogManager.getLogger(DBUtils.class);
	
	private static DBUtils instance;
	private DataSource ds;

	private DBUtils() {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Time-accounting connection pool");
		} catch (NamingException e) {
			log.error(e.getMessage());
			MysqlConnectionPoolDataSource mds = new MysqlConnectionPoolDataSource();
			mds.setURL("jdbc:mysql://localhost:3306/Time-accounting");
			mds.setUser("root");
			mds.setPassword("root");
			ds = mds;
		}
	}

	public static synchronized DBUtils getInstance() {
		if (instance == null) {
			instance = new DBUtils();
		}
		return instance;
	}

	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}
	
	public static void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				log.error("Cannot close Connection", e);
			}
		}
	}

	public static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				log.error("Cannot close Statement", e);
			}
		}
	}

	public static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				log.error("Cannot close ResultSet", e);
			}
		}
	}
	
	public static void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException e) {
				log.error("Cannot rollback connection", e);
			}
		}
	}
}