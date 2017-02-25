package com.netradius.demo.tenancy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * @author Kevin Hawkins
 * @author Erik R. Jensen
 */
public interface TenantService {

	boolean exists(@Nullable String tenant);

	boolean create(@Nonnull String tenant);

	boolean drop(@Nonnull String tenant);

	boolean rename(@Nonnull String tenant, @Nonnull String newTenant);

	int count();

	@Nonnull
	List<String> list();
}
