package com.excilys.binding;

import java.util.ArrayList;
import java.util.List;

import com.excilys.core.dto.UserDTO;
import com.excilys.core.entity.User;
import com.excilys.core.entity.UserRole;

/**
 * Mapper around User and UserDTO object
 * @author pqwarlot
 *
 */
public class UserMapper {
	public static UserDTO toDTO(User user) {
		List<String> roles = new ArrayList<>();
		
		for (UserRole role: user.getUserRole()) {
			roles.add(role.toString());
		}
		
		return new UserDTO(
				user.getUsername(),
				user.getPassword(),
				String.valueOf(user.isEnabled()),
				roles
				);
	}
}
