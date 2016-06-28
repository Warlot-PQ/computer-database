package com.excilys.webapp.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configurable
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.
	  	authorizeRequests()
			.antMatchers("/css/**").permitAll()
			.antMatchers("/fonts/**").permitAll()
			.antMatchers("/js/**").permitAll()	
			.antMatchers("/Dashboard").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
			.antMatchers("/**").access("hasRole('ROLE_ADMIN')")
			.and()
		.formLogin()
			.loginPage("/login")
			.failureUrl("/login?error")
            .usernameParameter("username") // input name where the id is retrieved in the login form
			.permitAll()
			.and()
		.logout()
			.and()
		.rememberMe();
	  
	  http.formLogin().successHandler(new UrlAuthenticationSuccessHandler());
	}
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		// JDBC command
//		auth.jdbcAuthentication().dataSource(dataSource)
//			.usersByUsernameQuery(
//					"select username, password, enabled from user where username=?")
//			.authoritiesByUsernameQuery(
//					"select username, role from user where username=?");
		
		// Use a custom user Service, no password encryption for now
		auth.userDetailsService(userDetailsService);
	}	
	
	/**
	 * Hard code user, password and role
	 * @param auth AuthenticationManagerBuilder object
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//	  auth.inMemoryAuthentication().withUser("Pierre").password("123").roles("USER");
//	  auth.inMemoryAuthentication().withUser("admin").password("123").roles("ADMIN");
	}
}
