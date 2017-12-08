package com.roncoo.education.util.redis;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import redis.clients.jedis.Tuple;

public class RedisService {
	protected final static Logger logger = Logger.getLogger(RedisService.class);

	private RedisService() {
	}

	private static final class Lazyloader {
		private static final ExecutorService EXECUTORS = Executors.newSingleThreadExecutor();
		private static final RedisService instance = new RedisService();
	}

	public static RedisService getInstance() {
		return Lazyloader.instance;
	}



	public void setSortSetItem(final String key, final int value, final Object member) {
		Lazyloader.EXECUTORS.execute(new Runnable() {
			public void run() {
				RedisUtil.incrSortSetItem(key, value, member.toString());
			}
		});
	}

	public void updateSortSetItem(final String key, final int value, final Object member) {
		Lazyloader.EXECUTORS.execute(new Runnable() {
			public void run() {
				RedisUtil.updateSortSetItem(key, value, member.toString());
			}
		});
	}

	public void incrSortSetItem(final String key, final int increment, final Object member) {
		if (increment == 0) {
			return;
		}
		Lazyloader.EXECUTORS.execute(new Runnable() {
			public void run() {
				RedisUtil.incrSortSetItem(key, increment, member.toString());
			}
		});
	}

	public Set<Tuple> rangeWithScores(String key, int start, int end) {
		return RedisUtil.rangeWithScores(key, start, end);
	}

	public Long rank(String key, Object member) {
		return RedisUtil.rank(key, member.toString());
	}

	public Double queryScore(String key, Object member) {
		return RedisUtil.queryScore(key, member.toString());
	}


	public void hmSet(Object key, Map<String, String> map) {
		RedisUtil.hmSet(key.toString(), map);
	}

	public void hmSet(Object key, Map<String, String> map, int seconds) {
		hmSet(key.toString(), map);
		expire(key.toString(), seconds);
	}

	public void hSet(Object key, String field, Object value) {
		RedisUtil.hSet(key.toString(), field, value.toString());
	}

	public String hget(String key, String field) {
		return RedisUtil.hget(key, field);
	}

	public Long hincrby(String key, String field, long value) {
		return RedisUtil.hincrby(key, field, value);
	}

	public Set<String> keys(String pattern) {
		return RedisUtil.keys(pattern);
	}

	public Map<String, String> hgetAll(String key) {
		return RedisUtil.hgetAll(key);
	}



	public Long expire(String key, int seconds) {
		return RedisUtil.expire(key, seconds);
	}

	public Long deleteKey(String key) {
		return RedisUtil.delete(key);
	}

	public void roleResetNew(int player_id) {
		// hSet(RedisKeys.PLAYER + player_id, RedisKeys.ROLE_MEILIZHI, 0);
		// List<NPC> npcs = DB_SERVICE.getAllNPCs();
		// for (NPC npc : npcs) {
		// hSet(RedisKeys.PLAYER + player_id, RedisKeys.ROLE_HAOGANDU +
		// npc.getId(), 0);
		// }
	}


	// public void roleReset(int player_id) {
	// updateSortSetItem(RedisConstants.RANK_MEILIZHI, 0, player_id);
	// List<NPC> npcs = DB_SERVICE.getAllNPCs();
	// for (NPC npc : npcs) {
	// updateSortSetItem(RedisConstants.RANK_HAOGANDU + npc.getId(), 0,
	// player_id);
	// }
	// }

	public void execute(Runnable task) {
		Lazyloader.EXECUTORS.execute(task);
	}

	/**
	 * @author gyn
	 * @param player_token
	 * @param seconds
	 *            设置过期时间 单位秒
	 * @param game_key
	 *            游戏名字
	 * @return boolean
	 */
	public boolean setKsToken(int player_id, long player_token, int seconds, String game_key) {
		boolean b = false;
		String key = "";
		try {
//			KSToken t = KSToken.decode(player_token);
			if (player_id == 488405 || player_id == 626075) {
				logger.info("  set ##################  " + player_token);
			}
			key = game_key + String.valueOf(player_id);
			if (RedisUtil.exists(key)) {
				RedisUtil.delete(key);
			}
			b = RedisUtil.setex(key, player_token, seconds);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return b;
	}

	/**
	 * @ @author gyn
	 * @param player_token
	 * @param game_key
	 * @return
	 */
//	public boolean getKsToken(String player_token, String game_key) {
//		boolean b = false;
//		String value = null;
//		try {
//			KSToken t = KSToken.decode(player_token);
//			String key = game_key + String.valueOf(t.getPlayerId());
//			
//			if (RedisUtil.exists(key)) {
//
//				value = RedisUtil.getValue(key);
//				if (value != null) {
//					b = value.equals(player_token);
//				}
//
//			}
//			b = value.equals(player_token);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return b;
//	}
	public boolean getKsToken(int playerId, long player_token, String game_key) {
		boolean b = false;
		String value = null;
		try {
			String key = game_key + String.valueOf(playerId);

			if (RedisUtil.exists(key)) {


				value = RedisUtil.getValue(key);
				if (value != null) {
					b = value.equals(player_token);
				}

			}	
			b = value.equals(player_token + "");
			if (playerId == 488405 || playerId == 626075) {
				logger.info(value + "  get #######" + b + "###########  " + player_token);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return b;
	}

}
