package com.excilys.webapp.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;

@Configurable
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private DataSource dataSource;
	@Autowired
	private UserDetailsService userDetailsService;
	private static final Logger LOGGER = LoggerFactory.getLogger(WebSecurityConfig.class);
		
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// Use HTTP Digest or Login form
		boolean isHttpDigest = false;

		http.csrf().disable();
		http
		  	.authorizeRequests()
		  		.antMatchers("/rest/**").permitAll()
				.antMatchers("/css/**").permitAll()
				.antMatchers("/fonts/**").permitAll()
				.antMatchers("/js/**").permitAll()	
				.antMatchers(WebappConfig.URL_DASHBOARD).access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
				.antMatchers("/**").access("hasRole('ROLE_ADMIN')");

		if (isHttpDigest) {
			LOGGER.info("Athentification: HTTP Digest");
			http
			.exceptionHandling()
				.authenticationEntryPoint(digestEntryPoint())
				.and()
			.addFilterAfter(digestAuthenticationFilter(digestEntryPoint()), DigestAuthenticationFilter.class);
		} else {
			LOGGER.info("Athentification: login form");
			http
				.formLogin()
				.loginPage("/login")
				.failureUrl("/login?error")
		        .usernameParameter("username") // input name where the username is retrieved in the login form
		        .successHandler(new URLAuthenticationSuccessHandler())
		        .permitAll();
		}
		// HTTPS
		http.requiresChannel().antMatchers(WebappConfig.URL_DASHBOARD).requiresSecure();
		http.requiresChannel().antMatchers(WebappConfig.URL_ADD_COMPUTER).requiresSecure();
		http.requiresChannel().antMatchers(WebappConfig.URL_EDIT_COMPUTER).requiresSecure();
	}
	
	/*
	 * Digest HTTP authentifications
	 */

	public DigestAuthenticationFilter digestAuthenticationFilter(DigestAuthenticationEntryPoint digestAuthenticationEntryPoint) throws Exception {
		DigestAuthenticationFilter digestAuthenticationFilter = new DigestAuthenticationFilter();
		digestAuthenticationFilter.setAuthenticationEntryPoint(digestEntryPoint());
		digestAuthenticationFilter.setUserDetailsService(userDetailsServiceBean());
		return digestAuthenticationFilter;
	}

	@Bean
	public DigestAuthenticationEntryPoint digestEntryPoint() {
		DigestAuthenticationEntryPoint digestAuthenticationEntryPoint = new DigestAuthenticationEntryPoint();
		digestAuthenticationEntryPoint.setKey("mykey"); // Key to generate the nonce token
		digestAuthenticationEntryPoint.setRealmName("Contacts Realm via Digest Authentication");
		return digestAuthenticationEntryPoint;
	}
	
	@Override
	@Bean
	public UserDetailsService userDetailsServiceBean() throws Exception {
	    return super.userDetailsServiceBean();
	}	

	/*
	 * Authentification with users store in DB and hard-coded users
	 */
	
	@Autowired
	public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
		boolean useJDBC = false;
		if (useJDBC) {
		// JDBC command
			LOGGER.info("Users datasource: JDBC");
			auth.jdbcAuthentication().dataSource(dataSource)
				.usersByUsernameQuery(
						"select username, password, enabled from user where username=?")
				.authoritiesByUsernameQuery(
						"select username, role from userRole where username=?");
		} else {
			LOGGER.info("Users datasource: DAO");
			// Use a custom user Service, no password encryption for now
			auth.userDetailsService(userDetailsService);
		}
	}	
	
	/**
	 * Hard code user, password and role
	 * @param auth AuthenticationManagerBuilder object
	 * @throws Exception
	 */
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//		LOGGER.info("Users hard-coded added!");
//		auth.inMemoryAuthentication().withUser("Pierre").password("123").roles("USER");
//		auth.inMemoryAuthentication().withUser("admin").password("123").roles("ADMIN");
	}
}
