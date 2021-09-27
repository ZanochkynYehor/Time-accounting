package com.project.db.dao;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.project.db.DBException;
import com.project.db.entity.Activity;
import com.project.db.entity.ActivityCategory;
import com.project.db.entity.User;

public class ActivityDAOTest {

	private ActivityDAO activityDao;
	private Activity activity;
	private static ActivityCategory category;
	private static int categoryId;
	
	@BeforeClass
	public static void beforeClass() throws DBException {
		ActivityCategoryDAO categoryDao = new ActivityCategoryDAO();
		category = categoryDao.create(ActivityCategory.createCategory("test"));
		categoryId = category.getId();
	}
	
	@AfterClass
	public static void afterClass() throws DBException {
		ActivityCategoryDAO categoryDao = new ActivityCategoryDAO();
		categoryDao.delete(category);
	}
	
	@Before
	public void beforeTest() throws DBException {
		activityDao = new ActivityDAO();
		activity = activityDao.create(Activity.createActivity("test", categoryId));
	}
	
	@After
	public void afterTest() throws DBException {
		activityDao = new ActivityDAO();
		activityDao.delete(activity);
	}
	
	@Test
	public void testCreate() throws DBException {
		Assert.assertNotEquals(0, activity.getId());
	}
	
	@Test
	public void testUpdate() throws DBException {
		activity.setName("test123");
		activityDao.update(activity);
		Activity updatedActivity = activityDao.get("test123");
		Assert.assertEquals(activity, updatedActivity);
	}
	
	@Test
	public void testGetLogin() throws DBException {
		Activity receivedActivity = activityDao.get("test");
		Assert.assertEquals(activity, receivedActivity);
	}
	
	@Test
	public void testGetId() throws DBException {
		Activity receivedActivity = activityDao.get(activity.getId());
		Assert.assertEquals(activity, receivedActivity);
	}
	
	@Test
	public void testGetAll() throws DBException {
		List<Activity> activities = activityDao.getAll();
		Assert.assertNotEquals(0, activities.size());
	}
	
	@Test
	public void testShouldBeNullWhenIdNotExisting() throws DBException {
		Activity receivedActivity = activityDao.get(Integer.MAX_VALUE);
		Assert.assertEquals(null, receivedActivity);
	}
	
	@Test
	public void testGetActivitiesUserDoesNotHave() throws DBException {
		List<Activity> activities = activityDao.getAll();
		User user = new UserDAO().create(User.createUser("test", "test", "test"));
		List<Activity> notUserActivities = activityDao.getActivitiesUserDoesNotHave(user.getId());
		new UserDAO().delete(user);
		Assert.assertEquals(activities.size(), notUserActivities.size());
	}
	
	@Test
	public void testGetActivitiesByCategory() throws DBException {
		List<Activity> activitiesByCategory = activityDao.getActivitiesByCategory(categoryId);
		Assert.assertEquals(1, activitiesByCategory.size());
	}
	
	@Test
	public void testGetAllSortedByUsersCount() throws DBException {
		List<Activity> activities = activityDao.getAll();
		List<Activity> sortedActivities = activityDao.getAllSortedByUsersCount("ASC");
		Assert.assertNotEquals(activities.toString(), sortedActivities.toString());
	}
}