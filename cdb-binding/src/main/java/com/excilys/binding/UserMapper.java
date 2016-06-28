package com.excilys.binding;

import com.excilys.core.dto.UserDTO;
import com.excilys.core.entity.User;

public class UserMapper {
	public static UserDTO toDTO(User user){
		return new UserDTO(
				user.getUsername(),
				user.getPassword(),
				String.valueOf(user.isEnabled()),
				user.getRole().toString());
	}
}
