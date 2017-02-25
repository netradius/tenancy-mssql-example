package com.mastercontrol.tenancy;

import com.mastercontrol.spring.SpringHelper;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * A simple connection provider which uses an existing data source and supports
 * SCHEMA based multi-tenancy with mysql.
 *
 * @author Kevin Hawkins
 * @author Erik R. Jensen
 */
public class TenantConnectionProvider implements MultiTenantConnectionProvider {

	private DataSource dataSource;

	private Connection getConnectionInternal() throws SQLException {
		if (dataSource ==  null) {
			dataSource = SpringHelper.getBean(DataSource.class);
		}
		assert  dataSource != null;
		return dataSource.getConnection();
	}

	@Override
	public Connection getAnyConnection() throws SQLException {
		return getConnection("mtdemo");
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		try {
			connection.createStatement().execute("USE mtdemo");
		} catch (Exception x) {
			throw new HibernateException("Failed to reset db to 'mtdemo':" + x.getMessage(), x);
		}
		connection.close();
	}

	@Override
	public Connection getConnection(String s) throws SQLException {
		Connection connection = getConnectionInternal();
		try {
			connection.createStatement().execute("USE " + s + "");
		} catch (Exception x) {
			throw  new HibernateException("Failed to set db to '" + s + "': " + x.getMessage(), x);
		}
		return connection;
	}

	@Override
	public void releaseConnection(String s, Connection connection) throws SQLException {
		releaseAnyConnection(connection);
	}

	@Override
	public boolean supportsAggressiveRelease() {
		return false;
	}

	@Override
	public boolean isUnwrappableAs(Class aClass) {
		return ConnectionProvider.class.equals(aClass)
				|| TenantConnectionProvider.class.isAssignableFrom(aClass);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T unwrap(Class<T> aClass) {
		if (isUnwrappableAs(aClass)) {
			return (T) this;
		} else {
			throw  new UnknownUnwrapTypeException(aClass);
		}
	}
}
