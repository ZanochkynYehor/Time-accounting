package com.project.db.dao;

import java.util.ArrayList;
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
import com.project.db.entity.UserActivity;

public class UserActivityDAOTest {

	private UserActivityDAO userActivityDao;
	private UserActivity userActivity;
	private static ActivityCategory category;
	private static int categoryId;
	private static Activity activity;
	private static int activityId;
	private static User user;
	private static String[] activityIds;

	@BeforeClass
	public static void beforeClass() throws DBException {
		category = new ActivityCategoryDAO().create(ActivityCategory.createCategory("test"));
		categoryId = category.getId();
		activity = new ActivityDAO().create(Activity.createActivity("test", categoryId));
		activityId = activity.getId();
		activityIds = new String[] { Integer.toString(activityId) };
		user = new UserDAO().create(User.createUser("test", "test", "test"));
	}

	@AfterClass
	public static void afterClass() throws DBException {
		new ActivityDAO().delete(activity);
		new ActivityCategoryDAO().delete(category);
		new UserDAO().delete(user);
	}

	@Before
	public void beforeTest() throws DBException {
		userActivityDao = new UserActivityDAO();
		userActivityDao.create(user, activityIds);
	}

	@After
	public void afterTest() throws DBException {
		userActivityDao = new UserActivityDAO();
		userActivityDao.delete(user.getId(), Integer.parseInt(activityIds[0]));
	}

	@Test
	public void testCreate() throws DBException {
		userActivity = userActivityDao.getUserActivity(user, activityId);
		Assert.assertNotEquals(0, userActivity.getUser().getId());
		Assert.assertNotEquals(0, userActivity.getActivity().getId());
	}
	
	@Test(expected = DBException.class)
	public void testShouldRollbackAndThrowExceptionWhenWrongActivityId() throws DBException {
		userActivityDao.create(user, new String[] {"12345"});
	}

	@Test
	public void testUpdate() throws DBException {
		userActivity = userActivityDao.getUserActivity(user, activityId);
		userActivity.setApproved("Yes");
		userActivityDao.update(userActivity);
		UserActivity updatedUserActivity = userActivityDao.getUserActivity(user, activityId);
		Assert.assertEquals("Yes", updatedUserActivity.getApproved());
	}

	@Test
	public void testGetAllUserActivities() throws DBException {
		List<UserActivity> userActivities = userActivityDao.getAllUserActivities(user);
		Assert.assertEquals(1, userActivities.size());
	}

	@Test
	public void testGetNotApprovedActivities() throws DBException {
		List<User> users = new UserDAO().getAll();
		List<UserActivity> userActivities = new ArrayList<>();
		for (User user : users) {
			userActivities.addAll(userActivityDao.getAllUserActivities(user));
		}
		int notApproved = 0;
		for (UserActivity userActivity : userActivities) {
			if (userActivity.getApproved().equals("No")) {
				notApproved++;
			}
		}
		List<UserActivity> notApprovedUserActivities = userActivityDao.getNotApprovedActivities();
		Assert.assertEquals(notApproved, notApprovedUserActivities.size());
	}
}