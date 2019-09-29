package com.agiraud.charon.core.cassandra.mapper;

import com.agiraud.charon.core.cassandra.entity.AuthorizationCodeByCode;
import com.agiraud.charon.core.dto.AuthorizationCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class AuthorizationCodeMapper {
	private AuthorizationCodeMapper() {}

	public static AuthorizationCodeByCode toAuthenticationCodeByCode(AuthorizationCode authorizationCode) {
		log.debug("[AuthorizationCodeMapper] AuthorizationCode to AuthorizationCodeByCode");
		if(authorizationCode == null) return null;
		
		AuthorizationCodeByCode authorizationCodeByAuthenticationId = new AuthorizationCodeByCode();
		authorizationCodeByAuthenticationId.setCode(authorizationCode.getCode());
		authorizationCodeByAuthenticationId.setClientId(authorizationCode.getClientId());
		authorizationCodeByAuthenticationId.setAuthentication(authorizationCode.getAuthentication());
		authorizationCodeByAuthenticationId.setExpiresAt(authorizationCode.getExpiresAt());
		return authorizationCodeByAuthenticationId;
	}

	public static AuthorizationCode toAuthenticationCode(AuthorizationCodeByCode authorizationCodeByAuthenticationId) {
		log.debug("[AuthorizationCodeMapper] AuthorizationodeByCode to AuthorizationCode");
		if(authorizationCodeByAuthenticationId == null) return null;
		
		AuthorizationCode authorizationCode = new AuthorizationCode();
		authorizationCode.setCode(authorizationCodeByAuthenticationId.getCode());
		authorizationCode.setClientId(authorizationCodeByAuthenticationId.getClientId());
		authorizationCode.setAuthentication(authorizationCodeByAuthenticationId.getAuthentication());
		authorizationCode.setExpiresAt(authorizationCodeByAuthenticationId.getExpiresAt());
		return authorizationCode;
	}
}
