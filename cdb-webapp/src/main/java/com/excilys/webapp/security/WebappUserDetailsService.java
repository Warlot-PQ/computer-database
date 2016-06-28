package com.excilys.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.core.entity.User;
import com.excilys.service.service.interfaces.UserService;

@Service("userDetailsService")
public class WebappUserDetailsService implements UserDetailsService {
	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.get(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User with username=%s was not found", username)));
		return new CurrentUser(user);
	}
	
	
}
