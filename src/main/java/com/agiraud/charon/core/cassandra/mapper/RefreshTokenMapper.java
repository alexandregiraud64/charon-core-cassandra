package com.agiraud.charon.core.cassandra.mapper;

import com.agiraud.charon.core.cassandra.entity.RefreshTokenByTokenId;
import com.agiraud.charon.core.dto.RefreshToken;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class RefreshTokenMapper {
	private RefreshTokenMapper() {}
	
	public static RefreshTokenByTokenId toRefreshTokenByTokenId(RefreshToken refreshToken) {
		log.debug("[RefreshTokenMapper] RefreshToken to RefreshTokenByTokenId");
		if(refreshToken == null) return null;
		
		RefreshTokenByTokenId refreshTokenByTokenId = new RefreshTokenByTokenId();
		refreshTokenByTokenId.setTokenId(refreshToken.getTokenId());
		refreshTokenByTokenId.setToken(refreshToken.getToken());
		refreshTokenByTokenId.setAuthentication(refreshToken.getAuthentication());
		return refreshTokenByTokenId;
	}

	public static RefreshToken toRefreshToken(RefreshTokenByTokenId refreshTokenByTokenId) {
		log.debug("[RefreshTokenMapper] RefreshTokenByTokenId to RefreshToken");
		if(refreshTokenByTokenId == null) return null;
		
		RefreshToken refreshToken = new RefreshToken();
		refreshToken.setTokenId(refreshTokenByTokenId.getTokenId());
		refreshToken.setToken(refreshTokenByTokenId.getToken());
		refreshToken.setAuthentication(refreshTokenByTokenId.getAuthentication());
		return refreshToken;
	}
}
