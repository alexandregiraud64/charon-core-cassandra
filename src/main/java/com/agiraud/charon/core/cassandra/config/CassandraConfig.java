package com.agiraud.charon.core.cassandra.config;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraSessionFactoryBean;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableCassandraRepositories(basePackages = "com.agiraud.charon.core.cassandra.repository")
@Slf4j
public class CassandraConfig extends AbstractCassandraConfiguration {
	/* ************************************************************************* */
	// ATTRIBUTES
	/* ************************************************************************* */
	@Value("${spring.data.cassandra.keyspace-name}")
	protected String keyspaceName;

	@Value("${spring.data.cassandra.port}")
	private Integer port;

	@Value("${spring.data.cassandra.contact-points}")
	private String nodeNames;

	/* ************************************************************************* */
	// GETTERS AND SETTERS
	/* ************************************************************************* */
	public String getNodeNames() {
		return this.nodeNames;
	}
	
	public int getPort(){
		return this.port;
	}
	
	/* ************************************************************************* */
	// OVERRIDES
	/* ************************************************************************* */
	@Override
	public String getContactPoints() {
		return getNodeNames();
	}

	@Override
	protected String getKeyspaceName() {
		return this.keyspaceName;
	}

	@Override
	protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
		return Collections.singletonList(
			CreateKeyspaceSpecification.createKeyspace(keyspaceName).ifNotExists().with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication()
		);
	}
    
	@Override
	public SchemaAction getSchemaAction() {
		return SchemaAction.CREATE_IF_NOT_EXISTS;
	}

	@Override
	protected List<String> getStartupScripts() {
		return Collections.singletonList("CREATE KEYSPACE IF NOT EXISTS " + keyspaceName + " WITH replication = {" + " 'class': 'SimpleStrategy', " + " 'replication_factor': '2' " + "};");
	}

	/* ************************************************************************* */
	// BEANS
	/* ************************************************************************* */
	@Bean
	public CassandraClusterFactoryBean cluster() {
		log.debug("Cassandra cluster initialized");
		CassandraClusterFactoryBean cluster = new CassandraClusterFactoryBean();
		cluster.setKeyspaceCreations(getKeyspaceCreations());
		cluster.setContactPoints(getContactPoints());
		cluster.setPort(getPort());
		cluster.setMetricsEnabled(false);
		return cluster;
	}

	@Bean
	public CassandraMappingContext mappingContext() {
		log.debug("Cassandra mapping context initialized");
		CassandraMappingContext mappingContext = new CassandraMappingContext();
		mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cluster().getObject(), getKeyspaceName()));
		return mappingContext;
	}

	@Bean
	public CassandraSessionFactoryBean session() {
		log.debug("Cassandra session initialized");
		CassandraSessionFactoryBean session = new CassandraSessionFactoryBean();
		session.setCluster(cluster().getObject());
		session.setKeyspaceName(getKeyspaceName());
		session.setConverter(cassandraConverter());
		session.setSchemaAction(SchemaAction.CREATE_IF_NOT_EXISTS);
		return session;
	}
}