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
import com.project.db.entity.Activity;
import com.project.db.entity.User;
import com.project.db.entity.UserActivity;

public class UserActivityDAO {

	private static final Logger log = LogManager.getLogger(UserActivityDAO.class);
	
	public void create(User user, String[] activitiesIds, String approved) throws DBException {
		Connection con = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			con.setAutoCommit(false);
			for (String id : activitiesIds) {
				create(con, user.getId(), Integer.parseInt(id), approved);
			}
			con.commit();
		} catch (SQLException | NumberFormatException ex) {
			log.error("Cannot create user activities", ex);
			DAO.rollback(con);
			throw new DBException("Cannot create user activities", ex);
		} finally {
			DAO.close(con);
		}
	}
	
	private void create(Connection con, int userId, int activityId, String approved) throws SQLException {
		String insert = "INSERT INTO users_activities VALUES (?, ?, ?, 0, 0)";
		PreparedStatement pstmt = null;
		try {
			pstmt = con.prepareStatement(insert);
			int index = 1;
			pstmt.setInt(index++, userId);
			pstmt.setInt(index++, activityId);
			pstmt.setString(index++, approved);
			pstmt.executeUpdate();
		} finally {
			DAO.close(pstmt);
		}
	}

	public void update(int userId, int activityId, String approved, String startDateTime, String finishTime) throws DBException {
		String update = "UPDATE users_activities SET approved = ?, start_datetime = ?, finish_time = ? WHERE user_id = ? AND activity_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(update);
			int index = 1;
			pstmt.setString(index++, approved);
			pstmt.setString(index++, startDateTime);
			pstmt.setString(index++, finishTime);
			pstmt.setInt(index++, userId);
			pstmt.setInt(index++, activityId);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			log.error("Cannot update user activity", ex);
			throw new DBException("Cannot update user activity", ex);
		} finally {
			DAO.close(pstmt);
			DAO.close(con);
		}
	}

	public void delete(int userId, int activityId) throws DBException {
		String delete = "DELETE FROM users_activities WHERE user_id = ? AND activity_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(delete);
			int index = 1;
			pstmt.setInt(index++, userId);
			pstmt.setInt(index++, activityId);
			pstmt.executeUpdate();
		} catch (SQLException ex) {
			log.error("Cannot delete user activity", ex);
			throw new DBException("Cannot delete user activity", ex);
		} finally {
			DAO.close(pstmt);
			DAO.close(con);
		}
	}

	public UserActivity getUserActivity(User user, int activityId) throws DBException {
		String select = "SELECT activity_id, activity_name, category_name, approved, start_datetime, finish_time FROM users_activities "
				+ "JOIN activities USING(activity_id) "
				+ "JOIN activity_categories USING (category_id) "
				+ "WHERE user_id = ? AND activity_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		UserActivity userActivity = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(select);
			int index = 1;
			pstmt.setInt(index++, user.getId());
			pstmt.setInt(index++, activityId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				userActivity = getUserActivityFromResultSet(user, rs);
			}
		} catch (SQLException ex) {
			log.error("Cannot get user activity", ex);
			throw new DBException("Cannot get user activity", ex);
		} finally {
			DAO.close(rs);
			DAO.close(pstmt);
			DAO.close(con);
		}
		return userActivity;
	}
	
	/**
	 * Get all user activities
 	 */
	public List<UserActivity> getAllUserActivities(User user) throws DBException {
		String select = "SELECT activity_id, activity_name, category_name, approved, start_datetime, finish_time FROM users_activities "
				+ "JOIN activities USING(activity_id) "
				+ "JOIN activity_categories USING (category_id) "
				+ "WHERE user_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<UserActivity> list = new ArrayList<>();
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(select);
			int index = 1;
			pstmt.setInt(index++, user.getId());
			rs = pstmt.executeQuery();
			while (rs.next()) {
				UserActivity userActivity = getUserActivityFromResultSet(user, rs);
				list.add(userActivity);
			}
		} catch (SQLException ex) {
			log.error("Cannot get all user activities", ex);
			throw new DBException("Cannot get all user activities", ex);
		} finally {
			DAO.close(rs);
			DAO.close(pstmt);
			DAO.close(con);
		}
		return list;
	}

	public List<UserActivity> getNotApprovedActivities() throws DBException {
		String select = "SELECT user_id, user_login, activity_id, activity_name, category_name FROM users_activities "
				+ "JOIN users USING(user_id) "
				+ "JOIN activities USING(activity_id) "
				+ "JOIN activity_categories USING(category_id) "
				+ "WHERE approved = 'No'";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<UserActivity> list = new ArrayList<>();
		try {
			con = ConnectionPool.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(select);
			while (rs.next()) {
				UserActivity userActivity = new UserActivity();
				userActivity.getUser().setId(rs.getInt("user_id"));
				userActivity.getUser().setLogin(rs.getString("user_login"));
				userActivity.getActivity().setId(rs.getInt("activity_id"));
				userActivity.getActivity().setName(rs.getString("activity_name"));
				userActivity.getActivity().setCategory(rs.getString("category_name"));
				list.add(userActivity);
			}
		} catch (SQLException ex) {
			log.error("Cannot get not approved activities", ex);
			throw new DBException("Cannot get not approved activities", ex);
		} finally {
			DAO.close(rs);
			DAO.close(stmt);
			DAO.close(con);
		}
		return list;		
	}
	
	private UserActivity getUserActivityFromResultSet(User user, ResultSet rs) throws SQLException {
		UserActivity userActivity = new UserActivity();
		userActivity.setUser(user);
		Activity activity = new Activity(rs.getString("activity_name"), rs.getString("category_name"));
		activity.setId(rs.getInt("activity_id"));
		userActivity.setActivity(activity);
		userActivity.setApproved(rs.getString("approved"));
		userActivity.setStartDateTime(rs.getString("start_datetime"));
		userActivity.setFinishTime(rs.getString("finish_time"));
		return userActivity;
	}
}