package com.project.db.entity;

import java.io.Serializable;

/**
 * The UserActivity entity class whose object characterize by User object, Activity object, approved (means admin approve or not activity for user), 
 * start date and time of activity and finish time.
 */
public class UserActivity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private User user = new User();
	private Activity activity = new Activity();
	private String approved;
	private String startDateTime;
	private String finishTime;
	
	public UserActivity() {
		
	}

	public UserActivity(User user, Activity activity, String approved, String startDateTime, String finishTime) {
		this.user = user;
		this.activity = activity;
		this.approved = approved;
		this.startDateTime = startDateTime;
		this.finishTime = finishTime;
	}
	
	public UserActivity(int userId, int activityId, String approved, String startDateTime, String finishTime) {
		this.user.setId(userId);
		this.activity.setId(activityId);
		this.approved = approved;
		this.startDateTime = startDateTime;
		this.finishTime = finishTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Activity getActivity() {
		return activity;
	}

	public void setActivity(Activity activity) {
		this.activity = activity;
	}

	public String getApproved() {
		return approved;
	}

	public void setApproved(String approved) {
		this.approved = approved;
	}
	
	public String getStartDateTime() {
		return startDateTime;
	}

	public void setStartDateTime(String startDateTime) {
		this.startDateTime = startDateTime;
	}
	
	public String getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(String finishTime) {
		this.finishTime = finishTime;
	}

	@Override
	public String toString() {
		return "UserActivity [user=" + user + ", activity=" + activity + ", approved=" + approved + ", startDateTime="
				+ startDateTime + ", finishTime=" + finishTime + "]";
	}

	public static UserActivity createUserActivity(User user, Activity activity, String approved, String startDateTime, String finishTime) {
		return new UserActivity(user, activity, approved, startDateTime, finishTime);
	}
	
	public static UserActivity createUserActivity(int userId, int activityId, String approved, String startDateTime, String finishTime) {
		return new UserActivity(userId, activityId, approved, startDateTime, finishTime);
	}
}