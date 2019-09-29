package com.agiraud.charon.core.cassandra.mapper;

import java.util.LinkedList;
import java.util.List;

import com.agiraud.charon.core.cassandra.entity.AccessTokenByAuthenticationId;
import com.agiraud.charon.core.cassandra.entity.AccessTokenByClientId;
import com.agiraud.charon.core.cassandra.entity.AccessTokenByRefreshToken;
import com.agiraud.charon.core.cassandra.entity.AccessTokenByTokenId;
import com.agiraud.charon.core.dto.AccessToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class AccessTokenMapper {
	private AccessTokenMapper() {}

	public static AccessTokenByAuthenticationId toAccessTokenByAuthenticationId(AccessToken accessToken) {
		log.debug("[AccessTokenMapper] AccessToken to AccessTokenByAuthenticationId");
		if(accessToken == null) return null;
		
		AccessTokenByAuthenticationId accessTokenByAuthenticationId = new AccessTokenByAuthenticationId();
		accessTokenByAuthenticationId.setTokenId(accessToken.getTokenId());
		accessTokenByAuthenticationId.setToken(accessToken.getToken());
		accessTokenByAuthenticationId.setAuthenticationId(accessToken.getAuthenticationId());
		accessTokenByAuthenticationId.setUsername(accessToken.getUsername());
		accessTokenByAuthenticationId.setClientId(accessToken.getClientId());
		accessTokenByAuthenticationId.setAuthentication(accessToken.getAuthentication());
		accessTokenByAuthenticationId.setRefreshToken(accessToken.getRefreshToken());
		return accessTokenByAuthenticationId;
	}

	public static AccessTokenByClientId toAccessTokenByClientId(AccessToken accessToken) {
		log.debug("[AccessTokenMapper] AccessToken to AccessTokenByClientId");
		if(accessToken == null) return null;
		
		AccessTokenByClientId accessTokenByClientId = new AccessTokenByClientId();
		accessTokenByClientId.setTokenId(accessToken.getTokenId());
		accessTokenByClientId.setToken(accessToken.getToken());
		accessTokenByClientId.setClientId(accessToken.getClientId());
		accessTokenByClientId.setUsername(accessToken.getUsername());
		accessTokenByClientId.setClientId(accessToken.getClientId());
		accessTokenByClientId.setAuthentication(accessToken.getAuthentication());
		accessTokenByClientId.setRefreshToken(accessToken.getRefreshToken());
		return accessTokenByClientId;
	}

	public static AccessTokenByRefreshToken toAccessTokenByRefreshToken(AccessToken accessToken) {
		log.debug("[AccessTokenMapper] AccessToken to AccessTokenByRefreshToken");
		if(accessToken == null) return null;
		
		AccessTokenByRefreshToken accessTokenByRefreshToken = new AccessTokenByRefreshToken();
		accessTokenByRefreshToken.setTokenId(accessToken.getTokenId());
		accessTokenByRefreshToken.setToken(accessToken.getToken());
		accessTokenByRefreshToken.setRefreshToken(accessToken.getRefreshToken());
		accessTokenByRefreshToken.setUsername(accessToken.getUsername());
		accessTokenByRefreshToken.setClientId(accessToken.getClientId());
		accessTokenByRefreshToken.setAuthentication(accessToken.getAuthentication());
		accessTokenByRefreshToken.setRefreshToken(accessToken.getRefreshToken());
		return accessTokenByRefreshToken;
	}

	public static AccessTokenByTokenId toAccessTokenByTokenId(AccessToken accessToken) {
		log.debug("[AccessTokenMapper] AccessToken to AccessTokenByTokenId");
		if(accessToken == null) return null;
		
		AccessTokenByTokenId accessTokenByTokenId = new AccessTokenByTokenId();
		accessTokenByTokenId.setTokenId(accessToken.getTokenId());
		accessTokenByTokenId.setToken(accessToken.getToken());
		accessTokenByTokenId.setTokenId(accessToken.getTokenId());
		accessTokenByTokenId.setUsername(accessToken.getUsername());
		accessTokenByTokenId.setClientId(accessToken.getClientId());
		accessTokenByTokenId.setAuthentication(accessToken.getAuthentication());
		accessTokenByTokenId.setRefreshToken(accessToken.getRefreshToken());
		return accessTokenByTokenId;
	}
	
	public static AccessToken toAccessToken(AccessTokenByAuthenticationId accessTokenByAuthenticationId) {
		log.debug("[AccessTokenMapper] AccessTokenByAuthenticationId to AccessToken");
		if(accessTokenByAuthenticationId == null) return null;
		
		AccessToken accessToken = new AccessToken();
		accessToken.setTokenId(accessTokenByAuthenticationId.getTokenId());
		accessToken.setToken(accessTokenByAuthenticationId.getToken());
		accessToken.setAuthenticationId(accessTokenByAuthenticationId.getAuthenticationId());
		accessToken.setUsername(accessTokenByAuthenticationId.getUsername());
		accessToken.setClientId(accessTokenByAuthenticationId.getClientId());
		accessToken.setAuthentication(accessTokenByAuthenticationId.getAuthentication());
		accessToken.setRefreshToken(accessTokenByAuthenticationId.getRefreshToken());
		return accessToken;
	}
	
	public static AccessToken toAccessToken(AccessTokenByClientId accessTokenByClientId) {
		log.debug("[AccessTokenMapper] AccessTokenByClientId to AccessToken");
		if(accessTokenByClientId == null) return null;
		
		AccessToken accessToken = new AccessToken();
		accessToken.setTokenId(accessTokenByClientId.getTokenId());
		accessToken.setToken(accessTokenByClientId.getToken());
		accessToken.setClientId(accessTokenByClientId.getClientId());
		accessToken.setUsername(accessTokenByClientId.getUsername());
		accessToken.setClientId(accessTokenByClientId.getClientId());
		accessToken.setAuthentication(accessTokenByClientId.getAuthentication());
		accessToken.setRefreshToken(accessTokenByClientId.getRefreshToken());
		return accessToken;
	}
	
	public static AccessToken toAccessToken(AccessTokenByRefreshToken accessTokenByRefreshToken) {
		log.debug("[AccessTokenMapper] AccessTokenByRefreshToken to AccessToken");
		if(accessTokenByRefreshToken == null) return null;
		
		AccessToken accessToken = new AccessToken();
		accessToken.setTokenId(accessTokenByRefreshToken.getTokenId());
		accessToken.setToken(accessTokenByRefreshToken.getToken());
		accessToken.setRefreshToken(accessTokenByRefreshToken.getRefreshToken());
		accessToken.setUsername(accessTokenByRefreshToken.getUsername());
		accessToken.setClientId(accessTokenByRefreshToken.getClientId());
		accessToken.setAuthentication(accessTokenByRefreshToken.getAuthentication());
		accessToken.setRefreshToken(accessTokenByRefreshToken.getRefreshToken());
		return accessToken;
	}
	
	public static AccessToken toAccessToken(AccessTokenByTokenId accessTokenByTokenId) {
		log.debug("[AccessTokenMapper] AccessTokenByTokenId to AccessToken");
		if(accessTokenByTokenId == null) return null;
		
		AccessToken accessToken = new AccessToken();
		accessToken.setTokenId(accessTokenByTokenId.getTokenId());
		accessToken.setToken(accessTokenByTokenId.getToken());
		accessToken.setTokenId(accessTokenByTokenId.getTokenId());
		accessToken.setUsername(accessTokenByTokenId.getUsername());
		accessToken.setClientId(accessTokenByTokenId.getClientId());
		accessToken.setAuthentication(accessTokenByTokenId.getAuthentication());
		accessToken.setRefreshToken(accessTokenByTokenId.getRefreshToken());
		return accessToken;
	}
	
	public static List<AccessToken> toAccessToken(List<AccessTokenByClientId> accessTokensByClientId){
		List<AccessToken> accessTokens = new LinkedList<AccessToken>();
		for (AccessTokenByClientId accessTokenByClientId : accessTokensByClientId) {
			accessTokens.add(toAccessToken(accessTokenByClientId));
		}
		return accessTokens;
	}
}
