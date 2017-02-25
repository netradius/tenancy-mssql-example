package com.mastercontrol.data.lifecycle;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.UUID;

/**
 * @author Erik R. Jensen
 */
public class UUIDListener {

	@PrePersist
	@PreUpdate
	public void onPersist(Object o) {
		if (o instanceof UUIDIdentified) {
			UUIDIdentified i = (UUIDIdentified)o;
			if (i.getId() == null) {
				i.setId(UUID.randomUUID());
			}
		}
	}

}
