package com.agiraud.charon.core.cassandra.config;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.AuthorizationRequest;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.ApprovalStoreUserApprovalHandler;

import com.agiraud.charon.core.dao.ScopeService;
import com.agiraud.charon.core.dto.Scope;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.oauth2.provider.approval.Approval.ApprovalStatus;
import org.springframework.util.Assert;

@Slf4j
public class CustomApprovalStoreUserApprovalHandler extends ApprovalStoreUserApprovalHandler {
	/* ************************************************************************* */
	// ATTRIBUTES
	/* ************************************************************************* */
	private ScopeService scopeService;
	private ApprovalStore approvalStore;

	/* ************************************************************************* */
	// CONSTRUCTOR
	/* ************************************************************************* */
	public CustomApprovalStoreUserApprovalHandler(ScopeService scopeService) {
		this.scopeService = scopeService;
	}
	
	/* ************************************************************************* */
	// POST CONSTRUCT FUNCTIONS
	/* ************************************************************************* */
	@PostConstruct
	public void init() {
		Assert.notNull(approvalStore, "ApprovalStore can not be null");
		Assert.notNull(scopeService, "ScopeService can not be null");
	}
	
	/* ************************************************************************* */
	// GETTERS AND SETTERS
	/* ************************************************************************* */
	public void setApprovalStore(ApprovalStore store) {
		super.setApprovalStore(store);
		this.approvalStore = store;
	}

	/* ************************************************************************* */
	// OVERRIDE
	/* ************************************************************************* */
	@Override
	public Map<String, Object> getUserApprovalRequest(AuthorizationRequest authorizationRequest, Authentication userAuthentication) {
		log.debug("Getting user approval request");
		
		Map<String, Object> model = new HashMap<String, Object>();
		model.putAll(authorizationRequest.getRequestParameters());
		Map<String, Scope> scopes = new LinkedHashMap<String, Scope>();
		
		if(scopeService == null) {
			log.error("The scope service is null");
		}
		
		Collection<Scope> scopesDetails = scopeService.getAll();
		log.info("Username: "+userAuthentication.getName());
		log.info("Client ID: "+authorizationRequest.getClientId());
		Collection<Approval> userApprovals = approvalStore.getApprovals(userAuthentication.getName(), authorizationRequest.getClientId());

		Set<String> scopeRequested = authorizationRequest.getScope();
		for (Scope scope : scopesDetails) {
			if(scopeRequested.contains(scope.getName())) {
				scopes.put(scope.getName(), scope);
			}
		}

		for (Approval approval : userApprovals) {
			if (scopeRequested.contains(approval.getScope())) {
				Scope scope = scopes.get(approval.getScope());
				scope.setApproved(approval.getStatus() == ApprovalStatus.APPROVED);
				scopes.put(scope.getName(), scope);
			}
		}
		
		model.put("scopes", scopes);
		return model;
	}

}
