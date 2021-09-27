package com.project.db.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.project.db.DBException;
import com.project.db.entity.User;
import com.project.web.password.PasswordUtil;

public class UserDAOTest {

	private UserDAO userDao;
	private User user;
	
	@Before
	public void beforeTest() throws DBException {
		userDao = new UserDAO();
		String salt = PasswordUtil.generateSalt();
		String password = PasswordUtil.hashThePlainTextPassword("test", salt);
		user = userDao.create(User.createUser("test", password, salt));
	}
	
	@After
	public void afterTest() throws DBException {
		userDao = new UserDAO();
		userDao.delete(user);
	}
	
	@Test
	public void testCreate() throws DBException {
		Assert.assertNotEquals(0, user.getId());
	}
	
	@Test
	public void testUpdate() throws DBException {
		user.setLogin("test123");
		userDao.update(user);
		User updatedUser = userDao.get("test123");
		Assert.assertEquals(user, updatedUser);
	}
	
	@Test
	public void testGetLogin() throws DBException {
		User receivedUser = userDao.get("test");
		Assert.assertEquals(user, receivedUser);
	}
	
	@Test
	public void testGetId() throws DBException {
		User receivedUser = userDao.get(user.getId());
		Assert.assertEquals(user, receivedUser);
	}
	
	@Test
	public void testGetAll() throws DBException {
		List<User> users = userDao.getAll();
		Assert.assertNotEquals(0, users.size());
	}
	
	@Test
	public void testShouldBeNullWhenIdNotExisting() throws DBException {
		User receivedUser = userDao.get(Integer.MAX_VALUE);
		Assert.assertEquals(null, receivedUser);
	}
}