package com.agiraud.charon.core.cassandra.repository;

import java.util.Optional;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.agiraud.charon.core.cassandra.entity.UserByEmail;

@Repository
public interface UserByEmailRepository extends CassandraRepository<UserByEmail, String> {
	Optional<UserByEmail> findByEmailAddress(String emailAddress);
}
