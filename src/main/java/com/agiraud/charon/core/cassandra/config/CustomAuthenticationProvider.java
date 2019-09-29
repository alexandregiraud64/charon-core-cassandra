package com.agiraud.charon.core.cassandra.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.stereotype.Service;

import com.agiraud.charon.core.dao.UserService;
import com.agiraud.charon.core.dto.UserDetail;
import com.agiraud.charon.core.exception.BadCredentialsException;

@Service("authenticationProvider")
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {
	/* ************************************************************************* */
	// ATTRIBUTES
	/* ************************************************************************* */
	@Autowired
	private UserService userService;

	/* ************************************************************************* */
	// OVERRIDE
	/* ************************************************************************* */
	@Override
	public Authentication authenticate(Authentication authentication) throws BadCredentialsException {
		log.debug("Authenticate in CustomAuthenticationProvider");
		
		if (authentication == null) {
			throw new InvalidTokenException("Invalid token (token not found)");
		}
		
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		if (username == "" || password == "") {
			throw new BadCredentialsException("Invalid username and/or password");
		}

		UserDetail user = userService.getUserDetailByUsername(username);

		if (user == null) {
			throw new BadCredentialsException("Invalid username and/or password");
		} else if (!user.isEnabled()) {
			throw new BadCredentialsException("your account is blocked");
		}

		if (isPasswordMatched(password, user.getPassword())) {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
			return authenticationToken;
		} else {
			throw new BadCredentialsException("Invalid username and/or password");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		log.debug("Get supports for authentication (UsernamePasswordAuthenticationToken)");
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	/* ************************************************************************* */
	// STATIC
	/* ************************************************************************* */
	public static boolean isPasswordMatched(final String plainPassword, final String bCryptHash) {
		return new BCryptPasswordEncoder().matches(plainPassword, bCryptHash);
	}
}
