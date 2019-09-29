package com.agiraud.charon.core.cassandra.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.agiraud.charon.core.cassandra.entity.RefreshTokenByTokenId;

@Repository
public interface RefreshTokenByTokenIdRepository extends CassandraRepository<RefreshTokenByTokenId, String> {
	Optional<RefreshTokenByTokenId> findByTokenId(String tokenId);
}
