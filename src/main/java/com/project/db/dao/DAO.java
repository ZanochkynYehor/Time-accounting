package com.project.db.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.db.DBException;

public interface DAO<T> {

	static final Logger log = LogManager.getLogger(DAO.class);
	
	T create(T obj) throws DBException;

	void update(T obj) throws DBException;

	void delete(T obj) throws DBException;

	T get(String name) throws DBException;

	List<T> getAll() throws DBException;

	static void close(Connection con) {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				log.error("Cannot close Connection", e);
			}
		}
	}

	static void close(Statement stmt) {
		if (stmt != null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				log.error("Cannot close Statement", e);
			}
		}
	}

	static void close(ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				log.error("Cannot close ResultSet", e);
			}
		}
	}
	
	static void rollback(Connection con) {
		if (con != null) {
			try {
				con.rollback();
			} catch (SQLException e) {
				log.error("Cannot rollback connection", e);
			}
		}
	}
}