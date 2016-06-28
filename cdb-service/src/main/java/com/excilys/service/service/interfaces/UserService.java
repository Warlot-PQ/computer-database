package com.excilys.service.service.interfaces;

import java.util.List;
import java.util.Optional;

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

	Optional<User> get(String username);

	void delete(String username);
}
