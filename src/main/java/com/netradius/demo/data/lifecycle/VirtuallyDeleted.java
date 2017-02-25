package com.netradius.demo.data.lifecycle;

/**
 * @author Erik R. Jensen
 */
public interface VirtuallyDeleted<T> {

	boolean isDeleted();

	T setDeleted(boolean deleted);

}
