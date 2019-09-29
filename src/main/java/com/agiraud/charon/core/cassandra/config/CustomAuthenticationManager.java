package com.agiraud.charon.core.cassandra.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service("authenticationManager")
@Slf4j
public class CustomAuthenticationManager implements AuthenticationManager {
	/* ************************************************************************* */
	// ATTRIBUTES
	/* ************************************************************************* */
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	/* ************************************************************************* */
	// OVERRIDE
	/* ************************************************************************* */
	/**
	 * Authenticates current authentication.
	 * @param authentication
	 * @return
	 */
	@Override
	public Authentication authenticate(Authentication authentication) {
		log.debug("Authentication in CustomAuthenticationManager");
		return authenticationProvider.authenticate(authentication);
	}
}
