package com.agiraud.charon.core.cassandra.entity;

import lombok.Data;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus;

import java.util.Date;

@Data
@Table("approval_by_user_id")
public class ApprovalByUserId {
	@PrimaryKeyColumn(name = "user_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED, ordering = Ordering.DESCENDING)
	private String userId;

	@PrimaryKeyColumn(name = "client_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
	private String clientId;

	@PrimaryKeyColumn(name = "scope", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
	private String scope;

	@Column("status")
	private ApprovalStatus status;

	@Column("expires_at")
	private Date expiresAt;

	@Column("last_updated_at")
	private Date lastUpdatedAt;
}
