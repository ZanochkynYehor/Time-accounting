package com.project.db.entity;

import java.io.Serializable;

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

	@Override
	public String toString() {
		return "ActivityCategory [id=" + id + ", name=" + name + "]";
	}
	
	public static ActivityCategory createCategory(String name) {
		return new ActivityCategory(name);
	}
}
