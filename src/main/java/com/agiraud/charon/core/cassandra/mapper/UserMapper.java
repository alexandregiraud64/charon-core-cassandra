package com.agiraud.charon.core.cassandra.mapper;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.agiraud.charon.core.cassandra.entity.UserByEmail;
import com.agiraud.charon.core.cassandra.entity.UserById;
import com.agiraud.charon.core.cassandra.entity.UserByUsername;
import com.agiraud.charon.core.dto.User;
import com.agiraud.charon.core.dto.UserDetail;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public final class UserMapper {
	private UserMapper() {}

	public static UserByEmail toUserByEmail(User user) {
		log.debug("[UserMapper] User to UserByEmail");
		if(user == null) return null;
		
		UserByEmail userByEmail = new UserByEmail();
		userByEmail.setUsername(user.getUsername());
		userByEmail.setUserId(user.getUserId());
		userByEmail.setEmailAddress(user.getEmailAddress());
		userByEmail.setPassword(user.getPassword());
		userByEmail.setFullName(user.getFullName());
		userByEmail.setEnabled(user.isEnabled());
		userByEmail.setPhoneNumber(user.getPhoneNumber());
		userByEmail.setProfileImage(user.getProfileImage());
		userByEmail.setRoles(user.getRoles());
		return userByEmail;
	}
	
	public static UserById toUserById(User user) {
		log.debug("[UserMapper] User to UserById");
		if(user == null) return null;
		
		UserById userById = new UserById();
		userById.setUsername(user.getUsername());
		userById.setUserId(user.getUserId());
		userById.setEmailAddress(user.getEmailAddress());
		userById.setPassword(user.getPassword());
		userById.setFullName(user.getFullName());
		userById.setEnabled(user.isEnabled());
		userById.setPhoneNumber(user.getPhoneNumber());
		userById.setProfileImage(user.getProfileImage());
		userById.setRoles(user.getRoles());
		return userById;
	}
	
	public static UserByUsername toUserByUsername(User user) {
		log.debug("[UserMapper] User to UserByUsername");
		if(user == null) return null;
		
		UserByUsername userByUsername = new UserByUsername();
		userByUsername.setUsername(user.getUsername());
		userByUsername.setUserId(user.getUserId());
		userByUsername.setEmailAddress(user.getEmailAddress());
		userByUsername.setPassword(user.getPassword());
		userByUsername.setFullName(user.getFullName());
		userByUsername.setEnabled(user.isEnabled());
		userByUsername.setPhoneNumber(user.getPhoneNumber());
		userByUsername.setProfileImage(user.getProfileImage());
		userByUsername.setRoles(user.getRoles());
		return userByUsername;
	}

	public static User toUser(UserByEmail userByEmail) {
		log.debug("[UserMapper] UserByEmail to User");
		if(userByEmail == null) return null;
		
		User user = new User();
		user.setUsername(userByEmail.getUsername());
		user.setUserId(userByEmail.getUserId());
		user.setEmailAddress(userByEmail.getEmailAddress());
		user.setPassword(userByEmail.getPassword());
		user.setFullName(userByEmail.getFullName());
		user.setEnabled(userByEmail.isEnabled());
		user.setPhoneNumber(userByEmail.getPhoneNumber());
		user.setProfileImage(userByEmail.getProfileImage());
		user.setRoles(userByEmail.getRoles());
		return user;
	}

	public static User toUser(UserById UserById) {
		log.debug("[UserMapper] UserById to User");
		if(UserById == null) return null;
		
		User user = new User();
		user.setUsername(UserById.getUsername());
		user.setUserId(UserById.getUserId());
		user.setEmailAddress(UserById.getEmailAddress());
		user.setPassword(UserById.getPassword());
		user.setFullName(UserById.getFullName());
		user.setEnabled(UserById.isEnabled());
		user.setPhoneNumber(UserById.getPhoneNumber());
		user.setProfileImage(UserById.getProfileImage());
		user.setRoles(UserById.getRoles());
		return user;
	}

	public static User toUser(UserByUsername userByUsername) {
		log.debug("[UserMapper] UserByUsername to User");
		if(userByUsername == null) return null;
		
		User user = new User();
		user.setUsername(userByUsername.getUsername());
		user.setUserId(userByUsername.getUserId());
		user.setEmailAddress(userByUsername.getEmailAddress());
		user.setPassword(userByUsername.getPassword());
		user.setFullName(userByUsername.getFullName());
		user.setEnabled(userByUsername.isEnabled());
		user.setPhoneNumber(userByUsername.getPhoneNumber());
		user.setProfileImage(userByUsername.getProfileImage());
		user.setRoles(userByUsername.getRoles());
		return user;
	}

	public static UserDetail toUserDetail(User user) {
		log.debug("[UserMapper] User to UserDetail");
		if(user == null) return null;
		
		UserDetail detail = new UserDetail();
		detail.setUsername(user.getUsername());
		detail.setUserId(user.getUserId());
		detail.setPassword(user.getPassword());
		detail.setEmailAddress(user.getEmailAddress());
		detail.setFullName(user.getFullName());
		detail.setPhoneNumber(user.getPhoneNumber());
		detail.setProfileImage(user.getProfileImage());
		detail.setEnabled(user.isEnabled());
		detail.setRoles(user.getRoles());
		return detail;
	}

	public static Collection<User> toUserCollection(List<UserById> usersById) {
		Collection<User> users = new LinkedList<User>();
		for (UserById userById : usersById) {
			users.add(UserMapper.toUser(userById));
		}
		return users;
	}

}
