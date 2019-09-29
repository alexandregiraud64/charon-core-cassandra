package com.agiraud.charon.core.cassandra.config;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.stereotype.Service;

import com.agiraud.charon.core.dao.AuthorizationCodeService;
import com.agiraud.charon.core.dto.AuthorizationCode;

import lombok.extern.slf4j.Slf4j;

@Service("authorizationCodeServices")
@Slf4j
public class CustomAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {
	/* ************************************************************************* */
	// ATTRIBUTES
	/* ************************************************************************* */
	@Autowired
	private AuthorizationCodeService service;

	/* ************************************************************************* */
	// OVERRIDE
	/* ************************************************************************* */
	@Override
	protected void store(String code, OAuth2Authentication authentication) {
		log.debug("Storing authorization code "+code);
		AuthorizationCode authenticationCode = new AuthorizationCode();
		authenticationCode.setClientId(authentication.getOAuth2Request().getClientId());
		authenticationCode.setAuthentication(TokenSerializer.serializeAuthentication(authentication));
		authenticationCode.setCode(code);

		Calendar expiresAt = Calendar.getInstance();
		expiresAt.add(Calendar.HOUR_OF_DAY, 24);
		authenticationCode.setExpiresAt(expiresAt.getTime());
		service.create(authenticationCode);
	}

	@Override
	protected OAuth2Authentication remove(String code) {
		log.debug("Removing authorization code "+code);
		AuthorizationCode authenticationCode = service.deleteByCode(code);
		if(authenticationCode == null) return null;
		return TokenSerializer.deserializeAuthentication(authenticationCode.getAuthentication());
	}

}
