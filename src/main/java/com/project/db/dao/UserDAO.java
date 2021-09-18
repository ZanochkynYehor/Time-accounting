package com.project.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.project.db.ConnectionPool;
import com.project.db.DBException;
import com.project.db.entity.User;

public class UserDAO implements DAO<User> {

	private static final Logger log = LogManager.getLogger(UserDAO.class);
	
	@Override
	public User create(User user) throws DBException {
		String insert = "INSERT INTO users VALUES (0, ?, ?, 2)";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			pstmt.setString(1, user.getLogin());
			pstmt.setString(2, user.getPassword());
			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
					if (rs.next()) {
						user.setId(rs.getInt(1));
					}
			}
		} catch (SQLException e) {
			log.error("Cannot insert user", e);
			throw new DBException("Cannot insert user", e);
		} finally {
			DAO.close(rs);
			DAO.close(pstmt);
			DAO.close(con);
		}
		return user;
	}

	@Override
	public void update(User user) throws DBException {
		String update = "UPDATE users SET user_login = ?, user_password = ?, role_id = ? WHERE user_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(update);
			int index = 1;
			pstmt.setString(index++, user.getLogin());
			pstmt.setString(index++, user.getPassword());
			pstmt.setInt(index++, user.getRoleId());
			pstmt.setInt(index++, user.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			log.error("Cannot update user", e);
			throw new DBException("Cannot update user", e);
		} finally {
			DAO.close(pstmt);
			DAO.close(con);
		}
	}

	@Override
	public void delete(User user) throws DBException {
		String delete = "DELETE FROM users WHERE user_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(delete);
			int index = 1;
			pstmt.setInt(index++, user.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			log.error("Cannot delete user", e);
			throw new DBException("Cannot delete user", e);
		} finally {
			DAO.close(pstmt);
			DAO.close(con);
		}
	}

	@Override
	public User get(String login) throws DBException {
		String select = "SELECT user_id, user_login, user_password, role_name FROM users JOIN user_roles USING(role_id) WHERE user_login = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(select);
			int index = 1;
			pstmt.setString(index++, login);
			rs = pstmt.executeQuery();
				if (rs.next()) {
					user = getUserFromResultSet(rs);
				}
		} catch (SQLException e) {
			log.error("Cannot get user", e);
			throw new DBException("Cannot get user", e);
		} finally {
			DAO.close(rs);
			DAO.close(pstmt);
			DAO.close(con);
		}
		return user;
	}
	
	public User get(int userId) throws DBException {
		String select = "SELECT user_id, user_login, user_password, role_name FROM users JOIN user_roles USING(role_id) WHERE user_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(select);
			int index = 1;
			pstmt.setInt(index++, userId);
			rs = pstmt.executeQuery();
				if (rs.next()) {
					user = getUserFromResultSet(rs);
				}
		} catch (SQLException e) {
			log.error("Cannot get user", e);
			throw new DBException("Cannot get user", e);
		} finally {
			DAO.close(rs);
			DAO.close(pstmt);
			DAO.close(con);
		}
		return user;
	}

	@Override
	public List<User> getAll() throws DBException {
		String select = "SELECT user_id, user_login, user_password, role_name FROM users JOIN user_roles USING(role_id)";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<User> list = new ArrayList<>();
		try {
			con = ConnectionPool.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(select);
			while (rs.next()) {
				User user = getUserFromResultSet(rs);
				list.add(user);
			}
		} catch (SQLException e) {
			log.error("Cannot get all users", e);
			throw new DBException("Cannot get all users", e);
		} finally {
			DAO.close(rs);
			DAO.close(stmt);
			DAO.close(con);
		}
		return list;
	}

	private User getUserFromResultSet(ResultSet rs) throws SQLException {
		User user = new User();
		user.setId(rs.getInt("user_id"));
		user.setLogin(rs.getString("user_login"));
		user.setPassword(rs.getString("user_password"));
		user.setRole(rs.getString("role_name"));
		return user;
	}
}