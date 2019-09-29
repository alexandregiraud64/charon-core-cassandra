package com.agiraud.charon.core.cassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.agiraud.charon.core.cassandra.entity.ClientByUserId;

import java.util.Collection;
import java.util.UUID;

@Repository
public interface ClientByUserIdRepository extends CassandraRepository<ClientByUserId, UUID> {
	Collection<ClientByUserId> findAllByUserId(UUID userId);
}
