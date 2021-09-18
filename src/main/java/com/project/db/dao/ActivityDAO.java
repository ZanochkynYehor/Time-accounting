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

public class ActivityDAO implements DAO<Activity> {

	private static final Logger log = LogManager.getLogger(ActivityDAO.class);
	
	@Override
	public Activity create(Activity activity) throws DBException {
		String insert = "INSERT INTO activities VALUES (0, ?, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			pstmt.setString(index++, activity.getName());
			pstmt.setInt(index++, activity.getCategoryId());
			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
					if (rs.next()) {
						activity.setId(rs.getInt(1));
					}
			}
		} catch (SQLException e) {
			log.error("Cannot insert activity", e);
			throw new DBException("Cannot insert activity", e);
		} finally {
			DAO.close(rs);
			DAO.close(pstmt);
			DAO.close(con);
		}
		return activity;
	}

	@Override
	public void update(Activity activity) throws DBException {
		String update = "UPDATE activities SET activity_name = ?, category_id = ? WHERE activity_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(update);
			int index = 1;
			pstmt.setString(index++, activity.getName());
			pstmt.setInt(index++, activity.getCategoryId());
			pstmt.setInt(index++, activity.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			log.error("Cannot update activity", e);
			throw new DBException("Cannot update activity", e);
		} finally {
			DAO.close(pstmt);
			DAO.close(con);
		}
	}

	@Override
	public void delete(Activity activity) throws DBException {
		String delete = "DELETE FROM activities WHERE activity_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(delete);
			int index = 1;
			pstmt.setInt(index++, activity.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			log.error("Cannot delete activity", e);
			throw new DBException("Cannot delete activity", e);
		} finally {
			DAO.close(pstmt);
			DAO.close(con);
		}
	}

	@Override
	public Activity get(String name) throws DBException {
		String select = "SELECT activity_id, activity_name, category_id, category_name FROM activities JOIN activity_categories USING(category_id) WHERE activity_name = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Activity activity = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(select);
			int index = 1;
			pstmt.setString(index++, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				activity = getActivityFromResultSet(rs);
			}
		} catch (SQLException e) {
			log.error("Cannot get activity", e);
			throw new DBException("Cannot get activity", e);
		} finally {
			DAO.close(rs);
			DAO.close(pstmt);
			DAO.close(con);
		}
		return activity;
	}
	
	public Activity get(int activityId) throws DBException {
		String select = "SELECT activity_id, activity_name, category_id, category_name FROM activities JOIN activity_categories USING(category_id) WHERE activity_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Activity activity = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(select);
			int index = 1;
			pstmt.setInt(index++, activityId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				activity = getActivityFromResultSet(rs);
			}
		} catch (SQLException e) {
			log.error("Cannot get activity", e);
			throw new DBException("Cannot get activity", e);
		} finally {
			DAO.close(rs);
			DAO.close(pstmt);
			DAO.close(con);
		}
		return activity;
	}

	public List<Activity> getActivitiesUserDoesNotHave(int userId) throws DBException {
		String select = "SELECT activity_id, activity_name, category_id, category_name FROM activities "
				+ "JOIN activity_categories USING(category_id) "
				+ "WHERE NOT EXISTS (SELECT activity_id FROM users_activities WHERE activity_id = activities.activity_id AND user_id = ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Activity> list = new ArrayList<>();
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(select);
			int index = 1;
			pstmt.setInt(index++, userId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Activity activity = getActivityFromResultSet(rs);
				list.add(activity);
			}
		} catch (SQLException e) {
			log.error("Cannot get activities that user does not have", e);
			throw new DBException("Cannot get activities that user does not have", e);
		} finally {
			DAO.close(rs);
			DAO.close(pstmt);
			DAO.close(con);
		}
		return list;
	}
	
	public List<Activity> getActivitiesByCategory(int categoryId) throws DBException {
		String select = "SELECT activity_id, activity_name, category_id, category_name FROM activities "
				+ "JOIN activity_categories USING(category_id) "
				+ "WHERE category_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Activity> list = new ArrayList<>();
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(select);
			int index = 1;
			pstmt.setInt(index++, categoryId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				Activity activity = getActivityFromResultSet(rs);
				list.add(activity);
			}
		} catch (SQLException e) {
			log.error("Cannot get activities by category", e);
			throw new DBException("Cannot get activities by category", e);
		} finally {
			DAO.close(rs);
			DAO.close(pstmt);
			DAO.close(con);
		}
		return list;
	}
	
	public List<Activity> getAllSortedByUsersCount(String option) throws DBException {
		String select = "SELECT activity_id, activity_name, category_id, category_name FROM activities "
				+ "LEFT JOIN users_activities USING(activity_id) JOIN activity_categories USING(category_id) "
				+ "GROUP BY (activity_id) ORDER BY COUNT(user_id) ";
		if (option.equals("DESC")) {
			select += "DESC";
		}
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Activity> list = new ArrayList<>();
		try {
			con = ConnectionPool.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(select);
			while (rs.next()) {
				Activity activity = getActivityFromResultSet(rs);
				list.add(activity);
			}
		} catch (SQLException e) {
			log.error("Cannot get sorted by users count activities", e);
			throw new DBException("Cannot get sorted by users count activities", e);
		} finally {
			DAO.close(rs);
			DAO.close(stmt);
			DAO.close(con);
		}
		return list;
	}
	
	@Override
	public List<Activity> getAll() throws DBException {
		String select = "SELECT activity_id, activity_name, category_id, category_name FROM activities JOIN activity_categories USING(category_id) ORDER BY activity_id";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<Activity> list = new ArrayList<>();
		try {
			con = ConnectionPool.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(select);
			while (rs.next()) {
				Activity activity = getActivityFromResultSet(rs);
				list.add(activity);
			}
		} catch (SQLException e) {
			log.error("Cannot get all activities", e);
			throw new DBException("Cannot get all activities", e);
		} finally {
			DAO.close(rs);
			DAO.close(stmt);
			DAO.close(con);
		}
		return list;
	}

	private Activity getActivityFromResultSet(ResultSet rs) throws SQLException {
		Activity activity = new Activity();
		activity.setId(rs.getInt("activity_id"));
		activity.setName(rs.getString("activity_name"));
		activity.setCategoryId(rs.getInt("category_id"));
		activity.setCategory(rs.getString("category_name"));
		return activity;
	}
}
