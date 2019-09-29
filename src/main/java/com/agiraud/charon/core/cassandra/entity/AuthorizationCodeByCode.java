package com.agiraud.charon.core.cassandra.entity;

import lombok.Data;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.nio.ByteBuffer;
import java.util.Date;

@Data
@Table("authorization_code_by_code")
public class AuthorizationCodeByCode {
	@PrimaryKeyColumn(name = "code", ordinal = 0, type = PrimaryKeyType.PARTITIONED, ordering = Ordering.DESCENDING)
	private String code;

	@Column("client_id")
	private String clientId;
	
	@Column("authentication")
	private ByteBuffer authentication;
	
	@Column("expires_at")
	private Date expiresAt;
}
