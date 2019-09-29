package com.agiraud.charon.core.cassandra.config;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.stereotype.Service;

import com.agiraud.charon.core.dao.ApprovalService;

import java.util.Collection;

@Service("approvalStore")
@Slf4j
public class CustomApprovalStore implements ApprovalStore {
	/* ************************************************************************* */
	// ATTRIBUTES
	/* ************************************************************************* */
	@Autowired
	private ApprovalService service;

	/* ************************************************************************* */
	// OVERRIDE
	/* ************************************************************************* */
	@Override
	public boolean addApprovals(Collection<Approval> approvals) {
		log.debug("Add approvals: "+approvals.size());
		for (Approval approval : approvals) {
			service.create(approval);
		}
		return true;
	}
	
	@Override
	public boolean revokeApprovals(Collection<Approval> approvals) {
		log.debug("Revoke approvals: "+approvals.size());
		service.delete(approvals);
		return true;
	}
	
	@Override
	public Collection<Approval> getApprovals(String userId, String clientId) {
		log.debug("Get approvals for user ID: "+userId+" and client ID:"+clientId);
		Collection<Approval> result = service.getByClientIdAndUserId(clientId, userId);
		return result;
	}
}
