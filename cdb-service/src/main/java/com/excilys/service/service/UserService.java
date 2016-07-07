package com.excilys.service.service;

import java.util.List;

import com.excilys.core.dto.UserDTO;
import com.excilys.core.entity.User;

/**
 * Specific services to user entities
 * @author pqwarlot
 *
 */
public interface UserService {	
	List<UserDTO> getAll();

	void create(User obj);

	void updateByName(User obj);

	UserDTO get(String username);

	void delete(String username);
}
