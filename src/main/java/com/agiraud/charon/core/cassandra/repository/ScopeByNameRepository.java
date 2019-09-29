package com.agiraud.charon.core.cassandra.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.agiraud.charon.core.cassandra.entity.ScopeByName;

@Repository
public interface ScopeByNameRepository extends CassandraRepository<ScopeByName, String> {
	Optional<ScopeByName> findByName(String name);
}
