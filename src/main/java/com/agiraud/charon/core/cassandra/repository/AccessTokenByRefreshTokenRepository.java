package com.agiraud.charon.core.cassandra.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.agiraud.charon.core.cassandra.entity.AccessTokenByRefreshToken;

@Repository
public interface AccessTokenByRefreshTokenRepository extends CassandraRepository<AccessTokenByRefreshToken, String> {
	Optional<AccessTokenByRefreshToken> findByRefreshToken(String refreshToken);
}
