package com.project.db.dao;

import java.util.List;

import com.project.db.DBException;

public interface DAO<T> {
	
	T create(T obj) throws DBException;

	void update(T obj) throws DBException;

	void delete(T obj) throws DBException;

	T get(String name) throws DBException;

	List<T> getAll() throws DBException;
}