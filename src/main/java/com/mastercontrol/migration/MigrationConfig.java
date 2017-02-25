package com.mastercontrol.migration;

import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Erik R. Jensen
 */
@Configuration
public class MigrationConfig {

	@Bean
	@Profile("!integration")
	public FlywayMigrationStrategy cleanMigrateStrategy() {
		return (flyway) -> {
			flyway.clean(); // TODO Remove when we go-live officially
			flyway.migrate();
		};
	}

}
