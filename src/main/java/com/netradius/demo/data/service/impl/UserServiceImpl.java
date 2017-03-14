package com.netradius.demo.data.service.impl;

import com.netradius.demo.data.model.User;
import com.netradius.demo.data.repository.UserRepository;
import com.netradius.demo.data.service.UserService;
import com.netradius.demo.migration.MigrationService;
import com.netradius.demo.tenancy.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

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

	@Autowired
	private TenantService tenantService;

	@Autowired
	private MigrationService migrationService;

	@Override
	public void create(@Nonnull String name) {
		final String tName = name.toLowerCase();
		if (!tenantService.create(tName)) {
			throw new IllegalArgumentException("Failed to create tenant [" + tName + "]");
		}
		migrationService.migrate(tName);
		// This is done so that we can run a separate transaction using the tenant just setup
//		CompletableFuture<ProviderIdentityView> future = CompletableFuture.supplyAsync(() -> updateAdmin(form));
//		try {
//			future.get();
//		} catch (InterruptedException | ExecutionException x) {
//			log.error("Error updating admin user on new tenant [" + cname + "]: " + x.getMessage());
//			throw new IllegalStateException(x);
//		}
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
