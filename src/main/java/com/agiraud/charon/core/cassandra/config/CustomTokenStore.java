package com.agiraud.charon.core.cassandra.config;

import com.agiraud.charon.core.dao.AccessTokenService;
import com.agiraud.charon.core.dao.RefreshTokenService;
import com.agiraud.charon.core.dto.AccessToken;
import com.agiraud.charon.core.dto.RefreshToken;
import com.google.common.base.Function;
import com.google.common.base.Predicate;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.core.cql.WriteOptions;
import org.springframework.security.oauth2.common.ExpiringOAuth2RefreshToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.DefaultAuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;

import java.nio.ByteBuffer;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static com.google.common.collect.Collections2.filter;
import static com.google.common.collect.Collections2.transform;

@Service("tokenStore")
@Slf4j
public class CustomTokenStore implements TokenStore {
	/* ************************************************************************* */
	// ATTRIBUTES
	/* ************************************************************************* */
	@Autowired
	private AccessTokenService accessTokenService;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private AuthenticationKeyGenerator authenticationKeyGenerator;

	/* ************************************************************************* */
	// OVERRIDE
	/* ************************************************************************* */
	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		log.debug("readAuthentication for OAuth2AccessToken token");
		return readAuthentication(token.getValue());
	}

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		log.debug("readAuthentication for String token");
		AccessToken accessToken = accessTokenService.getByTokenId(token);
		if (accessToken != null) {
			try {
				return TokenSerializer.deserializeAuthentication(accessToken.getAuthentication());
			} catch (IllegalArgumentException e) {
				removeAccessToken(token);
			}
		}
		return null;
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		log.debug("Authentication name: " + authentication.toString());
		ByteBuffer bufferedToken = TokenSerializer.serializeAccessToken(token);
		ByteBuffer bufferedAuthentication = TokenSerializer.serializeAuthentication(authentication);
		String refreshToken = null;
		if (token.getRefreshToken() != null) {
			refreshToken = token.getRefreshToken().getValue();
		}

		if (readAccessToken(token.getValue()) != null) {
			removeAccessToken(token.getValue());
		}

		AccessToken accessToken = new AccessToken();
		accessToken.setTokenId(token.getValue());
		accessToken.setToken(bufferedToken);
		accessToken.setAuthentication(bufferedAuthentication);
		accessToken.setRefreshToken(refreshToken);
		accessToken.setClientId(authentication.getOAuth2Request().getClientId());
		accessToken.setUsername(authentication.isClientOnly() ? null : authentication.getName());
		accessToken.setAuthenticationId(authenticationKeyGenerator.extractKey(authentication));

		accessTokenService.create(accessToken);

		if (token.getRefreshToken() != null && token.getRefreshToken().getValue() != null) {
			storeRefreshToken(token.getRefreshToken(), authentication);
		}

	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenId) {
		log.debug("readAccessToken");
		AccessToken accessToken = accessTokenService.getByTokenId(tokenId);

		if (accessToken != null) {
			try {
				return TokenSerializer.deserializeAccessToken(accessToken.getToken());
			} catch (IllegalArgumentException e) {
				removeAccessToken(tokenId);
			}

		}
		return null;
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken token) {
		removeAccessToken(token.getValue());
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		log.debug("storeRefreshToken");
		ByteBuffer bufferedRefreshToken = TokenSerializer.serializeRefreshToken(refreshToken);
		ByteBuffer bufferedAuthentication = TokenSerializer.serializeAuthentication(authentication);
		final String tokenKey = refreshToken.getValue();
		WriteOptions.WriteOptionsBuilder writeOptionsBuilder = WriteOptions.builder();
		if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
			ExpiringOAuth2RefreshToken expiringRefreshToken = (ExpiringOAuth2RefreshToken) refreshToken;
			Date expiration = expiringRefreshToken.getExpiration();
			if (expiration != null) {
				int seconds = Long.valueOf((expiration.getTime() - System.currentTimeMillis()) / 1000L).intValue();
				writeOptionsBuilder.ttl(seconds);
			}
		}

		refreshTokenService.create(new RefreshToken(tokenKey, bufferedRefreshToken, bufferedAuthentication));
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenId) {
		RefreshToken refreshToken = refreshTokenService.getById(tokenId);
		if (refreshToken != null) {
			try {
				return TokenSerializer.deserializeRefreshToken(refreshToken.getToken());
			} catch (IllegalArgumentException e) {
				removeRefreshToken(tokenId);
			}
		}
		return null;
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		log.debug("readAuthenticationForRefreshToken");
		RefreshToken refreshToken = refreshTokenService.getById(token.getValue());
		if (refreshToken != null) {
			try {
				return TokenSerializer.deserializeAuthentication(refreshToken.getAuthentication());
			} catch (IllegalArgumentException e) {
				removeRefreshToken(token.getValue());
			}
		}
		return null;
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken token) {
		removeRefreshToken(token.getValue());
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		removeAccessTokenUsingRefreshToken(refreshToken.getValue());
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		log.debug("getAccessToken");
		String authenticationId = authenticationKeyGenerator.extractKey(authentication);
		AccessToken accessToken = accessTokenService.getByAuthenticationId(authenticationId);
		if (accessToken != null) {
			return TokenSerializer.deserializeAccessToken(accessToken.getToken());
		}
		return null;
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String username) {
		log.debug("findTokensByClientIdAndUsername");
		final List<AccessToken> oAuth2AccessTokens = accessTokenService.getByClientIdAndUsername(clientId, username);
		final Collection<AccessToken> noNullTokens = filter(oAuth2AccessTokens, byNotNulls());
		return transform(noNullTokens, toOAuth2AccessToken());
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		final List<AccessToken> oAuth2AccessTokens = accessTokenService.getByClientId(clientId);
		final Collection<AccessToken> noNullTokens = filter(oAuth2AccessTokens, byNotNulls());
		return transform(noNullTokens, toOAuth2AccessToken());
	}

	/* ************************************************************************* */
	// REMOVE FUNCTIONS
	/* ************************************************************************* */
	public void removeAccessToken(final String tokenValue) {
		AccessToken accessToken = accessTokenService.getByTokenId(tokenValue);
		if (accessToken != null) {
			accessTokenService.delete(accessToken);
		}
	}

	public void removeRefreshToken(String token) {
		RefreshToken refreshToken = refreshTokenService.getById(token);
		if (refreshToken != null) {
			refreshTokenService.delete(refreshToken);
		}
	}

	public void removeAccessTokenUsingRefreshToken(final String refreshToken) {
		AccessToken accessToken = accessTokenService.getByRefreshToken(refreshToken);
		if (accessToken != null) {
			accessTokenService.delete(accessToken);
		}
	}

	/* ************************************************************************* */
	// PREDICATES
	/* ************************************************************************* */
	private Predicate<AccessToken> byNotNulls() {
		return token -> token != null;
	}

	private Function<AccessToken, OAuth2AccessToken> toOAuth2AccessToken() {
		return token -> TokenSerializer.deserializeAccessToken(token.getToken());
	}

	/* ************************************************************************* */
	// BEANS
	/* ************************************************************************* */
	@Bean
	public AuthenticationKeyGenerator getAuthenticationKeyGenerator() {
		return new DefaultAuthenticationKeyGenerator();
	}
}
