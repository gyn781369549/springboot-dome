package com.roncoo.education.util.redis;
import java.util.Map;
import java.util.Set;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;
public class RedisUtil {

	/**
	 * 为有序集 key 的成员 member 的 score 值加上增量 increment 。 可以通过传递一个负数值 increment ，让
	 * score 减去相应的值，比如 ZINCRBY key -5 member ，就是让 member 的 score 值减去 5 。 当 key
	 * 不存在，或 member 不是 key 的成员时， ZINCRBY key increment member 等同于 ZADD key
	 * increment member 。
	 * 
	 * // * @throws Exception
	 */
	public static void incrSortSetItem(String key, double increment, String member) {
		Jedis jedis = null;
		try {
			jedis = ConnectionManager.getConnection();
			jedis.zincrby(key, increment, member);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void updateSortSetItem(String key, double increment, String member) {
		Jedis jedis = null;
		try {
			jedis = ConnectionManager.getConnection();
			jedis.zadd(key, increment, member);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 显示有序集下标区间
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static Set<Tuple> rangeWithScores(String key, int start, int end) {
		Jedis jedis = null;
		try {
			jedis = ConnectionManager.getConnection();
			return jedis.zrevrangeWithScores(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Long rank(String key, String member) {
		Jedis jedis = null;
		try {
			jedis = ConnectionManager.getConnection();
			return jedis.zrevrank(key, member);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1l;
	}

	public static Double queryScore(String key, String member) {
		Jedis jedis = null;
		try {
			jedis = ConnectionManager.getConnection();
			return jedis.zscore(key, member);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void hmSet(String key, Map<String, String> map) {
		Jedis jedis = null;
		try {
			jedis = ConnectionManager.getConnection();
			jedis.hmset(key, map);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static void hSet(String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = ConnectionManager.getConnection();
			jedis.hset(key, field, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static String hget(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = ConnectionManager.getConnection();
			return jedis.hget(key, field);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Map<String, String> hgetAll(String key) {
		Jedis jedis = null;
		try {
			jedis = ConnectionManager.getConnection();
			return jedis.hgetAll(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Long hincrby(String key, String field, long value) {
		Jedis jedis = null;
		try {
			jedis = ConnectionManager.getConnection();
			return jedis.hincrBy(key, field, value);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static Set<String> keys(String pattern) {
		Jedis jedis = null;
		try {
			jedis = ConnectionManager.getConnection();
			return jedis.keys(pattern);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	// /**
	// * Redis Zremrangebyscore 命令用于移除有序集中，指定分数（score）区间内的所有成员。
	// */
	// public static Long zremrangebyscore(String key, double start, double end)
	// {
	// Jedis jedis = null;
	// try {
	// jedis = ConnectionManager.getConnection();
	// return jedis.zremrangeByScore(key, start, end);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }finally {
	// try {
	// ConnectionManager.closeConnection(jedis);
	// } catch (Exception e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// return 0L;
	// }

	public static Long delete(String key) {
		Jedis jedis = null;
		try {
			jedis = ConnectionManager.getConnection();
			return jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0L;
	}

	/**
	 * 存放 k,v 设置过期时间 单位 秒
	 * 
	 * @param key
	 * @return
	 */
	public static boolean setex(String key, String value, int seconds) {
		Jedis jedis = null;
		try {
			jedis = ConnectionManager.getConnection();
			return jedis.setex(key, seconds, value).equals("OK");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	public static boolean setex(String key, long value, int seconds) {
		Jedis jedis = null;
		try {
			jedis = ConnectionManager.getConnection();
			return jedis.setex(key, seconds, String.valueOf(value)).equals("OK");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}

	public static String getValue(String key) {
		Jedis jedis = null;
		try {
			jedis = ConnectionManager.getConnection();
			
			return jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	/**
	 * 存在返回true
	 * @param key
	 * @return
	 */
	public static boolean exists(String key) {
		Jedis jedis = null;
		try {
			jedis = ConnectionManager.getConnection();
			
			return jedis.exists(key);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}
	

	/**
	 * 获取过期时间，永久或者不存在 都返回-1   -2
	 * 
	 * @param key
	 * @return
	 */
	public static int getTtl(String key) {
		Jedis jedis = null;
		try {

			jedis = ConnectionManager.getConnection();
			return Integer.parseInt(jedis.ttl(key).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;
	}
	/**
	 * 移除某个key的生存时间
	 * 
	 * @param key
	 * @return
	 */
	public static int persist(String key) {
		Jedis jedis = null;
		try {

			jedis = ConnectionManager.getConnection();
			return Integer.parseInt(jedis.persist(key).toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return -1;
	}
	
	public static Long expire(String key, int seconds) {
		Jedis jedis = null;
		try {
			jedis = ConnectionManager.getConnection();
			return jedis.expire(key, seconds);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				ConnectionManager.closeConnection(jedis);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return 0L;
	}

	public static void main(String[] args) throws Exception {
		System.out.println("add-"+setex("mouni1",
				"N5dEpKPw04hddy6bOzk2YCyeIbAzZNK8JjO3iiv%2FgAqnlOaLhotb2OeyYNSvFT3tZVPIZtbsBHg%3D", 1 * 60));
		System.out.println("查看剩余生存时间"+getTtl("mouni1"));
		System.out.println("查询value-"+getValue("mouni1"));
		System.out.println("移除生存时间"+persist("mouni1"));
		System.out.println("查看剩余生存时间"+getTtl("mouni1"));
		System.out.println("是否存在"+exists("mouni1"));
		System.out.println("删除key"+delete("mouni1"));
		System.out.println("是否存在"+exists("mouni1"));
		System.out.println("查询value"+getValue("mouni1"));
		

	}

}
