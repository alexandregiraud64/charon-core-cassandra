package com.agiraud.charon.core.cassandra.dao;

import java.util.Collection;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.approval.Approval;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.agiraud.charon.core.cassandra.entity.ApprovalByUserId;
import com.agiraud.charon.core.cassandra.mapper.ApprovalMapper;
import com.agiraud.charon.core.cassandra.repository.ApprovalByUserIdRepository;
import com.agiraud.charon.core.dao.ApprovalService;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ApprovalServiceImpl implements ApprovalService {
	/* ************************************************************************* */
	// ATTRIBUTES
	/* ************************************************************************* */
	@Autowired
	private ApprovalByUserIdRepository repositoryByUserId;

	/* ************************************************************************* */
	// POST CONSTRUCT FUNCTIONS
	/* ************************************************************************* */
	@PostConstruct
	public void init() {
		Assert.notNull(repositoryByUserId, "repositoryByUserId can not be null");
	}

	/* ************************************************************************* */
	// PUBLIC FUNCTIONS
	/* ************************************************************************* */
	public void create(Approval approval) {
		// TODO Check if the data already exist
		save(approval);
	}

	public void delete(Approval approval) {
		repositoryByUserId.delete(ApprovalMapper.toApprovalByUserId(approval));
	}

	public void delete(Collection<Approval> approvals) {
		for (Approval approval : approvals) {
			delete(approval);
		}
	}

	@Transactional(readOnly = true)
	public Collection<Approval> getByUserId(String userId) {
		return ApprovalMapper.toApprovalList(repositoryByUserId.findByUserId(userId));
	}

	@Transactional(readOnly = true)
	public Collection<Approval> getByClientIdAndUserId(String clientId, String userId) {
		return ApprovalMapper.toApprovalList(repositoryByUserId.findAllByUserIdAndClientId(userId, clientId));
	}

	/* ************************************************************************* */
	// PRIVATE FUNCTIONS
	/* ************************************************************************* */
	private void save(Approval approval) {
		log.debug("Saving approval: "+approval.toString());
		saveApprovalByUserId(ApprovalMapper.toApprovalByUserId(approval));
	}
	
	private void saveApprovalByUserId(@NotNull final ApprovalByUserId approval) {
		repositoryByUserId.save(approval);
	}

}
