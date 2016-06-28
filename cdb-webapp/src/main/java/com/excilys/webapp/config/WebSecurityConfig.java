package com.excilys.webapp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configurable
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
	  http.authorizeRequests()
		.antMatchers("/css/**").permitAll()
		.antMatchers("/fonts/**").permitAll()
		.antMatchers("/js/**").permitAll()		
		.antMatchers("/Dashboard").access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
		.antMatchers("/**").access("hasRole('ROLE_ADMIN')")
		.and().formLogin();	
	  
	  http.formLogin().successHandler(new UrlAuthenticationSuccessHandler());
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
	  auth.inMemoryAuthentication().withUser("Pierre").password("123").roles("USER");
	  auth.inMemoryAuthentication().withUser("admin").password("123").roles("ADMIN");
	}
}
