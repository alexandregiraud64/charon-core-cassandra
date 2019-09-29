package com.agiraud.charon.core.cassandra.dao;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.agiraud.charon.core.dao.SessionService;
import com.agiraud.charon.core.dto.UserDetail;
import com.agiraud.charon.core.exception.NotRegisteredException;

@Service
public class SessionServiceImpl implements SessionService {
	/* ************************************************************************* */
	// PUBLIC FUNCTIONS
	/* ************************************************************************* */
	public Object getPrincipal(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = (auth != null) ? auth.getPrincipal() : null;
		return principal;
	}
	
	public Boolean isAuthenticate() {
		Object principal = getPrincipal();
		return (principal != null && principal instanceof UserDetails);
	}
	
	public UserDetail getPrincipalAsCustomUserPrincipal(){
		return (isAuthenticate()) ? (UserDetail)getPrincipal() : null;
	}
	
	public UserDetail getAuthenticatedUser() throws NotRegisteredException {
		if(!isAuthenticate()) throw new NotRegisteredException("No authenticated user found.");
		return getPrincipalAsCustomUserPrincipal();
	}
	
	public String getAuthenticatedUserLogin() {
		UserDetail principal = getPrincipalAsCustomUserPrincipal();
		return (principal == null) ? null : principal.getUsername();
	}
}
