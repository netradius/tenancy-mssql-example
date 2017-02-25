package com.netradius.demo.migration;


import com.netradius.demo.tenancy.TenantService;
import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Erik R. Jensen
 */
@Service
@Slf4j
public class MigrationServiceImpl implements MigrationService {

	@Autowired
	private Flyway flyway;

	@Autowired
	private TenantService tenantService;

	@Autowired
	private FlywayMigrationStrategy flywayMigrationStrategy;

	@Override
	@PostConstruct
	public void migrate() {
		List<String> tenants = tenantService.list();
		tenants.forEach(this::migrate);
	}

	@Override
	public void migrate(@Nonnull String tenant) {
		if (tenantService.exists(tenant)) {
			log.debug("Applying migrations to tenant " + tenant);
			synchronized (this) { // We can't have threads stepping on one another
				try {
					//tODO adjust this for multiple db
//					flyway.setSchemas(tenant);
//					flywayMigrationStrategy.migrate(flyway);
				} finally {
					flyway.setSchemas("public");
				}
			}
		}
	}
}
