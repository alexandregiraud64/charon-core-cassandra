package com.agiraud.charon.core.cassandra.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.agiraud.charon.core.cassandra.entity.AuthorizationCodeByCode;

@Repository
public interface AuthorizationCodeByCodeRepository extends CassandraRepository<AuthorizationCodeByCode, String> {
	Optional<AuthorizationCodeByCode> findByCode(String code);
}
