package com.netradius.demo.tenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.util.StringUtils;

/**
 * @author Kevin Hawkins
 * @author Erik R. Jensen
 */
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

	@Override
	public String resolveCurrentTenantIdentifier() {
		return TenantHolder.get();
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}
}
