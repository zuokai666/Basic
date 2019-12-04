package com.zk.basic.lamborghini.pool;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.Semaphore;
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
	
	private static final long housekeeper_period_ms = 10 * 1000;
	private static final int permits = 10000;
	
	private ConcurrentBag<ConnectionBean> connectionBag;
	protected LamborghiniConfig config;
	protected String poolName = "LamborghiniPool";
	protected DataSource dataSource;
	private ScheduledExecutorService houseKeepingExecutorService;
	private Semaphore semaphore = new Semaphore(permits);
	
	public LamborghiniPool(LamborghiniConfig config) {
		this.connectionBag = new ConcurrentBag<>();
		this.config = config;
		this.dataSource = initializeDataSource();
		this.houseKeepingExecutorService = initializeScheduledExecutorService();
		initializeScheduledExecutorServiceTask();
	}
	
	private DataSource initializeDataSource() {
		return new DriverDataSource(config.getUrl(), config.getDriverClassName(), 
				config.getUser(), config.getPassword());
	}
	
	private ScheduledExecutorService initializeScheduledExecutorService() {
		return new ScheduledThreadPoolExecutor(
				1, new SimpleThreadFactory(poolName + " houseKeeper", true), new DiscardPolicy());
	}
	
	private void initializeScheduledExecutorServiceTask() {
		this.houseKeepingExecutorService.scheduleWithFixedDelay(
				new HouseKeeper(), 100L, housekeeper_period_ms, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * 对外公开最内部的DataSource，以便获取基本属性与方法
	 * @return
	 */
	public DataSource getDataSource() {
		return dataSource;
	}
	
	/**
	 * 回收对象
	 * @param bean
	 */
	public void recycle(ConnectionBean bean){
		connectionBag.requite(bean);
	}
	
	public Connection getConnection() throws SQLException {
		try {
			semaphore.acquire();
			return connectionBag.borrow(config.getConnectionTimeout(), TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new SQLException(e);
		} finally {
			semaphore.release();
		}
	}
	
	/**
	 * 新建连接对象
	 * @return
	 * @throws SQLException
	 */
	protected ConnectionBean newConnectionBean() throws SQLException{
		return new ConnectionBean(getDataSource().getConnection(), this);
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
	
	private int getTotalConnections(){
		return connectionBag.size();
	}
	private int getIdleConnections(){
		return connectionBag.getCountBy(BagBean.STATE_NOT_IN_USE);
	}
	
	private boolean shouldCreateAnotherConnection(){
		return getTotalConnections() < config.getMaxPoolSize() && 
				(connectionBag.getWaitingThreadCount() > 0 || getIdleConnections() < config.getMinIdle());
	}
	
	private boolean shouldRemoveAnotherConnection(){
		return getTotalConnections() < config.getMaxPoolSize() && 
				getIdleConnections() > config.getMinIdle();
	}
	
	private class HouseKeeper implements Runnable{
		
		private final Logger log = LoggerFactory.getLogger(HouseKeeper.class);
		
		@Override
		public void run() {
			try {
				while(shouldCreateAnotherConnection()){
					connectionBag.add(newConnectionBean());
				}
				while(shouldRemoveAnotherConnection()){
					connectionBag.randomRemove();
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