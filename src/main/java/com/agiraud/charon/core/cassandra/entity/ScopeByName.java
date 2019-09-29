package com.agiraud.charon.core.cassandra.entity;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;
import org.springframework.security.oauth2.common.util.OAuth2Utils;

import lombok.Data;

@Data
@Table("scope_by_name")
public class ScopeByName {
	@PrimaryKeyColumn(name = "name", ordinal = 0, type = PrimaryKeyType.PARTITIONED, ordering = Ordering.DESCENDING)
	private String name;

	@Column("description")
	private String description;

	@Column("prefix")
	private String prefix = OAuth2Utils.SCOPE_PREFIX;

	@Column("is_enabled")
	private boolean isEnabled;

	@Column("fields")
	private List<String> fields = new LinkedList<String>();
}
