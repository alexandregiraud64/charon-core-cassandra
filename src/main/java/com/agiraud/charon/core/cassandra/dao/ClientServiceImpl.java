package com.agiraud.charon.core.cassandra.dao;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.agiraud.charon.core.cassandra.entity.ClientByClientId;
import com.agiraud.charon.core.cassandra.entity.ClientByUserId;
import com.agiraud.charon.core.cassandra.mapper.ClientMapper;
import com.agiraud.charon.core.cassandra.repository.ClientByClientIdRepository;
import com.agiraud.charon.core.cassandra.repository.ClientByUserIdRepository;
import com.agiraud.charon.core.dao.ClientService;
import com.agiraud.charon.core.dao.ScopeService;
import com.agiraud.charon.core.dao.SessionService;
import com.agiraud.charon.core.dao.UserService;
import com.agiraud.charon.core.dto.Client;
import com.agiraud.charon.core.dto.ClientDetail;
import com.agiraud.charon.core.exception.EntityNotFoundException;
import com.datastax.driver.core.utils.UUIDs;

import lombok.extern.slf4j.Slf4j;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class ClientServiceImpl implements ClientService {
	/* ************************************************************************* */
	// ATTRIBUTES
	/* ************************************************************************* */
	@Autowired
	private ClientByClientIdRepository repositoryByClientId;
	
	@Autowired
	private ClientByUserIdRepository repositoryByUserId;

	@Autowired
	private UserService userService;

	@Autowired
	private ScopeService scopeService;

	@Autowired
	private SessionService sessionService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
	/* ************************************************************************* */
	// POST CONSTRUCT FUNCTIONS
	/* ************************************************************************* */
	@PostConstruct
	public void init() {
		Assert.notNull(repositoryByClientId, "repositoryByClientId can not be null");
		Assert.notNull(repositoryByUserId, "repositoryByUserId can not be null");
	}

	/* ************************************************************************* */
	// PUBLIC FUNCTIONS
	/* ************************************************************************* */
	public void create(Client client) {
		log.debug("Creating client");
		client.setClientId(UUIDs.timeBased().toString());
		client.setClientSecret(passwordEncoder.encode(client.getClientSecret()));
		client.setAccessTokenValiditySeconds(2592000);
		client.setRefreshTokenValiditySeconds(2592000 * 30);
		client.setAuthorizedGrantTypes(new HashSet<String>(Arrays.asList("refresh_token", "authorization_code")));
		client.setUserId(sessionService.getPrincipalAsCustomUserPrincipal().getUserId());
		
		checkIfScopeExists(client.getScope());
		checkIfUserIdExists(client.getUserId());
		
		save(client);
	}

	public void delete(Client client) {
		repositoryByClientId.delete(ClientMapper.toClientByClientId(client));
		repositoryByUserId.delete(ClientMapper.toClientByUserId(client));
	}

	@Transactional(readOnly = true)
	public Client getById(String id) {
		log.debug("Get client based on ID: "+id);
		return ClientMapper.toClient(repositoryByClientId.findByClientId(id).orElse(null));
	}

	@Transactional(readOnly = true)
	public ClientDetail getClientDetailById(String clientId) {
		 return ClientMapper.toClientDetail(getById(clientId));
	}
	
	@Transactional(readOnly = true)
	public Client findByClientId(String id) throws EntityNotFoundException {
		Client client = getById(id);
		if (client == null) {
			throw new EntityNotFoundException("Client not found for the ID: '"+id+"'");
		}
		return client;
	}

	@Transactional(readOnly = true)
	public Collection<Client> getAllClientsForAuthenticatedUser() {
		UUID userId = sessionService.getAuthenticatedUser().getUserId();
		log.info("Looking for all clients for the user "+userId);
		return ClientMapper.toClientList(repositoryByUserId.findAllByUserId(userId));
	}

	public void deleteById(final String clientId) {
		Client client = getById(clientId);
		delete(client);
	}
	
	/* ************************************************************************* */
	// PRIVATE FUNCTIONS
	/* ************************************************************************* */
	private void save(Client client) {
		log.debug("Saving client: "+client.toString());
	    
		saveClientByClientId(ClientMapper.toClientByClientId(client));
		saveClientByUserId(ClientMapper.toClientByUserId(client));
	}
	
	private void saveClientByClientId(@NotNull final ClientByClientId client) {
		repositoryByClientId.save(client);
	}
	
	private void saveClientByUserId(@NotNull final ClientByUserId client) {
		repositoryByUserId.save(client);
	}

	private void checkIfScopeExists(Set<String> scope) throws EntityNotFoundException {
		log.info("Checking for scopes");
		for (String name : scope) {
			log.info("Checking for scope "+name);
			scopeService.findByScopeName(name);
		}
	}

	private void checkIfUserIdExists(UUID userId) throws EntityNotFoundException {
		log.info("Checking for userId "+userId);
		userService.findByUserId(userId);
	}
}
