package com.netradius.demo.tenancy;

import com.netradius.demo.spring.SpringHelper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.jdbc.connections.spi.MultiTenantConnectionProvider;
import org.hibernate.service.UnknownUnwrapTypeException;

import javax.sql.DataSource;
import java.sql.*;

/**
 * A simple connection provider which uses an existing data source and supports
 * SCHEMA based multi-tenancy with mysql.
 *
 * @author Kevin Hawkins
 * @author Erik R. Jensen
 */
@Slf4j
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
		return getConnection(TenantHolder.get());
	}

	@Override
	public void releaseAnyConnection(Connection connection) throws SQLException {
		try {
			connection.createStatement().execute("USE " + TenantHolder.get());
		} catch (Exception x) {
			throw new HibernateException("Failed to reset db to " + TenantHolder.get() + ": " + x.getMessage(), x);
		}
		connection.close();
	}

	@Override
	public Connection getConnection(String s) throws SQLException {
		Connection connection = getConnectionInternal();
		try {
			connection.createStatement().execute("USE " + s);
			PreparedStatement ps = connection.prepareStatement("SELECT DB_NAME() AS [Current Database];");
			ResultSet rs = ps.executeQuery();
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnsNumber = rsmd.getColumnCount();
			while (rs.next()) {
				for (int i = 1; i <= columnsNumber; i++) {
					String columnValue = rs.getString(i);
					log.debug("Current database " + columnValue);
				}
			}
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
