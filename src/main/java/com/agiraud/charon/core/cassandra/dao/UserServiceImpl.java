package com.agiraud.charon.core.cassandra.dao;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.agiraud.charon.core.cassandra.entity.UserByEmail;
import com.agiraud.charon.core.cassandra.entity.UserById;
import com.agiraud.charon.core.cassandra.entity.UserByUsername;
import com.agiraud.charon.core.cassandra.mapper.UserMapper;
import com.agiraud.charon.core.cassandra.repository.UserByEmailRepository;
import com.agiraud.charon.core.cassandra.repository.UserByIdRepository;
import com.agiraud.charon.core.cassandra.repository.UserByUsernameRepository;
import com.agiraud.charon.core.dao.UserService;
import com.agiraud.charon.core.dto.User;
import com.agiraud.charon.core.dto.UserDetail;
import com.agiraud.charon.core.exception.AlreadyRegisteredException;
import com.agiraud.charon.core.exception.EntityNotFoundException;
import com.datastax.driver.core.utils.UUIDs;

import java.util.Arrays;
import java.util.Collection;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class UserServiceImpl implements UserService {
	/* ************************************************************************* */
	// ATTRIBUTES
	/* ************************************************************************* */
	@Autowired
	private UserByUsernameRepository userByUsernameRepository;

	@Autowired
	private UserByEmailRepository userByEmailRepository;

	@Autowired
	private UserByIdRepository userByIdRepository;

	/* ************************************************************************* */
	// POST CONSTRUCT FUNCTIONS
	/* ************************************************************************* */
	@PostConstruct
	public void init() {
		Assert.notNull(userByUsernameRepository, "userByUsernameRepository can not be null");
		Assert.notNull(userByEmailRepository, "userByEmailRepository can not be null");
		Assert.notNull(userByIdRepository, "userByIdRepository can not be null");
	}

	/* ************************************************************************* */
	// PUBLIC FUNCTIONS
	/* ************************************************************************* */
	public void create(User user, boolean forced) {
		user.setEnabled(true);
		user.setUserId(UUIDs.timeBased());
		
		if(!forced) {
			user.setRoles(Arrays.asList("ROLE_USER"));
		}
		
		checkIfUsernameAlreadyExists(user.getUsername());
		checkIfEmailAlreadyExists(user.getEmailAddress());
		
		save(user);
	}
	
	public void delete(User user) {
		userByIdRepository.delete(UserMapper.toUserById(user));
		userByUsernameRepository.delete(UserMapper.toUserByUsername(user));
		userByEmailRepository.delete(UserMapper.toUserByEmail(user));
	}

	@Transactional(readOnly = true)
	public User getByUsername(String username) {
		return UserMapper.toUser(userByUsernameRepository.findByUsername(username).orElse(null));
	}

	@Transactional(readOnly = true)
	public User getByEmail(String email) {
		return UserMapper.toUser(userByEmailRepository.findByEmailAddress(email).orElse(null));
	}

	@Transactional(readOnly = true)
	public User getByUserId(UUID userId) {
		return UserMapper.toUser(userByIdRepository.findByUserId(userId).orElse(null));
	}

	@Transactional(readOnly = true)
	public UserDetail getUserDetailByUsername(String username) {
		return UserMapper.toUserDetail(getByUsername(username));
	}

	@Transactional(readOnly = true)
	public UserDetail getUserDetailByEmail(String email) {
		return UserMapper.toUserDetail(getByEmail(email));
	}
	
	@Transactional(readOnly = true)
	public Collection<User> getAll() {
		return UserMapper.toUserCollection(userByIdRepository.findAll());
	}

	@Transactional(readOnly = true)
	public User findByUsername(String username) throws EntityNotFoundException {
		User user = getByUsername(username);
		if (user == null) {
			throw new EntityNotFoundException("User not found for the username: '"+username+"'");
		}
		return user;
	}

	@Transactional(readOnly = true)
	public User findByEmailAddress(String email) throws EntityNotFoundException {
		User user = getByEmail(email);
		if (user == null) {
			throw new EntityNotFoundException("User not found for the email: '"+email+"'");
		}
		return user;
	}

	@Transactional(readOnly = true)
	public User findByUserId(UUID userId) throws EntityNotFoundException {
		User user = getByUserId(userId);
		if (user == null) {
			throw new EntityNotFoundException("User not found for the user id: '"+userId+"'");
		}
		return user;
	}

	public void deleteById(UUID userId) throws EntityNotFoundException {
		User user = getByUserId(userId);
		delete(user);
	}

	public void update(User userBefore, User userAfter) {
		delete(userBefore);
		save(userAfter);
	}

	public void updateToAdmin(UUID userId) throws EntityNotFoundException {
		User userBefore = getByUserId(userId);
		User userAfter = userBefore;
		
		userAfter.setRoles(Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
		
		update(userBefore, userAfter);
	}
	
	/* ************************************************************************* */
	// CHECK FUNCTIONS
	/* ************************************************************************* */
	private void checkIfUsernameAlreadyExists(String username) {
		User user = getByUsername(username);
		if (user != null) {
			throw new AlreadyRegisteredException("This username is already registered.");
		}
	}

	private void checkIfEmailAlreadyExists(String emailAddress) {
		User user = getByEmail(emailAddress);
		if (user != null) {
			throw new AlreadyRegisteredException("An account already exist for this email.");
		}
	}

	/* ************************************************************************* */
	// PRIVATE FUNCTIONS
	/* ************************************************************************* */
	private void save(final User user) {
		log.debug("Saving user: "+user.toString());
		saveUserByUsername(UserMapper.toUserByUsername(user));
		saveUserByEmail(UserMapper.toUserByEmail(user));
		saveUserById(UserMapper.toUserById(user));
	}

	private void saveUserByUsername(@NotNull final UserByUsername user) {
		userByUsernameRepository.save(user);
	}

	private void saveUserByEmail(@NotNull final UserByEmail user) {
		userByEmailRepository.save(user);
	}

	private void saveUserById(@NotNull final UserById user) {
		userByIdRepository.save(user);
	}
}
