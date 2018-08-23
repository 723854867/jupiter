package org.jupiter.redis;

import java.util.Set;

import org.jupiter.bean.enums.ColumnStyle;
import org.jupiter.util.lang.CollectionUtil;
import org.jupiter.util.spring.ConfigLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Pool;

@Configuration
public class RedisInitializer {
	
	@Bean
	public RedisConfig redisConfig() {
		return ConfigLoader.load("classpath*:conf/redis.properties").toBean(RedisConfig.class, ColumnStyle.camel2dot);
	}
	
	@Bean(name = "jedisPool")
	public Pool<Jedis> jedisPool(RedisConfig redisConfig) {
		JedisPoolConfig config = new JedisPoolConfig();
		// 连接耗尽时是否阻塞：false-直接抛异常，true-阻塞直到超时，默认 true
		config.setBlockWhenExhausted(redisConfig.isBlockWhenExhausted());
		// 设置注册策略类名：默认 DefaultEvictionPolicy(当连接超过最大空闲时间，或连接数超过最大空闲连接数时逐出)
		config.setEvictionPolicyClassName(redisConfig.getEvictionPolicyClass());
//		config.setFairness(fairness);
		// 是否启用pool的jmx管理功能, 默认true
		config.setJmxEnabled(redisConfig.isJmxEnabled());
//		config.setJmxNameBase(jmxNameBase);
//		config.setJmxNamePrefix("");
		// 是否启用后进先出  - last in first out, 默认true
		config.setLifo(redisConfig.isLifo());
		// 最大空闲连接数, 默认8个
		config.setMaxIdle(redisConfig.getMaxIdle());
		// 最小空闲连接数, 默认0
		config.setMinIdle(redisConfig.getMinIdle());
		// 最大连接数, 默认8个
		config.setMaxTotal(redisConfig.getMaxTotal());
		// 获取连接时的最大等待毫秒数(如果设置为阻塞时BlockWhenExhausted),如果超时就抛异常, 小于零:阻塞不确定的时间, 默认-1
		config.setMaxWaitMillis(redisConfig.getMaxWaitMillis());
		// 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)
		config.setMinEvictableIdleTimeMillis(redisConfig.getMinEvictableIdleTimeMillis());
		// 每次逐出检查时逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
		config.setNumTestsPerEvictionRun(redisConfig.getNumTestsPerEvictionRun());
		// 对象空闲多久后逐出, 当空闲时间>该值且空闲连接>最大空闲数时直接逐出,不再根据MinEvictableIdleTimeMillis判断  (默认逐出策略)   
		config.setSoftMinEvictableIdleTimeMillis(redisConfig.getSoftMinEvictableIdleTimeMillis());
		// 在获取连接的时候检查有效性, 默认false
		config.setTestOnBorrow(redisConfig.isTestOnBorrow());
		config.setTestOnCreate(redisConfig.isTestOnCreate());
		config.setTestOnReturn(redisConfig.isTestOnReturn());
		// 在空闲时检查有效性, 默认false
		config.setTestWhileIdle(redisConfig.isTestWhileIdle());
		// 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
		config.setTimeBetweenEvictionRunsMillis(redisConfig.getTimeBetweenEvictionRunsMillis());
		
		String poolName = redisConfig.getPoolClass();
		if (poolName.equals(ShardedJedisPool.class.getName())) 
			throw new UnsupportedOperationException("Unsupported ShardedJedisPool");
		else if (poolName.equals(JedisSentinelPool.class.getName())) {
			String sentinels = redisConfig.getHost();
			Set<String> set = CollectionUtil.splitToStringSet(sentinels, ";");
			return new JedisSentinelPool(redisConfig.getMaster(), set, config, redisConfig.getConnTimeout(), redisConfig.getPassword());
		} else 
			return new JedisPool(config, redisConfig.getHost(), redisConfig.getPort(), redisConfig.getConnTimeout(), redisConfig.getPassword(), redisConfig.getDatabase());
	}
}
