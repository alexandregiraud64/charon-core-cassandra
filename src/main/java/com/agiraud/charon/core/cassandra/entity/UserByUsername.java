package com.agiraud.charon.core.cassandra.entity;

import lombok.Data;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Table("user_by_username")
public class UserByUsername {
	@PrimaryKeyColumn(name = "username", ordinal = 0, type = PrimaryKeyType.PARTITIONED, ordering = Ordering.DESCENDING)
	private String username;

	@PrimaryKeyColumn(name = "user_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
	private UUID userId;

	@Column("email_address")
	private String emailAddress;

	@Column("full_name")
	private String fullName;

	@Column("profile_image")
	private String profileImage;

	@Column("password")
	private String password;

	@Column("enabled")
	private boolean enabled = false;

	@Column("phone_number")
	private String phoneNumber;

	@Column("roles")
	private List<String> roles = new ArrayList<>();
}
