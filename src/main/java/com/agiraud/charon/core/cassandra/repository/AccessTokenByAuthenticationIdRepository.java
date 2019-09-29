package com.agiraud.charon.core.cassandra.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.agiraud.charon.core.cassandra.entity.AccessTokenByAuthenticationId;

@Repository
public interface AccessTokenByAuthenticationIdRepository extends CassandraRepository<AccessTokenByAuthenticationId, String> {
	Optional<AccessTokenByAuthenticationId> findByAuthenticationId(String authenticationId);
}
