package com.mastercontrol.tenancy;

import org.hibernate.context.spi.CurrentTenantIdentifierResolver;
import org.springframework.util.StringUtils;

/**
 * @author Kevin Hawkins
 * @author Erik R. Jensen
 */
public class TenantIdentifierResolver implements CurrentTenantIdentifierResolver {

	@Override
	public String resolveCurrentTenantIdentifier() {
		String tenant = TenantHolder.get();
		return StringUtils.hasText(tenant) ? tenant : "mtdemo";
	}

	@Override
	public boolean validateExistingCurrentSessions() {
		return true;
	}
}
