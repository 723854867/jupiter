package org.jupiter.redis;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.jupiter.redis.RedisConsts.EXPX;
import org.jupiter.redis.RedisConsts.NXXX;
import org.jupiter.redis.ops.LuaOps;
import org.jupiter.redis.ops.StringOps;
import org.jupiter.util.KeyUtil;
import org.springframework.stereotype.Component;

/**
 * redis 分布式锁
 */
@Component
public class DistributeLock {

	@Resource
	private LuaOps luaOps;
	@Resource
	private StringOps stringOps;
	
	/**
	 * 分布式锁：尝试获取指定资源的锁，获取成功返回唯一锁id，失败则返回null。只尝试获取一次
	 * 
	 * @param lock
	 * @return
	 */
	public String tryLock(String lock, int expire) {
		String lockId = KeyUtil.uuid();
		return stringOps.set(lock, lockId, NXXX.NX, EXPX.PX, expire) ? lockId : null;
	}
	
	/**
	 * 分布式锁：获取指定资源的锁，直到超时，成功则返回锁id，失败或者超时返回null。指定超时时间
	 * 
	 * @param lock
	 * @return
	 */
	public String lock(String lock, int wait, int expire) {
		long begin = System.nanoTime();
		while (true) {
			String lockId = tryLock(lock, expire);
			if (null != lockId)
				return lockId;
			
			long time = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - begin);
			if (time >= wait)
				return null;
			Thread.yield();
		}
	}
	
	/**
	 * 释放锁资源：建议获取锁成功之后将释放锁资源写在 final 块中
	 * 
	 * @param lock
	 * @param lockId
	 * @return
	 */
	public boolean releaseLock(String lock, String lockId) {
		return luaOps.delIfEquals(lock, lockId);
	}
}
