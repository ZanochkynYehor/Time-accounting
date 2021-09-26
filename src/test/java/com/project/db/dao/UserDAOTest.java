package com.project.db.dao;

import org.junit.Assert;
import org.junit.Test;

import com.project.db.DBException;
import com.project.db.entity.User;

public class UserDAOTest {

	@Test
	public void test() throws DBException {
		UserDAO userDao = new UserDAO();
		User user = userDao.get(1);
		Assert.assertEquals("admin", user.getLogin());
	}
}
