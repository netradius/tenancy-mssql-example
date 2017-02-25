package com.mastercontrol.tenancy;

import com.netradius.commons.lang.ValidationHelper;

/**
 * @author Kevin Hawkins
 * @author Erik R. Jensen
 */
public class TenantHolder {

	private static InheritableThreadLocal<String> tenantHolder = new InheritableThreadLocal<>();

	public static void set(String tenant) {
		ValidationHelper.checkForEmpty(tenant);
		tenantHolder.set(tenant);
	}

	public static String get() {
		return tenantHolder.get();
	}

	public static void unset() {
		tenantHolder.set(null);
	}

}
