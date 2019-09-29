package com.agiraud.charon.core.cassandra.entity;

import lombok.Data;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;

@Data
@Table("access_token_by_authentication_id")
public class AccessTokenByAuthenticationId {
	@PrimaryKeyColumn(name = "authentication_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED, ordering = Ordering.DESCENDING)
	private String authenticationId;

	@Column("token_id")
	private String tokenId;

	@Column("token")
	private ByteBuffer token;

	@Column("username")
	private String username;

	@Column("client_id")
	private String clientId;

	@Column("authentication")
	private ByteBuffer authentication;

	@Column("refresh_token")
	private String refreshToken;
}
