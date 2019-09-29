package com.agiraud.charon.core.cassandra.mapper;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.agiraud.charon.core.cassandra.entity.ScopeByName;
import com.agiraud.charon.core.dto.Scope;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ScopeMapper {
	private ScopeMapper() {}

	public static ScopeByName toScopeByName(Scope scope) {
		log.debug("[ScopeMapper] Scope to ScopeByName");
		if(scope == null) return null;
		
		ScopeByName scopeByName = new ScopeByName();
		scopeByName.setName(scope.getName());
		scopeByName.setDescription(scope.getDescription());
		scopeByName.setPrefix(scope.getPrefix());
		scopeByName.setEnabled(scope.isEnabled());
		scopeByName.setFields(scope.getFields());
		return scopeByName;
	}
	
	public static Scope toScope(ScopeByName scopeByName) {
		log.debug("[ScopeMapper] ScopeByName to Scope");
		if(scopeByName == null) return null;
		
		Scope scope = new Scope();
		scope.setName(scopeByName.getName());
		scope.setDescription(scopeByName.getDescription());
		scope.setPrefix(scopeByName.getPrefix());
		scope.setEnabled(scopeByName.isEnabled());
		scope.setFields(scopeByName.getFields());
		return scope;
	}

	public static Collection<Scope> toScopeList(List<ScopeByName> scopesByName) {
		Collection<Scope> scopes = new LinkedList<Scope>();
		for (ScopeByName scopeByName : scopesByName) {
			scopes.add(ScopeMapper.toScope(scopeByName));
		}
		return scopes;
	}

	public static Collection<ScopeByName> toScopeByNameList(Collection<Scope> scopes) {
		Collection<ScopeByName> scopesByName = new LinkedList<ScopeByName>();
		for (Scope scope : scopes) {
			scopesByName.add(ScopeMapper.toScopeByName(scope));
		}
		return scopesByName;
	}
}
