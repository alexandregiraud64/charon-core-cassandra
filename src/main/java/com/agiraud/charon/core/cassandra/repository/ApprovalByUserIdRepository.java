package com.agiraud.charon.core.cassandra.repository;

import java.util.Collection;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.agiraud.charon.core.cassandra.entity.ApprovalByUserId;

@Repository
public interface ApprovalByUserIdRepository extends CassandraRepository<ApprovalByUserId, String> {
	Collection<ApprovalByUserId> findByUserId(String userId);
	Collection<ApprovalByUserId> findAllByUserIdAndClientId(String userId, String clientId);
}
