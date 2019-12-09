package com.zk.basic.lamborghini;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;

import javax.sql.DataSource;

import com.zk.basic.lamborghini.pool.LamborghiniPool;

/**
 * 对外公开的兰博基尼数据库连接池，重写了获取的连接的关闭方法，使得不使用的时候放回连接池，做到复用。
 * 
 * @author king
 * @date 2019-12-04 20:13:37
 *
 */
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
	
	/**
	 * 因为复用连接，需要考虑有状态的对象问题，目前没有特殊处理。2019年12月9日11:45:28 左凯
	 * 为了避免频繁改动，需要加入观察者模式来满足开闭原则。
	 */
	@Override
	public Connection getConnection() throws SQLException {
		if(isClosed()){
			throw new SQLException("LamborghiniDataSource has been closed.");
		}
		return pool.getConnection();
	}
	
	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		throw new SQLFeatureNotSupportedException();
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