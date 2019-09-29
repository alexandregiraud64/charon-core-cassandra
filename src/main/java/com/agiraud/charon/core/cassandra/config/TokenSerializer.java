package com.agiraud.charon.core.cassandra.config;

import java.nio.ByteBuffer;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

public class TokenSerializer {
	
	private TokenSerializer() {}
	
	static public ByteBuffer serializeAccessToken(OAuth2AccessToken token) {
		return ByteBuffer.wrap(SerializationUtils.serialize(token));
	}

	static public ByteBuffer serializeRefreshToken(OAuth2RefreshToken token) {
		return ByteBuffer.wrap(SerializationUtils.serialize(token));
	}

	static public ByteBuffer serializeAuthentication(OAuth2Authentication authentication) {
		return ByteBuffer.wrap(SerializationUtils.serialize(authentication));
	}

	static public OAuth2AccessToken deserializeAccessToken(ByteBuffer token) {
		byte[] bytes = new byte[token.remaining()];
		token.get(bytes);
		return SerializationUtils.deserialize(bytes);
	}

	static public OAuth2RefreshToken deserializeRefreshToken(ByteBuffer token) {
		byte[] bytes = new byte[token.remaining()];
		token.get(bytes);
		return SerializationUtils.deserialize(bytes);
	}

	static public OAuth2Authentication deserializeAuthentication(ByteBuffer authentication) {
		byte[] bytes = new byte[authentication.remaining()];
		authentication.get(bytes);
		return SerializationUtils.deserialize(bytes);
	}

}
