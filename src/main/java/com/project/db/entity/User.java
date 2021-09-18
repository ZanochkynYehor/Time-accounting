package com.project.db.entity;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String login;
	private transient String password;
	private int roleId;
	private String role;

	public User() {

	}

	public User(String login, String password, int roleId, String role) {
		this.login = login;
		this.password = password;
		this.roleId = roleId;
		this.role = role;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", login=" + login + ", role=" + role + "]";
	}

	public static User createUser(String login, String password) {
		return new User(login, password, 2, "user");
	}
}