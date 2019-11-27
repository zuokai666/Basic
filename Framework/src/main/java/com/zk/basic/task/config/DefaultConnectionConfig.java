package com.zk.basic.task.config;

public class DefaultConnectionConfig implements ConnectionConfig{

	private String url;
	private String user;
    private String password;
	
    public DefaultConnectionConfig(String url, String user, String password) {
		this.url = url;
		this.user = user;
		this.password = password;
	}
    
	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public String getUser() {
		return user;
	}

	@Override
	public String getPassword() {
		return password;
	}	
}