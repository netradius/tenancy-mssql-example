package com.mastercontrol.data.lifecycle;

import java.util.UUID;

/**
 * @author Erik R. Jensen
 */
public interface UUIDIdentified<T> {

	UUID getId();

	T setId(UUID id);
}
