package org.jupiter.redis.ops;

import javax.annotation.Resource;

import org.jupiter.callback.Callback;

import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;
import redis.clients.util.Pool;

@Slf4j
public abstract class Ops {

	@Resource
	private Pool<Jedis> jedisPool;
	
	/**
	 * 执行 redis 命令
	 * 
	 * @param invoke
	 * @return
	 */
	protected <T> T invoke(Callback<Jedis, T> invoke) {
		Jedis jedis = null;
		try {
			jedis = jedisPool.getResource();
			return invoke.invoke(jedis);
		} catch (Exception e) {
			log.error("Jedis connection get failure!", e);
			throw e;
		} finally {
			if (null != jedis)
				jedis.close();
		}
	}
}
