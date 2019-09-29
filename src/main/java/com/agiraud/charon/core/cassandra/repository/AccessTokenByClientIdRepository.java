package com.agiraud.charon.core.cassandra.repository;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.agiraud.charon.core.cassandra.entity.AccessTokenByClientId;

import java.util.List;

@Repository
public interface AccessTokenByClientIdRepository extends CassandraRepository<AccessTokenByClientId, String> {
	List<AccessTokenByClientId> findByClientId(String clientId);
	List<AccessTokenByClientId> findByClientIdAndUsername(String clientId, String username);
}
