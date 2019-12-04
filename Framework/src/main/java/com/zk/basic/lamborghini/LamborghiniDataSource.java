package com.zk.basic.lamborghini;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.zk.basic.lamborghini.pool.LamborghiniPool;

public class LamborghiniDataSource implements DataSource, AutoCloseable{
	
	private final AtomicBoolean shutdown = new AtomicBoolean(false);
	private final LamborghiniPool pool;
	
	public LamborghiniDataSource(LamborghiniConfig configuration) {
		this.pool = new LamborghiniPool(configuration);
	}
	
	@Override
	public void close() throws Exception {
		if(shutdown.compareAndSet(false, true)){//幂等操作
			pool.close();
		}
	}
	
	@Override
	public Connection getConnection() throws SQLException {
		if(isClosed()){
			throw new SQLException("LamborghiniDataSource has been closed.");
		}
		return pool.getConnection();
	}
	
	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return getConnection();
	}
	
	protected boolean isClosed() {
		return shutdown.get();
	}

	@Override
	public PrintWriter getLogWriter() throws SQLException {
		return pool.getDataSource().getLogWriter();
	}

	@Override
	public void setLogWriter(PrintWriter out) throws SQLException {
		pool.getDataSource().setLogWriter(out);
	}

	@Override
	public void setLoginTimeout(int seconds) throws SQLException {
		pool.getDataSource().setLoginTimeout(seconds);
	}

	@Override
	public int getLoginTimeout() throws SQLException {
		return pool.getDataSource().getLoginTimeout();
	}

	@Override
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		return pool.getDataSource().getParentLogger();
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		return pool.getDataSource().unwrap(iface);
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		return pool.getDataSource().isWrapperFor(iface);
	}	
}