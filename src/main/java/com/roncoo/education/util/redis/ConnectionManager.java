package com.roncoo.education.util.redis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class ConnectionManager {

	private static final String redis_host = "127.0.0.1";
	private static final int redis_port = 6379;
	private static final int redis_timeout = 2000;
	private static final String redis_password = "crs-g7wc0jdo:ks888888";

	private static JedisPool pool;

	static {
		JedisPoolConfig config = new JedisPoolConfig();
		// 控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
		// pool = new JedisPool(config, redis_host, 6379);
		pool = new JedisPool(config, redis_host, redis_port, redis_timeout, redis_password);
	}

	public static void main(String[] args) throws Exception {
		pool = new JedisPool(new JedisPoolConfig(), redis_host, redis_port, redis_timeout, redis_password);
		Jedis jedis = pool.getResource();
		jedis.set("a", "abc");
		System.out.println("redis中的值：" + jedis.get("a"));

		closeConnection(jedis);
	}
	/**
	 * 获取连接
	 * @return
	 * @throws Exception
	 */
	public static synchronized Jedis getConnection() throws Exception {
		try {
			return pool.getResource();
		} catch (Exception e) {
			throw e;
		}
	}
	/**
	 * 关闭连接
	 * @param connection
	 * @throws Exception
	 */
	public static synchronized void closeConnection(Jedis connection) throws Exception {
		try {
			if (connection != null && connection.isConnected()) {
				connection.close();
			}
		} catch (Exception e) {
			throw e;
		}
	}

}
