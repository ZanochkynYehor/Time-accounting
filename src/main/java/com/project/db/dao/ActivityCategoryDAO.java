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
import com.project.db.entity.ActivityCategory;

public class ActivityCategoryDAO implements DAO<ActivityCategory> {
	
	private static final Logger log = LogManager.getLogger(ActivityCategoryDAO.class);
	
	@Override
	public ActivityCategory create(ActivityCategory category) throws DBException {
		String insert = "INSERT INTO activity_categories VALUES (0, ?)";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
			int index = 1;
			pstmt.setString(index++, category.getName());
			if (pstmt.executeUpdate() > 0) {
				rs = pstmt.getGeneratedKeys();
					if (rs.next()) {
						category.setId(rs.getInt(1));
					}
			}
		} catch (SQLException e) {
			log.error("Cannot insert category", e);
			throw new DBException("Cannot insert category", e);
		} finally {
			DAO.close(rs);
			DAO.close(pstmt);
			DAO.close(con);
		}
		return category;
	}

	@Override
	public void update(ActivityCategory category) throws DBException {
		String update = "UPDATE activity_categories SET category_name = ? WHERE category_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(update);
			int index = 1;
			pstmt.setString(index++, category.getName());
			pstmt.setInt(index++, category.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			log.error("Cannot update category", e);
			throw new DBException("Cannot update category", e);
		} finally {
			DAO.close(pstmt);
			DAO.close(con);
		}
	}

	@Override
	public void delete(ActivityCategory category) throws DBException {
		String delete = "DELETE FROM activity_categories WHERE category_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(delete);
			int index = 1;
			pstmt.setInt(index++, category.getId());
			pstmt.executeUpdate();
		} catch (SQLException e) {
			log.error("Cannot delete category", e);
			throw new DBException("Cannot delete category", e);
		} finally {
			DAO.close(pstmt);
			DAO.close(con);
		}
	}

	@Override
	public ActivityCategory get(String name) throws DBException {
		String select = "SELECT * FROM activity_categories WHERE category_name = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ActivityCategory activityCategory = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(select);
			int index = 1;
			pstmt.setString(index++, name);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				activityCategory = getCategoryFromResultSet(rs);
			}
		} catch (SQLException e) {
			log.error("Cannot get category", e);
			throw new DBException("Cannot get category", e);
		} finally {
			DAO.close(rs);
			DAO.close(pstmt);
			DAO.close(con);
		}
		return activityCategory;
	}

	public ActivityCategory get(int categoryId) throws DBException {
		String select = "SELECT * FROM activity_categories WHERE category_id = ?";
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		ActivityCategory activityCategory = null;
		try {
			con = ConnectionPool.getInstance().getConnection();
			pstmt = con.prepareStatement(select);
			int index = 1;
			pstmt.setInt(index++, categoryId);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				activityCategory = getCategoryFromResultSet(rs);
			}
		} catch (SQLException e) {
			log.error("Cannot get category", e);
			throw new DBException("Cannot get category", e);
		} finally {
			DAO.close(rs);
			DAO.close(pstmt);
			DAO.close(con);
		}
		return activityCategory;
	}
	
	@Override
	public List<ActivityCategory> getAll() throws DBException {
		String select = "SELECT * FROM activity_categories";
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<ActivityCategory> list = new ArrayList<>();
		try {
			con = ConnectionPool.getInstance().getConnection();
			stmt = con.createStatement();
			rs = stmt.executeQuery(select);
			while (rs.next()) {
				ActivityCategory activityCategory = getCategoryFromResultSet(rs);
				list.add(activityCategory);
			}
		} catch (SQLException e) {
			log.error("Cannot get all activity categories", e);
			throw new DBException("Cannot get all activity categories", e);
		} finally {
			DAO.close(rs);
			DAO.close(stmt);
			DAO.close(con);
		}
		return list;
	}
	
	private ActivityCategory getCategoryFromResultSet(ResultSet rs) throws SQLException {
		ActivityCategory activityCategory = new ActivityCategory();
		activityCategory.setId(rs.getInt("category_id"));
		activityCategory.setName(rs.getString("category_name"));
		return activityCategory;
	}
}