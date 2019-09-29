package com.agiraud.charon.core.cassandra.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.agiraud.charon.core.cassandra.entity.AccessTokenByTokenId;

@Repository
public interface AccessTokenByTokenIdRepository extends CassandraRepository<AccessTokenByTokenId, String> {
	Optional<AccessTokenByTokenId> findByTokenId(String tokenId);
}
