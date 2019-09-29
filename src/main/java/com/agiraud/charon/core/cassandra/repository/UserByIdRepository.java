package com.agiraud.charon.core.cassandra.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.agiraud.charon.core.cassandra.entity.UserById;

@Repository
public interface UserByIdRepository extends CassandraRepository<UserById, UUID> {
	Optional<UserById> findByUserId(UUID userId);
}
