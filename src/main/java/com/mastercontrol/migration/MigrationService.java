package com.mastercontrol.migration;

import javax.annotation.Nonnull;

/**
 * @author Erik R. Jensen
 */
public interface MigrationService {

	void migrate();

	void migrate(@Nonnull String tenant);

}
