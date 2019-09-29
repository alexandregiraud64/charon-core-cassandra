package com.agiraud.charon.core.cassandra.mapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import com.agiraud.charon.core.cassandra.entity.ClientByClientId;
import com.agiraud.charon.core.cassandra.entity.ClientByUserId;
import com.agiraud.charon.core.dto.Client;
import com.agiraud.charon.core.dto.ClientDetail;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class ClientMapper {
	private ClientMapper() {}

	public static ClientByClientId toClientByClientId(Client client) {
		log.debug("[ClientMapper] Client to ClientByClientId");
		if(client == null) return null;
		
		ClientByClientId clientByClientId = new ClientByClientId();
		clientByClientId.setClientId(client.getClientId());
		clientByClientId.setClientSecret(client.getClientSecret());
		clientByClientId.setDisplayName(client.getDisplayName());
		clientByClientId.setScope(client.getScope());
		clientByClientId.setResourceIds(client.getResourceIds());
		clientByClientId.setAuthorizedGrantTypes(client.getAuthorizedGrantTypes());
		clientByClientId.setAuthorities(client.getAuthorities());
		clientByClientId.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
		clientByClientId.setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
		clientByClientId.setAdditionalInformation(client.getAdditionalInformation());
		clientByClientId.setAutoApproveScopes(client.getAutoApproveScopes());
		clientByClientId.setRegisteredRedirectUri(client.getRegisteredRedirectUri());
		clientByClientId.setUserId(client.getUserId());
		return clientByClientId;
	}

	public static Client toClient(ClientByClientId clientByClientId) {
		log.debug("[ClientMapper] ClientByClientId to Client");
		if(clientByClientId == null) return null;
		
		Client client = new Client();
		client.setClientId(clientByClientId.getClientId());
		client.setClientSecret(clientByClientId.getClientSecret());
		client.setDisplayName(clientByClientId.getDisplayName());
		client.setScope(clientByClientId.getScope());
		client.setResourceIds(clientByClientId.getResourceIds());
		client.setAuthorizedGrantTypes(clientByClientId.getAuthorizedGrantTypes());
		client.setAuthorities(clientByClientId.getAuthorities());
		client.setAccessTokenValiditySeconds(clientByClientId.getAccessTokenValiditySeconds());
		client.setRefreshTokenValiditySeconds(clientByClientId.getRefreshTokenValiditySeconds());
		client.setAdditionalInformation(clientByClientId.getAdditionalInformation());
		client.setAutoApproveScopes(clientByClientId.getAutoApproveScopes());
		client.setRegisteredRedirectUri(clientByClientId.getRegisteredRedirectUri());
		client.setUserId(clientByClientId.getUserId());
		return client;
	}

	public static ClientByUserId toClientByUserId(Client client) {
		log.debug("[ClientMapper] Client to ClientByUserId");
		if(client == null) return null;
		
		ClientByUserId clientByUserId = new ClientByUserId();
		clientByUserId.setClientId(client.getClientId());
		clientByUserId.setClientSecret(client.getClientSecret());
		clientByUserId.setDisplayName(client.getDisplayName());
		clientByUserId.setScope(client.getScope());
		clientByUserId.setResourceIds(client.getResourceIds());
		clientByUserId.setAuthorizedGrantTypes(client.getAuthorizedGrantTypes());
		clientByUserId.setAuthorities(client.getAuthorities());
		clientByUserId.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
		clientByUserId.setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
		clientByUserId.setAdditionalInformation(client.getAdditionalInformation());
		clientByUserId.setAutoApproveScopes(client.getAutoApproveScopes());
		clientByUserId.setRegisteredRedirectUri(client.getRegisteredRedirectUri());
		clientByUserId.setUserId(client.getUserId());
		return clientByUserId;
	}

	public static Client toClient(ClientByUserId clientByUserId) {
		log.debug("[ClientMapper] ClientByUserId to Client");
		if(clientByUserId == null) return null;
		
		Client client = new Client();
		client.setClientId(clientByUserId.getClientId());
		client.setClientSecret(clientByUserId.getClientSecret());
		client.setDisplayName(clientByUserId.getDisplayName());
		client.setScope(clientByUserId.getScope());
		client.setResourceIds(clientByUserId.getResourceIds());
		client.setAuthorizedGrantTypes(clientByUserId.getAuthorizedGrantTypes());
		client.setAuthorities(clientByUserId.getAuthorities());
		client.setAccessTokenValiditySeconds(clientByUserId.getAccessTokenValiditySeconds());
		client.setRefreshTokenValiditySeconds(clientByUserId.getRefreshTokenValiditySeconds());
		client.setAdditionalInformation(clientByUserId.getAdditionalInformation());
		client.setAutoApproveScopes(clientByUserId.getAutoApproveScopes());
		client.setRegisteredRedirectUri(clientByUserId.getRegisteredRedirectUri());
		client.setUserId(clientByUserId.getUserId());
		return client;
	}
	
	public static ClientDetail toClientDetail(Client client) {
		log.debug("[ClientMapper] Client to ClientDetail");
		if(client == null) return null;

		ClientDetail clientDetail = new ClientDetail();
		clientDetail.setClientId(client.getClientId());
		clientDetail.setClientSecret(client.getClientSecret());
		clientDetail.setDisplayName(client.getDisplayName());
		clientDetail.setScope(client.getScope());
		clientDetail.setResourceIds(client.getResourceIds());
		clientDetail.setAuthorizedGrantTypes(client.getAuthorizedGrantTypes());
		clientDetail.setAuthorities(toAuthoritiesCollection(client.getAuthorities()));
		clientDetail.setAccessTokenValiditySeconds(client.getAccessTokenValiditySeconds());
		clientDetail.setRefreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds());
		clientDetail.setAutoApproveScopes(client.getAutoApproveScopes());
		clientDetail.setRegisteredRedirectUri(client.getRegisteredRedirectUri());
		
		return clientDetail;
	}

	private static List<GrantedAuthority> toAuthoritiesCollection(Set<String> set) {
		if(set == null) return new ArrayList<GrantedAuthority>();
		
		String authorities = "";
		for (String authority : set) {
			if(authorities != "") {
				authorities += ",";
			}
			
			authorities += authority;
		}
		return AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
	}

	public static Client toClient(ClientDetail clientDetail) {
		log.debug("[ClientMapper] ClientDetail to Client");
		if(clientDetail == null) return null;
		
		Client client = new Client();
		client.setClientId(clientDetail.getClientId());
		client.setClientSecret(clientDetail.getClientSecret());
		client.setDisplayName(clientDetail.getDisplayName());
		client.setScope(clientDetail.getScope());
		client.setResourceIds(clientDetail.getResourceIds());
		client.setAuthorizedGrantTypes(clientDetail.getAuthorizedGrantTypes());
		client.setAuthorities(toAutoritiesSet(clientDetail.getAuthorities()));
		client.setAccessTokenValiditySeconds(clientDetail.getAccessTokenValiditySeconds());
		client.setRefreshTokenValiditySeconds(clientDetail.getRefreshTokenValiditySeconds());
		client.setAutoApproveScopes(clientDetail.getAutoApproveScopes());
		client.setRegisteredRedirectUri(clientDetail.getRegisteredRedirectUri());
		return client;
	}

	private static Set<String> toAutoritiesSet(Collection<GrantedAuthority> authorities) {
		if(authorities == null) return new HashSet<String>();
		
		Set<String> result = new HashSet<String>();
		for (GrantedAuthority grantedAuthority : authorities) {
			result.add(grantedAuthority.getAuthority());
		}
		return result;
	}
	
	public static Collection<Client> toClientList(Collection<ClientByUserId> clientsByUserId) {
		Collection<Client> clients = new LinkedList<Client>();
		for (ClientByUserId clientByUserId : clientsByUserId) {
			clients.add(ClientMapper.toClient(clientByUserId));
		}
		return clients;
	}

	public static Collection<ClientByUserId> toClientByUserIdList(Collection<Client> clients) {
		Collection<ClientByUserId> clientsByClientId = new LinkedList<ClientByUserId>();
		for (Client client : clients) {
			clientsByClientId.add(ClientMapper.toClientByUserId(client));
		}
		return clientsByClientId;
	}
}
