package com.agiraud.charon.core.cassandra.dao;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.agiraud.charon.core.cassandra.entity.AuthorizationCodeByCode;
import com.agiraud.charon.core.cassandra.mapper.AuthorizationCodeMapper;
import com.agiraud.charon.core.cassandra.repository.AuthorizationCodeByCodeRepository;
import com.agiraud.charon.core.dao.AuthorizationCodeService;
import com.agiraud.charon.core.dto.AuthorizationCode;
import com.agiraud.charon.core.exception.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AuthorizationCodeServiceImpl implements AuthorizationCodeService {
	/* ************************************************************************* */
	// ATTRIBUTES
	/* ************************************************************************* */
	@Autowired
	private AuthorizationCodeByCodeRepository repositoryByCode;

	/* ************************************************************************* */
	// POST CONSTRUCT FUNCTIONS
	/* ************************************************************************* */
	@PostConstruct
	public void init() {
		Assert.notNull(repositoryByCode, "repositoryByCode can not be null");
	}

	/* ************************************************************************* */
	// PUBLIC FUNCTIONS
	/* ************************************************************************* */
	public void create(AuthorizationCode authenticationCode) {
		// TODO Check if the data already exist
		save(authenticationCode);
	}

	public void delete(AuthorizationCode authenticationCode) {
		repositoryByCode.delete(AuthorizationCodeMapper.toAuthenticationCodeByCode(authenticationCode));
	}

	@Transactional(readOnly = true)
	public AuthorizationCode getByCode(String code) {
		return AuthorizationCodeMapper.toAuthenticationCode(repositoryByCode.findByCode(code).orElse(null));
	}

	public AuthorizationCode deleteByCode(String code) {
		AuthorizationCode authenticationCode = getByCode(code);
		if(authenticationCode == null) return authenticationCode;
		
		delete(authenticationCode);
		return authenticationCode;
	}

	@Transactional(readOnly = true)
	public AuthorizationCode findByCode(String code) throws EntityNotFoundException {
		AuthorizationCode authenticationCode = getByCode(code);
		if (authenticationCode == null) {
			throw new EntityNotFoundException("Authorization code not found for the code: '"+code+"'");
		}
		return authenticationCode;
	}

	/* ************************************************************************* */
	// PRIVATE FUNCTIONS
	/* ************************************************************************* */
	private void save(AuthorizationCode authenticationCode) {
		log.debug("Saving authorization code: "+authenticationCode.toString());
		saveAuthenticationCodeByClientId(AuthorizationCodeMapper.toAuthenticationCodeByCode(authenticationCode));
	}
	
	private void saveAuthenticationCodeByClientId(@NotNull final AuthorizationCodeByCode authenticationCode) {
		repositoryByCode.save(authenticationCode);
	}

}
