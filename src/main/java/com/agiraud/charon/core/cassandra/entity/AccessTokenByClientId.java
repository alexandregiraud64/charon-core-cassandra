package com.agiraud.charon.core.cassandra.entity;

import lombok.Data;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;

@Data
@Table("access_token_by_client_id")
public class AccessTokenByClientId {
	@PrimaryKeyColumn(name = "client_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED, ordering = Ordering.DESCENDING)
	private String clientId;

	@PrimaryKeyColumn(name = "username", ordinal = 1, ordering = Ordering.DESCENDING)
	private String username;

	@Column("token_id")
	private String tokenId;

	@Column("token")
	private ByteBuffer token;

	@Column("authentication_id")
	private String authenticationId;

	@Column("authentication")
	private ByteBuffer authentication;

	@Column("refresh_token")
	private String refreshToken;
}
