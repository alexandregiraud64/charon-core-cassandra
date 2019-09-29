package com.agiraud.charon.core.cassandra.dao;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.agiraud.charon.core.cassandra.entity.ScopeByName;
import com.agiraud.charon.core.cassandra.mapper.ScopeMapper;
import com.agiraud.charon.core.cassandra.repository.ScopeByNameRepository;
import com.agiraud.charon.core.dao.ScopeService;
import com.agiraud.charon.core.dto.Scope;
import com.agiraud.charon.core.exception.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ScopeServiceImpl implements ScopeService {
	/* ************************************************************************* */
	// ATTRIBUTES
	/* ************************************************************************* */
	@Autowired
	private ScopeByNameRepository repositoryByName;

	/* ************************************************************************* */
	// POST CONSTRUCT FUNCTIONS
	/* ************************************************************************* */
	@PostConstruct
	public void init() {
		Assert.notNull(repositoryByName, "repositoryById can not be null");
	}

	/* ************************************************************************* */
	// PUBLIC FUNCTIONS
	/* ************************************************************************* */
	public void create(Scope scope) {
		save(scope);
	}

	public void delete(Scope scope) {
		repositoryByName.delete(ScopeMapper.toScopeByName(scope));
	}

	@Transactional(readOnly = true)
	public Scope getByName(String name) {
		return ScopeMapper.toScope(repositoryByName.findByName(name).orElse(null));
	}

	@Transactional(readOnly = true)
	public Scope findByScopeName(String name) throws EntityNotFoundException {
		Scope scope = getByName(name);
		if (scope == null) {
			throw new EntityNotFoundException("Scope not found for the scope name: '"+name+"'");
		}
		return scope;
	}

	@Transactional(readOnly = true)
	public Collection<Scope> getAll() {
		log.debug("Retrieving all the scopes");
		return ScopeMapper.toScopeList(repositoryByName.findAll());
	}

	/* ************************************************************************* */
	// PRIVATE FUNCTIONS
	/* ************************************************************************* */
	private void save(Scope scope) {
		log.debug("Saving scope: "+scope.toString());
		saveScopeByName(ScopeMapper.toScopeByName(scope));
	}
	
	private void saveScopeByName(@NotNull final ScopeByName scope) {
		repositoryByName.save(scope);
	}
}
