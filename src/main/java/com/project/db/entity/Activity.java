package com.project.db.entity;

import java.io.Serializable;
import java.util.Comparator;

/**
 * The Activity entity class whose object characterize by id, activity name, category id and category name
 * and also has some comparators to sort a List of activities.
 */
public class Activity implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String name;
	private int categoryId;
	private String category;

	public Activity() {

	}
	
	public Activity(Activity activity) {
		this.id = activity.id;
		this.name = activity.name;
		this.categoryId = activity.categoryId;
		this.category = activity.category;
	}

	public Activity(String name, String category) {
		this.name = name;
		this.category = category;
	}

	public Activity(String name, int categoryId) {
		this.name = name;
		this.categoryId = categoryId;
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

	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public static Activity createActivity(String name, String category) {
		return new Activity(name, category);
	}
	
	public static Activity createActivity(String name, int categoryId) {
		return new Activity(name, categoryId);
	}
	
	@Override
	public String toString() {
		return "Activity [id=" + id + ", name=" + name + ", category=" + category + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Activity) {
			Activity activity = (Activity) obj;
			return this.name.equals(activity.name);
		}
		return false;
	}
	
	public static Comparator<Activity> IdComparator = new Comparator<Activity>() {

		public int compare(Activity a1, Activity a2) {
			int id1 = a1.getId();
			int id2 = a2.getId();
			return id1-id2;
		}
	};
	
	public static Comparator<Activity> NameAscComparator = new Comparator<Activity>() {

		public int compare(Activity a1, Activity a2) {
			String name1 = a1.getName().toUpperCase();
			String name2 = a2.getName().toUpperCase();
			return name1.compareTo(name2);
		}
	};
	
	public static Comparator<Activity> NameDescComparator = new Comparator<Activity>() {

		public int compare(Activity a1, Activity a2) {
			String name1 = a1.getName().toUpperCase();
			String name2 = a2.getName().toUpperCase();
			return name2.compareTo(name1);
		}
	};
	
	public static Comparator<Activity> CategoryAscComparator = new Comparator<Activity>() {

		public int compare(Activity a1, Activity a2) {
			String category1 = a1.getCategory().toUpperCase();
			String category2 = a2.getCategory().toUpperCase();
			return category1.compareTo(category2);
		}
	};
	
	public static Comparator<Activity> CategoryDescComparator = new Comparator<Activity>() {

		public int compare(Activity a1, Activity a2) {
			String category1 = a1.getCategory().toUpperCase();
			String category2 = a2.getCategory().toUpperCase();
			return category2.compareTo(category1);
		}
	};
}