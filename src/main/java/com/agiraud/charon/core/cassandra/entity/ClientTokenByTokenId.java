package com.agiraud.charon.core.cassandra.entity;

import lombok.Data;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;

@Data
@Table("client_token_by_token_id")
public class ClientTokenByTokenId {
	@PrimaryKeyColumn(name = "token_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED, ordering = Ordering.DESCENDING)
	private String tokenId;

	@Column("token")
	private ByteBuffer token;
	
	@Column("authentication_id")
	private String authenticationId;

	@Column("username")
	private String username;

	@Column("client_id")
	private String clientId;
}
