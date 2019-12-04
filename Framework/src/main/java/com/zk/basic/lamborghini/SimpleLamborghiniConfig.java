package com.zk.basic.lamborghini;

public class SimpleLamborghiniConfig implements LamborghiniConfig{
	
	private String url;
	private String driverClassName;
	private String user;
	private String password;
	private int minIdle;
	private int maxPoolSize;
	private int connectionTimeout;
	private String poolName;
	
	public void setUrl(String url) {
		this.url = url;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public void setMinIdle(int minIdle) {
		this.minIdle = minIdle;
	}
	public void setMaxPoolSize(int maxPoolSize) {
		this.maxPoolSize = maxPoolSize;
	}
	public void setConnectionTimeout(int connectionTimeout) {
		this.connectionTimeout = connectionTimeout;
	}
	public void setPoolName(String poolName) {
		this.poolName = poolName;
	}
	
	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String getDriverClassName() {
		return driverClassName;
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public int getMinIdle() {
		return minIdle;
	}

	@Override
	public int getMaxPoolSize() {
		return maxPoolSize;
	}

	@Override
	public int getConnectionTimeout() {
		return connectionTimeout;
	}
	@Override
	public String getPoolName() {
		return poolName;
	}
}