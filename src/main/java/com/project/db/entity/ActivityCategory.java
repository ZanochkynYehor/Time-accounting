package com.project.db.entity;

import java.io.Serializable;

/**
 * The ActivityCategory entity class whose object characterize by id and category name.
 */
public class ActivityCategory implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	
	public ActivityCategory() {
		
	}
	
	public ActivityCategory(String name) {
		this.name = name;
	}

	public ActivityCategory(ActivityCategory category) {
		this.id = category.id;
		this.name = category.name;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public static ActivityCategory createCategory(String name) {
		return new ActivityCategory(name);
	}
	
	@Override
	public String toString() {
		return "ActivityCategory [id=" + id + ", name=" + name + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof ActivityCategory) {
			ActivityCategory category = (ActivityCategory) obj;
			return this.name.equals(category.name);
		}
		return false;
	}
	
	////////////////////////////////////
	
	private int countOfActivities;
	
	public int getCountOfActivities() {
		return countOfActivities;
	}

	public void setCountOfActivities(int countOfActivities) {
		this.countOfActivities = countOfActivities;
	}
}