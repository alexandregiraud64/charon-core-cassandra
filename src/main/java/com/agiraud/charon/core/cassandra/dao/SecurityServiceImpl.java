package com.agiraud.charon.core.cassandra.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.agiraud.charon.core.dao.SecurityService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SecurityServiceImpl implements SecurityService{
	/* ************************************************************************* */
	// ATTRIBUTES
	/* ************************************************************************* */
    @Autowired
    private AuthenticationManager authenticationManager;

	/* ************************************************************************* */
	// OVERRIDE
	/* ************************************************************************* */

    @Override
    public String findLoggedInUsername() {
    	log.debug("findLoggedInUsername");
        Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
        if (userDetails instanceof UserDetails) {
            return ((UserDetails)userDetails).getUsername();
        }

        return null;
    }

    @Override
    public void autoLogin(String username, String password) {
    	log.debug("autoLogin");
    	UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);
    	Authentication authenticatedUser = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
    }
}
