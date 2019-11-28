package com.zk.basic.task.config;

public class DefaultConnectionConfig implements ConnectionConfig{

	private String url;
	private String user;
    private String password;
    private String dataBase;
	
    public DefaultConnectionConfig(String url, String user, String password, String dataBase) {
		this.url = url;
		this.user = user;
		this.password = password;
		this.dataBase = dataBase;
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

	@Override
	public String getDataBase() {
		return dataBase;
	}	
}