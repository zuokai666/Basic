package com.zk.basic.lamborghini.util;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Iterator;
import java.util.Properties;
import java.util.ServiceLoader;

import javax.sql.DataSource;

public final class DriverDataSource implements DataSource {
	
	private final String jdbcUrl;
	private final Properties driverProperties;
	private Driver driver;
	
	public DriverDataSource(String jdbcUrl, String driverClassName, String username, String password) {
		this.jdbcUrl = jdbcUrl;
		this.driverProperties = new Properties();
		driverProperties.put("user", username);
		driverProperties.put("password", password);
		
		ServiceLoader<Driver> loadedDrivers = ServiceLoader.load(Driver.class);
        Iterator<Driver> driversIterator = loadedDrivers.iterator();
        while(driversIterator.hasNext()) {
        	Driver d = driversIterator.next();
        	if (d.getClass().getName().equals(driverClassName)) {
				driver = d;
				break;
			}
        }
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		return driver.connect(jdbcUrl, driverProperties);
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return getConnection();
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void setLogWriter(PrintWriter logWriter) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		DriverManager.setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return DriverManager.getLoginTimeout();
	}

	@Override
	public java.util.logging.Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return driver.getParentLogger();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new SQLFeatureNotSupportedException();
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return false;
	}
}