package com.the9o.app.ip2nation.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
	
	/**
	 * Shadow as User with Roles
	 * 
	 * @param username
	 * @param password
	 * @param roles
	 */
	public static void runAs(String username, String password, String... roles) {
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(username, password, AuthorityUtils.createAuthorityList(roles)));
	}

}
