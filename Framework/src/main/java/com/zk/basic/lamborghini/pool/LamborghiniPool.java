package com.zk.basic.lamborghini.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zk.basic.lamborghini.LamborghiniConfig;
import com.zk.basic.lamborghini.thread.DiscardPolicy;
import com.zk.basic.lamborghini.thread.SimpleThreadFactory;
import com.zk.basic.lamborghini.util.BagBean;
import com.zk.basic.lamborghini.util.ConcurrentBag;
import com.zk.basic.lamborghini.util.DriverDataSource;

/**
 * 连接池，提供了对并发包的操作，以及定时填充整理功能
 * 
 * @author King
 * 
 */
public class LamborghiniPool implements AutoCloseable{
	
	public static final long housekeeper_period_ms = 10 * 1000;
	
	private ConcurrentBag<ConnectionBean> connectionBag;
	protected LamborghiniConfig config;
	protected String poolName;
	protected DataSource dataSource;
	private ScheduledExecutorService houseKeepingExecutorService;
	
	public LamborghiniPool(LamborghiniConfig config) {
		this.connectionBag = new ConcurrentBag<>();
		this.config = config;
		this.poolName = config.getPoolName();
		this.dataSource = initializeDataSource();
		this.houseKeepingExecutorService = initializeScheduledExecutorService();
		initializeScheduledExecutorServiceTask();
	}
	
	protected DataSource initializeDataSource() {
		return new DriverDataSource(config.getUrl(), config.getDriverClassName(), 
				config.getUser(), config.getPassword());
	}
	
	protected ScheduledExecutorService initializeScheduledExecutorService() {
		return new ScheduledThreadPoolExecutor(
				1, new SimpleThreadFactory(poolName + " houseKeeper", true), new DiscardPolicy());
	}
	
	protected void initializeScheduledExecutorServiceTask() {
		this.houseKeepingExecutorService.scheduleWithFixedDelay(
				new HouseKeeper(), 100L, housekeeper_period_ms, TimeUnit.MILLISECONDS);
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	public void recycle(ConnectionBean bean){
		connectionBag.requite(bean);
	}
	
	public Connection getConnection() throws SQLException {
		try {
			return connectionBag.borrow(config.getConnectionTimeout(), TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new SQLException(e);
		}
	}
	
	protected Connection newConnection() throws SQLException{
		return dataSource.getConnection();
	}
	
	protected ConnectionBean newConnectionBean() throws SQLException{
		return new ConnectionBean(newConnection(), this);
	}
	
	@Override
	public void close() throws Exception {
		if(connectionBag != null){
			connectionBag.close();
			connectionBag = null;
		}
		dataSource = null;
		poolName = null;
	}
	
	public int getTotalConnections(){
		return connectionBag.size();
	}
	public int getIdleConnections(){
		return connectionBag.getCountBy(BagBean.STATE_NOT_IN_USE);
	}
	
	public boolean shouldCreateAnotherConnection(){
		return getTotalConnections() < config.getMaxPoolSize() && 
				(connectionBag.getWaitingThreadCount() > 0 || getIdleConnections() < config.getMinIdle());
	}
	
	public class HouseKeeper implements Runnable{
		
		private final Logger log = LoggerFactory.getLogger(HouseKeeper.class);
		
		@Override
		public void run() {
			try {
				if(shouldCreateAnotherConnection()){
					connectionBag.add(newConnectionBean());
				}
				int total = getTotalConnections();
				int idle = getIdleConnections();
				log.debug("HouseKeeper state : total[{}],idle[{}],use[{}]", total, idle, (total - idle));
			} catch (Exception e) {
				log.error("HouseKeeper task catch exception.", e);
			}
		}
	}
}