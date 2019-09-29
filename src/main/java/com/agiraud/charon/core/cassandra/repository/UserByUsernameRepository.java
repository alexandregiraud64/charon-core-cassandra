package com.agiraud.charon.core.cassandra.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.agiraud.charon.core.cassandra.entity.UserByUsername;

@Repository
public interface UserByUsernameRepository extends CassandraRepository<UserByUsername, String> {
	Optional<UserByUsername> findByUsername(String username);
}
