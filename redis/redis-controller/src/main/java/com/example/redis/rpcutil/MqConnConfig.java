//package com.example.redis.rpcutil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.stereotype.Component;
//
///**
// * User: zhaolp
// */
//@Component
//@ConfigurationProperties(prefix = "rmq")
//public class MqConnConfig {
//
//	private static final Logger logger = LoggerFactory.getLogger(MqConnConfig.class);
//
//	private final static int DEFAULT_PORT = 4561;
//
//	private final static String DEFAULT_USERNAME = "ccs";
//
//	private final static String DEFAULT_PASSWORD = "84217399";
//
//	private final static int DEFAULT_CONCURRENT_CONSUMERS = Runtime.getRuntime().availableProcessors() * 2;
//
//	private static final int PREFETCH_SIZE = 1;
//
//	private static final int DEFAULT_CHANNEL_CACHE_SIZE = 25;
//
//	private static final int DEFAULT_CONNECTION_CACHE_SIZE = 5;
//
//	private static final int DEFAULT_CONNECTION_LIMIT = 10;
//
//	private String username = DEFAULT_USERNAME;
//
//	private String password = DEFAULT_PASSWORD;
//
//	private String serverHost;
//
//	private String addresses;
//
//	private int port = DEFAULT_PORT;
//
//	private String virtualHost="/";
//
//	/**
//	 *rabbitmq建立连接的超时时间
//	 */
//	private int connectionTimeout = 0;
//
//	/**
//	   * 事件消息处理线程数，默认是 CPU核数 * 2
//	 */
//	private int concurrentConsumers = DEFAULT_CONCURRENT_CONSUMERS;
//
//	/**
//	 * 最大消费者数
//	 */
//	private int maxConcurrentConsumers = DEFAULT_CONCURRENT_CONSUMERS;
//
//	/**
//	 * 每次消费消息的预取值
//	 */
//	private int prefetchSize = PREFETCH_SIZE;
//
//	/**
//	 * 每个连接的信道缓存数
//	 */
//	private int channelCacheSize = DEFAULT_CHANNEL_CACHE_SIZE;
//
//	private int connectionCacheSize = DEFAULT_CONNECTION_CACHE_SIZE;
//
//	private int connectionLimit = DEFAULT_CONNECTION_LIMIT;
//
//	private boolean startListener = true;
//
//
//	public MqConnConfig(){
//	}
//
//
//	public void setServerHost(String serverHost) {
//		this.serverHost = serverHost;
//	}
//
//	public String getServerHost() {
//		return serverHost;
//	}
//
//	public String getAddresses() {
//		return addresses;
//	}
//
//	public void setAddresses(String addresses) {
//		this.addresses = addresses;
//	}
//
//	public void setPort(int port) {
//		this.port = port;
//	}
//
//	public int getPort() {
//		return port;
//	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}
//
//	public String getUsername() {
//		return username;
//	}
//
//	public void setPassword(String password) {
//		this.password = password;
//	}
//
//	public String getPassword() {
//		return password;
//	}
//
//	public void setVirtualHost(String virtualHost) {
//		this.virtualHost = virtualHost;
//	}
//
//	public String getVirtualHost() {
//		return virtualHost;
//	}
//
//	public void setConnectionTimeout(int connectionTimeout) {
//		this.connectionTimeout = connectionTimeout;
//	}
//
//	public int getConnectionTimeout() {
//		return connectionTimeout;
//	}
//
//	public void setConcurrentConsumers(int concurrentConsumers) {
//		this.concurrentConsumers = concurrentConsumers;
//	}
//
//	public int getConcurrentConsumers() {
//		return concurrentConsumers;
//	}
//
//	public int getMaxConcurrentConsumers() {
//		return maxConcurrentConsumers;
//	}
//
//	public void setMaxConcurrentConsumers(int maxConcurrentConsumers) {
//		this.maxConcurrentConsumers = maxConcurrentConsumers;
//	}
//
//	public void setPrefetchSize(int prefetchSize) {
//		this.prefetchSize = prefetchSize;
//	}
//
//	public int getPrefetchSize() {
//		return prefetchSize;
//	}
//
//	public int getChannelCacheSize() {
//		return channelCacheSize;
//	}
//
//	public void setChannelCacheSize(int channelCacheSize) {
//		this.channelCacheSize = channelCacheSize;
//	}
//
//	public int getConnectionCacheSize() {
//		return connectionCacheSize;
//	}
//
//	public void setConnectionCacheSize(int connectionCacheSize) {
//		this.connectionCacheSize = connectionCacheSize;
//	}
//
//	public int getConnectionLimit() {
//		return connectionLimit;
//	}
//
//	public void setConnectionLimit(int connectionLimit) {
//		this.connectionLimit = connectionLimit;
//	}
//
//
//	public boolean isStartListener() {
//		return startListener;
//	}
//
//	public void setStartListener(boolean startListener) {
//		this.startListener = startListener;
//	}
//
//}
