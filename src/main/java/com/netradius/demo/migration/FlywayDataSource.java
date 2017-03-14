package com.netradius.demo.migration;

import com.netradius.demo.tenancy.TenantHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.datasource.DelegatingDataSource;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.sql.*;

@Slf4j
public class FlywayDataSource extends DelegatingDataSource {

	private String defaultDB;

	public FlywayDataSource(DataSource targetDataSource, String defaultDB) {
		super(targetDataSource);
		this.defaultDB = defaultDB;
	}

	@Override
	public FlywayConnection getConnection() throws SQLException {
		String s = TenantHolder.get();
		Connection connection = super.getConnection();
		if (StringUtils.hasText(s)) {
			connection.createStatement().execute("USE " + s + "");
		}
		return new FlywayConnection(connection, defaultDB);
	}
}
