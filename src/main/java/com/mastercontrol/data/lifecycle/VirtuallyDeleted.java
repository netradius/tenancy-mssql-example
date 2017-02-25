package com.mastercontrol.data.lifecycle;

/**
 * @author Erik R. Jensen
 */
public interface VirtuallyDeleted<T> {

	boolean isDeleted();

	T setDeleted(boolean deleted);

}
