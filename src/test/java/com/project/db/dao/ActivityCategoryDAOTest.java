package com.project.db.dao;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.project.db.DBException;
import com.project.db.entity.ActivityCategory;

public class ActivityCategoryDAOTest {

	private ActivityCategoryDAO categoryDao;
	private ActivityCategory category;
	
	@Before
	public void beforeTest() throws DBException {
		categoryDao = new ActivityCategoryDAO();
		category = categoryDao.create(ActivityCategory.createCategory("test"));
	}
	
	@After
	public void afterTest() throws DBException {
		categoryDao = new ActivityCategoryDAO();
		categoryDao.delete(category);
	}
	
	@Test
	public void testCreate() throws DBException {
		Assert.assertNotEquals(0, category.getId());
	}
	
	@Test
	public void testUpdate() throws DBException {
		category.setName("test123");
		categoryDao.update(category);
		ActivityCategory updatedCategory = categoryDao.get("test123");
		Assert.assertEquals(category, updatedCategory);
	}
	
	@Test
	public void testGetLogin() throws DBException {
		ActivityCategory receivedCategory = categoryDao.get("test");
		Assert.assertEquals(category, receivedCategory);
	}
	
	@Test
	public void testGetId() throws DBException {
		ActivityCategory receivedCategory = categoryDao.get(category.getId());
		Assert.assertEquals(category, receivedCategory);
	}
	
	@Test
	public void testGetAll() throws DBException {
		List<ActivityCategory> categories = categoryDao.getAll();
		Assert.assertNotEquals(0, categories.size());
	}
	
	@Test
	public void testShouldBeNullWhenIdNotExisting() throws DBException {
		ActivityCategory receivedCategory = categoryDao.get(Integer.MAX_VALUE);
		Assert.assertEquals(null, receivedCategory);
	}
}