package com.agiraud.charon.core.cassandra.dao;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.agiraud.charon.core.cassandra.entity.AccessTokenByAuthenticationId;
import com.agiraud.charon.core.cassandra.entity.AccessTokenByClientId;
import com.agiraud.charon.core.cassandra.entity.AccessTokenByRefreshToken;
import com.agiraud.charon.core.cassandra.entity.AccessTokenByTokenId;
import com.agiraud.charon.core.cassandra.mapper.AccessTokenMapper;
import com.agiraud.charon.core.cassandra.repository.AccessTokenByAuthenticationIdRepository;
import com.agiraud.charon.core.cassandra.repository.AccessTokenByClientIdRepository;
import com.agiraud.charon.core.cassandra.repository.AccessTokenByRefreshTokenRepository;
import com.agiraud.charon.core.cassandra.repository.AccessTokenByTokenIdRepository;
import com.agiraud.charon.core.dao.AccessTokenService;
import com.agiraud.charon.core.dto.AccessToken;
import com.agiraud.charon.core.exception.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AccessTokenServiceImpl implements AccessTokenService {
	/* ************************************************************************* */
	// ATTRIBUTES
	/* ************************************************************************* */
	@Autowired
	private AccessTokenByAuthenticationIdRepository repositoryByAuthenticationId;

	@Autowired
	private AccessTokenByClientIdRepository repositoryByClientId;

	@Autowired
	private AccessTokenByRefreshTokenRepository repositoryByRefreshToken;

	@Autowired
	private AccessTokenByTokenIdRepository repositoryByTokenId;

	/* ************************************************************************* */
	// POST CONSTRUCT FUNCTIONS
	/* ************************************************************************* */
	@PostConstruct
	public void init() {
		Assert.notNull(repositoryByAuthenticationId, "repositoryByAuthenticationId can not be null");
		Assert.notNull(repositoryByClientId, "repositoryByClientId can not be null");
		Assert.notNull(repositoryByRefreshToken, "repositoryByRefreshToken can not be null");
		Assert.notNull(repositoryByTokenId, "repositoryByTokenId can not be null");
	}

	/* ************************************************************************* */
	// PUBLIC FUNCTIONS
	/* ************************************************************************* */
	public void create(AccessToken accessToken) {
		save(accessToken);
	}

	public void delete(AccessToken accessToken) {
		repositoryByTokenId.delete(AccessTokenMapper.toAccessTokenByTokenId(accessToken));
	}

	@Transactional(readOnly = true)
	public AccessToken getByTokenId(String id) {
		return AccessTokenMapper.toAccessToken(repositoryByTokenId.findByTokenId(id).orElse(null));
	}

	@Transactional(readOnly = true)
	public AccessToken getByAuthenticationId(String id) {
		return AccessTokenMapper.toAccessToken(repositoryByAuthenticationId.findByAuthenticationId(id).orElse(null));
	}

	@Transactional(readOnly = true)
	public AccessToken getByRefreshToken(String refreshToken) {
		return AccessTokenMapper.toAccessToken(repositoryByRefreshToken.findByRefreshToken(refreshToken).orElseThrow(null));
	}

	@Transactional(readOnly = true)
	public List<AccessToken> getByClientId(String id) {
		return AccessTokenMapper.toAccessToken(repositoryByClientId.findByClientId(id));
	}

	@Transactional(readOnly = true)
	public List<AccessToken> getByClientIdAndUsername(String id, String username) {
		return AccessTokenMapper.toAccessToken(repositoryByClientId.findByClientIdAndUsername(id, username));
	}

	@Transactional(readOnly = true)
	public AccessToken findByTokenId(String id) throws EntityNotFoundException {
		AccessToken accessToken = getByTokenId(id);
		if (accessToken == null) {
			throw new EntityNotFoundException("AccessToken not found for the token id: '"+id+"'");
		}
		return accessToken;
	}

	@Transactional(readOnly = true)
	public AccessToken findByAuthenticationId(String id) throws EntityNotFoundException {
		AccessToken accessToken = getByAuthenticationId(id);
		if (accessToken == null) {
			throw new EntityNotFoundException("AccessToken not found for the authentication id: '"+id+"'");
		}
		return accessToken;
	}

	@Transactional(readOnly = true)
	public AccessToken findByRefreshToken(String id) throws EntityNotFoundException {
		AccessToken accessToken = getByRefreshToken(id);
		if (accessToken == null) {
			throw new EntityNotFoundException("AccessToken not found for the refresh token: '"+id+"'");
		}
		return accessToken;
	}
	
	/* ************************************************************************* */
	// PRIVATE FUNCTIONS
	/* ************************************************************************* */
	private void save(AccessToken accessToken) {
		log.debug("Saving accessToken: "+accessToken.toString());
		saveAccessTokenByAuthenticationId(AccessTokenMapper.toAccessTokenByAuthenticationId(accessToken));
		saveAccessTokenByClientId(AccessTokenMapper.toAccessTokenByClientId(accessToken));
		saveAccessTokenByRefreshToken(AccessTokenMapper.toAccessTokenByRefreshToken(accessToken));
		saveAccessTokenByTokenId(AccessTokenMapper.toAccessTokenByTokenId(accessToken));
	}
	
	private void saveAccessTokenByAuthenticationId(@NotNull final AccessTokenByAuthenticationId accessToken) {
		repositoryByAuthenticationId.save(accessToken);
	}
	
	private void saveAccessTokenByClientId(@NotNull final AccessTokenByClientId accessToken) {
		repositoryByClientId.save(accessToken);
	}
	
	private void saveAccessTokenByRefreshToken(@NotNull final AccessTokenByRefreshToken accessToken) {
		repositoryByRefreshToken.save(accessToken);
	}
	
	private void saveAccessTokenByTokenId(@NotNull final AccessTokenByTokenId accessToken) {
		repositoryByTokenId.save(accessToken);
	}
}
