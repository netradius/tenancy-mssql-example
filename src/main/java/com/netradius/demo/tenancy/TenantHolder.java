package com.netradius.demo.tenancy;

import com.netradius.commons.lang.ValidationHelper;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;

/**
 * @author Kevin Hawkins
 * @author Erik R. Jensen
 */
public class TenantHolder {

	public static final String DEFAULT_TENANT = "mtdemo";

	private static InheritableThreadLocal<String> tenantHolder = new InheritableThreadLocal<>();

	public static void set(@Nonnull String tenant) {
		ValidationHelper.checkForEmpty(tenant);
		tenantHolder.set(tenant);
	}

	@Nonnull
	public static String get() {
		String tenant = tenantHolder.get();
		if (!StringUtils.hasText(tenant)) {
			tenant = DEFAULT_TENANT;
			tenantHolder.set(tenant);
		}
		return tenant;
	}

	public static void unset() {
		tenantHolder.set(DEFAULT_TENANT);
	}

}
