package com.mastercontrol.tenancy;

import com.netradius.commons.lang.ValidationHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Kevin Hawkins
 * @author Erik R. Jensen
 */
@Service
@Slf4j
public class TenantServiceImpl implements TenantService {

	private static final String EXISTS_QUERY = "SELECT 1 FROM information_schema.schemata WHERE CATALOG_NAME = lower(?)";
	private static final String LIST_QUERY = "SELECT CATALOG_NAME FROM information_schema.schemata WHERE CATALOG_NAME NOT IN ('information_schema') ORDER BY CATALOG_NAME ";
	private static final String COUNT_QUERY = "SELECT count(1) FROM information_schema.schemata WHERE CATALOG_NAME NOT IN ('information_schema')";

	@Autowired
	private DataSource dataSource;

	private Set<String> disallowedTenants = new HashSet<String>() {{
		add("information_schema");
		add("public");
	}};

	private void checkAllowed(String tenant) {
		if (StringUtils.hasText(tenant)) {
			tenant = tenant.toLowerCase();
			if (disallowedTenants.contains(tenant)) {
				throw new IllegalArgumentException("Tenant [" + tenant + "] is not allowed");
			}
		}
	}

	@Override
	public boolean exists(@Nullable String tenant) {
		checkAllowed(tenant);
		try (Connection c = dataSource.getConnection();
		     PreparedStatement ps = c.prepareStatement(EXISTS_QUERY)
		) {
			ps.setString(1, tenant);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next();
			}
		} catch (SQLException x) {
			log.error("Error querying for schemas: " + x.getMessage(), x);
		}
		return false;
	}

	@Override
	public boolean create(@Nonnull String tenant) {
		ValidationHelper.checkForEmpty(tenant);
		tenant = tenant.toLowerCase();
		checkAllowed(tenant);
		ValidationHelper.checkRegex(tenant, "[a-zA-Z][a-zA-Z0-9]*");
		if (exists(tenant)) {
			return false;
		}
		try (Connection c = dataSource.getConnection();
		     Statement stmt = c.createStatement()) {
			stmt.execute("CREATE database " + tenant);
			return true;
		} catch (SQLException x) {
			log.error("Error creating schema [" + tenant + "]: " + x.getMessage(), x);
		}
		return false;
	}

	@Override
	public boolean drop(@Nonnull String tenant) {
		ValidationHelper.checkForEmpty(tenant);
		tenant = tenant.toLowerCase();
		checkAllowed(tenant);
		ValidationHelper.checkRegex(tenant, "[a-z]{1,31}");
		if (!exists(tenant)) {
			return false;
		}
		try (Connection c = dataSource.getConnection();
		     Statement stmt = c.createStatement()) {
			stmt.executeUpdate("DROP database " + tenant + " CASCADE");
			return true;
		} catch (SQLException x) {
			log.error("Error drop schema: [" + tenant + "]: " + x.getMessage(), x);
		}
		return false;
	}

	@Override
	public boolean rename(@Nonnull String tenant, @Nonnull String newTenant) {
		ValidationHelper.checkForEmpty(tenant);
		tenant = tenant.toLowerCase();
		ValidationHelper.checkRegex(tenant, "[a-z]{1,31}");
		ValidationHelper.checkForEmpty(newTenant);
		newTenant = newTenant.toLowerCase();
		ValidationHelper.checkRegex(newTenant, "[a-z]{1,31}");
		checkAllowed(tenant);
		checkAllowed(newTenant);
		if (exists(newTenant)) {
			return false;
		}
		try (Connection c = dataSource.getConnection();
		     Statement stmt = c.createStatement()) {
			stmt.execute("RENAME " + tenant + " TO " + newTenant);
			return true;
		} catch (SQLException x) {
			log.error("Error renaming schema [" + tenant + "] to [" + newTenant + "]: " + x.getMessage(), x);
		}
		return false;
	}

	@Override
	public int count() {
		try (Connection c = dataSource.getConnection();
		     PreparedStatement ps = c.prepareStatement(COUNT_QUERY);
		     ResultSet rs = ps.executeQuery()) {
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (SQLException x) {
			log.error("Error counting tenants: " + x.getMessage(), x);
		}
		return 0;
	}

	@Nonnull
	@Override
	public List<String> list() {
		List<String> tenants = new ArrayList<>(count());
		try (Connection c = dataSource.getConnection();
		     PreparedStatement ps = c.prepareStatement(LIST_QUERY);
		     ResultSet rs = ps.executeQuery()) {
			while (rs.next()) {
				tenants.add(rs.getString(1));
			}
		} catch (SQLException x) {
			log.error("Error querying tenant: " + x.getMessage(), x);
		}
		return tenants;
	}
}
