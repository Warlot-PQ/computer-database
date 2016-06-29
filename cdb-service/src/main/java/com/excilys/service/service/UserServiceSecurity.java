package com.excilys.service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.excilys.core.entity.User;
import com.excilys.persistence.repository.interfaces.UserDAO;

/**
 * Get spring security UserDetails from DAO to identify user from data stored in DB.
 * @author pqwarlot
 *
 */
@Service("userDetailsService")
public class UserServiceSecurity implements UserDetailsService{
	@Autowired
	private UserDAO userDAO;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userDAO.findByUserName(username);
		if (user == null) {	
			throw new UsernameNotFoundException(String.format("User with username=%s was not found", username));
		}
		return new org.springframework.security.core.userdetails.User(
				user.getUsername(), 
				user.getPassword(), 
				AuthorityUtils.createAuthorityList(user.getUserRole().toString()));
	}		
	
}
