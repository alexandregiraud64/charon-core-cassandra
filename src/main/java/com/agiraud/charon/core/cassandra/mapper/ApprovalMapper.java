package com.agiraud.charon.core.cassandra.mapper;

import java.util.Collection;
import java.util.LinkedList;

import org.springframework.security.oauth2.provider.approval.Approval;

import com.agiraud.charon.core.cassandra.entity.ApprovalByUserId;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ApprovalMapper {
	private ApprovalMapper() {}
	
	public static ApprovalByUserId toApprovalByUserId(Approval approval) {
		log.debug("[ApprovalMapper] Approval to ApprovalByUserId");
		if(approval == null) return null;
		
		ApprovalByUserId approvalByUserId = new ApprovalByUserId();
		approvalByUserId.setUserId(approval.getUserId());
		approvalByUserId.setClientId(approval.getClientId());
		approvalByUserId.setScope(approval.getScope());
		approvalByUserId.setStatus(approval.getStatus());
		approvalByUserId.setExpiresAt(approval.getExpiresAt());
		approvalByUserId.setLastUpdatedAt(approval.getLastUpdatedAt());
		return approvalByUserId;
	}

	public static Approval toApproval(ApprovalByUserId approvalByUserId) {
		log.debug("[UserMapper] ApprovalByUserId to Approval");
		if(approvalByUserId == null) return null;
		
		Approval approval = new Approval(
				approvalByUserId.getUserId(),
				approvalByUserId.getClientId(),
				approvalByUserId.getScope(),
				approvalByUserId.getExpiresAt(),
				approvalByUserId.getStatus(),
				approvalByUserId.getLastUpdatedAt());
		return approval;
	}

	public static Collection<Approval> toApprovalList(Collection<ApprovalByUserId> approvalsByUserId) {
		Collection<Approval> approvals = new LinkedList<Approval>();
		for (ApprovalByUserId approvalByUserId : approvalsByUserId) {
			approvals.add(ApprovalMapper.toApproval(approvalByUserId));
		}
		return approvals;
	}

	public static Collection<ApprovalByUserId> toApprovalByUserIdList(Collection<Approval> approvals) {
		Collection<ApprovalByUserId> approvalsByUserId = new LinkedList<ApprovalByUserId>();
		for (Approval approval : approvals) {
			approvalsByUserId.add(ApprovalMapper.toApprovalByUserId(approval));
		}
		return approvalsByUserId;
	}
}
