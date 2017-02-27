package com.netradius.demo.data.service;

import javax.annotation.Nonnull;

/**
 * Service for User related tasks.
 *
 * @author Kevin Hawkins
 */
public interface UserService {

	void create(@Nonnull String name);

	String getUsername(@Nonnull String name);
}
