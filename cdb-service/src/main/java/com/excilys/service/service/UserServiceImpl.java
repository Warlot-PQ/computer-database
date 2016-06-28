package com.excilys.service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.core.dto.UserDTO;
import com.excilys.core.entity.User;
import com.excilys.persistence.repository.interfaces.UserDAO;
import com.excilys.service.service.interfaces.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;

	@Override
	public List<UserDTO> getAll() {
		return userDAO.findAll();
	}

	@Override
	public void create(User obj) {
		if (obj == null) return;
		userDAO.create(obj);
	}

	@Override
	public void updateByName(User obj) {
		if (obj == null) return;
		userDAO.updateByName(obj);
	}

	@Override
	public Optional<User> get(String username) {
		if (username == null) return null;
		User user = userDAO.findByUserName(username);
		
		if (user == null) {
			return Optional.empty();
		}
		return Optional.of(user);
	}

	@Override
	public void delete(String username) {
		if (username == null) return;
		userDAO.delete(username);
	}

}
