package com.netradius.demo.data.service.impl;

import com.netradius.demo.data.model.User;
import com.netradius.demo.data.repository.UserRepository;
import com.netradius.demo.data.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;

/**
 * Implementation of UserService.
 *
 * @author Kevin Hawkins
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void create(@Nonnull String name) {
		//TODO implement me
	}

	@Override
	public String getUsername(@Nonnull String name) {
		User user = userRepository.findByName(name);
		if (user != null) {
			return user.getName();
		}
		return "";
	}
}
