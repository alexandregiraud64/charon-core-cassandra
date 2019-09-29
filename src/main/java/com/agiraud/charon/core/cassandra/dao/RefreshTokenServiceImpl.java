package com.agiraud.charon.core.cassandra.dao;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.agiraud.charon.core.cassandra.entity.RefreshTokenByTokenId;
import com.agiraud.charon.core.cassandra.mapper.RefreshTokenMapper;
import com.agiraud.charon.core.cassandra.repository.RefreshTokenByTokenIdRepository;
import com.agiraud.charon.core.dao.RefreshTokenService;
import com.agiraud.charon.core.dto.RefreshToken;
import com.agiraud.charon.core.exception.EntityNotFoundException;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class RefreshTokenServiceImpl implements RefreshTokenService {
	/* ************************************************************************* */
	// ATTRIBUTES
	/* ************************************************************************* */
	@Autowired
	private RefreshTokenByTokenIdRepository repositoryByTokenId;

	/* ************************************************************************* */
	// POST CONSTRUCT FUNCTIONS
	/* ************************************************************************* */
	@PostConstruct
	public void init() {
		Assert.notNull(repositoryByTokenId, "repositoryByTokenId can not be null");
	}

	/* ************************************************************************* */
	// PUBLIC FUNCTIONS
	/* ************************************************************************* */
	public void create(RefreshToken refreshToken) {
		save(refreshToken);
	}

	public void delete(RefreshToken refreshToken) {
		repositoryByTokenId.delete(RefreshTokenMapper.toRefreshTokenByTokenId(refreshToken));
	}

	@Transactional(readOnly = true)
	public RefreshToken getById(String id) {
		return RefreshTokenMapper.toRefreshToken(repositoryByTokenId.findByTokenId(id).orElse(null));
	}

	@Transactional(readOnly = true)
	public RefreshToken findByTokenId(String id) throws EntityNotFoundException {
		RefreshToken refreshToken = getById(id);
		if (refreshToken == null) {
			throw new EntityNotFoundException("Refresh token not found for the ID: '"+id+"'");
		}
		return refreshToken;
	}
	
	/* ************************************************************************* */
	// PRIVATE FUNCTIONS
	/* ************************************************************************* */
	private void save(RefreshToken refreshToken) {
		log.debug("Saving refreshToken: "+refreshToken.toString());
		saveRefreshTokenByTokenId(RefreshTokenMapper.toRefreshTokenByTokenId(refreshToken));
	}

	private void saveRefreshTokenByTokenId(@NotNull final RefreshTokenByTokenId refreshToken) {
		repositoryByTokenId.save(refreshToken);
	}
}
