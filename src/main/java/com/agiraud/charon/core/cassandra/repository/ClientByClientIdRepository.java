package com.agiraud.charon.core.cassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.agiraud.charon.core.cassandra.entity.ClientByClientId;

import java.util.Optional;

@Repository
public interface ClientByClientIdRepository extends CassandraRepository<ClientByClientId, String> {
	Optional<ClientByClientId> findByClientId(String clientId);
}
