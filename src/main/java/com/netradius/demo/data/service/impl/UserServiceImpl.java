package com.netradius.demo.data.service.impl;

import com.netradius.demo.data.service.UserService;
import lombok.extern.slf4j.Slf4j;
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

	@Override
	public void create(@Nonnull String name) {
		//TODO implement me
	}
}
