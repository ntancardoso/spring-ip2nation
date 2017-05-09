package com.the9o.app.ip2nation.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//TODO Added for DEMO purpose only. Implement your own Authentication
		auth.inMemoryAuthentication().
				withUser("admin").password("password").roles("USER", "ADMIN");
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.httpBasic().and().authorizeRequests().
				antMatchers(HttpMethod.POST, "/countries").hasRole("ADMIN").
				antMatchers(HttpMethod.PUT, "/countries/**").hasRole("ADMIN").
				antMatchers(HttpMethod.PATCH, "/countries/**").hasRole("ADMIN").and().
				csrf().disable();
	}
}
