package com.excilys.persistence.repository.interfaces;

import java.util.List;

import com.excilys.core.entity.User;

public interface UserDAO {

	List<User> findAll();

	void create(User obj);

	void updateByName(User obj);

	User findByUserName(String username);

	void delete(String username);

}