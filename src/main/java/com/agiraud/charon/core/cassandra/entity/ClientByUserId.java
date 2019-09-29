package com.agiraud.charon.core.cassandra.entity;

import lombok.Data;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Table("client_by_user_id")
public class ClientByUserId {
	
	@PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED, ordering = Ordering.DESCENDING)
	private UUID userId;
	
	@PrimaryKeyColumn(name = "client_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
	private String clientId;
	
	@Column("client_secret")
	private String clientSecret;
	
	@Column("display_name")
	private String displayName;

	@Column("scope")
	private Set<String> scope = new HashSet<String>();
	
	@Column("resource_ids")
	private Set<String> resourceIds = new HashSet<String>();
	
	@Column("authorized_grant_types")
	private Set<String> authorizedGrantTypes = new HashSet<String>();
	
	@Column("registered_redirect_uri")
	private Set<String> registeredRedirectUri = new HashSet<String>();
	
	@Column("authorities")
	private Set<String> authorities = new HashSet<String>();
	
	@Column("access_token_validity_seconds")
	private Integer accessTokenValiditySeconds = 21600;
	
	@Column("refresh_token_validity_seconds")
	private Integer refreshTokenValiditySeconds = 2592000;
	
	@Column("additional_information")
	private String additionalInformation;
	
	@Column("auto_approve_scopes")
	private Set<String> autoApproveScopes;
}
